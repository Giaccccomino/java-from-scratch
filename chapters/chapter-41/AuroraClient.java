import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.List;

public class AuroraClient {
    record ScreenStatus(int screen, int free) {}
    record BookingRequest(String showing, List<String> seats) {}
    record ApiError(String error) {}

    private final HttpClient http = HttpClient.newHttpClient();
    private final ObjectMapper json = new ObjectMapper();
    private final String base;

    AuroraClient(String base) { this.base = base; }

    ScreenStatus status(int screen) throws IOException, InterruptedException {
        HttpResponse<String> r = http.send(HttpRequest.newBuilder(URI.create(base + "/screens/" + screen)).build(),
                HttpResponse.BodyHandlers.ofString());
        if (r.statusCode() != 200) {
            ApiError e = json.readValue(r.body(), ApiError.class);
            throw new IOException(r.statusCode() + ": " + e.error());
        }
        return json.readValue(r.body(), ScreenStatus.class);
    }

    String book(BookingRequest request, String token) throws IOException, InterruptedException {
        HttpRequest post = HttpRequest.newBuilder(URI.create(base + "/bookings"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(json.writeValueAsString(request)))
                .build();
        HttpResponse<String> r = http.send(post, HttpResponse.BodyHandlers.ofString());
        return r.statusCode() + " " + json.readTree(r.body()).path("id").asInt();
    }

    public static void main(String[] args) throws Exception {
        var server = AuroraServer.start(8081);
        try {
            AuroraClient c = new AuroraClient("http://localhost:8081");
            System.out.println(c.status(3));
            System.out.println(c.status(3).free() > 30 ? "there's room" : "almost full");
            System.out.println(c.book(new BookingRequest("The Deep Blue", List.of("C5", "C6")), "test-secret"));
            try { c.status(9); } catch (IOException e) { System.out.println("error: " + e.getMessage()); }
        } finally { server.stop(0); System.exit(0); }
    }
}
