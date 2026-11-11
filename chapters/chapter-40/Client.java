import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

public class Client {
    public static void main(String[] args) throws Exception {
        var server = AuroraServer.start(8080);
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(5))
                    .build();

            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/screens/3")).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.headers().firstValue("content-type").orElse("?"));
            System.out.println(response.body());
            System.out.println(response.version());

            var r404 = client.send(HttpRequest.newBuilder(URI.create("http://localhost:8080/screens/9")).build(), HttpResponse.BodyHandlers.ofString());
            System.out.println(r404.statusCode() + " " + r404.body());

            String json = """
                    {"showing":"The Deep Blue","seats":["C5","C6"]}""";
            HttpRequest post = HttpRequest.newBuilder(URI.create("http://localhost:8080/bookings"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer test-secret")
                    .timeout(Duration.ofSeconds(10))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> created = client.send(post, HttpResponse.BodyHandlers.ofString());
            System.out.println(created.statusCode() + " " + created.headers().firstValue("location").orElse("") + " " + created.body());

            var noToken = client.send(HttpRequest.newBuilder(URI.create("http://localhost:8080/bookings")).POST(HttpRequest.BodyPublishers.ofString(json)).build(), HttpResponse.BodyHandlers.ofString());
            System.out.println(noToken.statusCode() + " " + noToken.body());
            var get405 = client.send(HttpRequest.newBuilder(URI.create("http://localhost:8080/bookings")).build(), HttpResponse.BodyHandlers.ofString());
            System.out.println(get405.statusCode() + " Allow: " + get405.headers().firstValue("allow").orElse(""));

            // asynchronous
            List<CompletableFuture<String>> futures = new ArrayList<>();
            for (String s : List.of("1", "2", "3")) {
                futures.add(client.sendAsync(HttpRequest.newBuilder(URI.create("http://localhost:8080/screens/" + s)).build(), HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body));
            }
            futures.forEach(f -> System.out.println(f.join()));

            // timeout
            try {
                client.send(HttpRequest.newBuilder(URI.create("http://localhost:8080/slow")).timeout(Duration.ofMillis(500)).build(), HttpResponse.BodyHandlers.ofString());
            } catch (HttpTimeoutException e) { System.out.println("timeout: " + e.getMessage()); }

            // connection refused
            try {
                client.send(HttpRequest.newBuilder(URI.create("http://localhost:8099/screens/1")).build(), HttpResponse.BodyHandlers.ofString());
            } catch (ConnectException e) { System.out.println("ConnectException: " + e.getMessage()); }

            // host that doesn't exist
            try {
                client.send(HttpRequest.newBuilder(URI.create("http://boxoffice.invalid/screens/1")).build(), HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) { System.out.println(e.getClass().getName() + ": " + e.getMessage()); }

            // invalid URI
            try { URI.create("http://localhost:8080/search?movie=The Deep Blue"); }
            catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
            String q = URLEncoder.encode("The Deep Blue", java.nio.charset.StandardCharsets.UTF_8);
            System.out.println(q);
        } finally {
            server.stop(0);
            System.exit(0);
        }
    }
}
