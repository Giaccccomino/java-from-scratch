import com.sun.net.httpserver.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.*;

public class AuroraServer {
    static final Map<String, Integer> seats = new ConcurrentHashMap<>(Map.of("1", 120, "2", 80, "3", 37));

    public static HttpServer start(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", port), 0);
        server.createContext("/screens/", AuroraServer::screens);
        server.createContext("/bookings", AuroraServer::bookings);
        server.createContext("/slow", s -> {                                // answers after three seconds (Section 40.5)
            try { Thread.sleep(3000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            respond(s, 200, "{\"message\":\"at last\"}");
        });
        server.setExecutor(Executors.newVirtualThreadPerTaskExecutor());   // one request, one virtual thread
        server.start();
        return server;
    }

    static void screens(HttpExchange s) throws IOException {
        String id = s.getRequestURI().getPath().substring("/screens/".length());
        Integer free = seats.get(id);
        if (free == null) {
            respond(s, 404, "{\"error\":\"screen " + id + " does not exist\"}");
            return;
        }
        respond(s, 200, "{\"screen\":" + id + ",\"free\":" + free + "}");
    }

    static void bookings(HttpExchange s) throws IOException {
        if (!s.getRequestMethod().equals("POST")) {
            s.getResponseHeaders().set("Allow", "POST");
            respond(s, 405, "");
            return;
        }
        String body = new String(s.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        String token = s.getRequestHeaders().getFirst("Authorization");
        if (!"Bearer test-secret".equals(token)) {
            respond(s, 401, "{\"error\":\"unauthorized\"}");
            return;
        }
        s.getResponseHeaders().set("Location", "/bookings/4821");
        respond(s, 201, "{\"id\":4821,\"received\":" + body + "}");
    }

    static void respond(HttpExchange s, int code, String text) throws IOException {
        byte[] b = text.getBytes(StandardCharsets.UTF_8);
        s.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        s.sendResponseHeaders(code, b.length == 0 ? -1 : b.length);     // -1: no body
        if (b.length > 0) try (OutputStream out = s.getResponseBody()) { out.write(b); }
        s.close();
    }

    public static void main(String[] args) throws IOException {
        start(8080);
        System.out.println("Server at http://localhost:8080");
    }
}
