package com.aurora.cinema;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.http.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/** Starts the complete application on a free port and queries it with HttpClient (Chapter 40). */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookingApiTest {

    @Value("${local.server.port}")
    int port;

    final HttpClient http = HttpClient.newHttpClient();

    HttpResponse<String> get(String path) throws Exception {
        return http.send(HttpRequest.newBuilder(URI.create("http://localhost:" + port + path)).build(),
                HttpResponse.BodyHandlers.ofString());
    }

    HttpResponse<String> post(String json) throws Exception {
        return http.send(HttpRequest.newBuilder(URI.create("http://localhost:" + port + "/api/bookings"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json)).build(),
                HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void scheduleAndSeats() throws Exception {
        var r = get("/api/showings");
        assertEquals(200, r.statusCode());
        assertTrue(r.body().contains("The Deep Blue"));
        assertEquals(200, get("/api/showings/SP4/seats").statusCode());
        assertEquals(404, get("/api/showings/SP9/seats").statusCode());
    }

    @Test
    void bookingCreatedThenSeatTaken() throws Exception {
        String json = """
                {"showingId":"SP1","customer":"Julia Ferris","email":"julia@example.com","seats":["C5","C6"]}""";
        var created = post(json);
        assertEquals(201, created.statusCode());
        assertTrue(created.headers().firstValue("location").orElseThrow().startsWith("/api/bookings/AUR-"));
        assertTrue(created.body().contains("\"totalCents\":1900"));
        assertFalse(created.body().contains("julia@example.com"), "the email must not leave the API");

        var conflict = post(json);
        assertEquals(409, conflict.statusCode());
        assertTrue(conflict.body().contains("seats already taken: [C5, C6]"));
    }

    @Test
    void invalidRequests() throws Exception {
        assertEquals(400, post("{\"showingId\":\"SP1\",\"customer\":\"Mark\",\"email\":\"mark\",\"seats\":[\"D1\"]}").statusCode());
        assertEquals(400, post("{\"showingId\":\"SP1\",").statusCode());
        assertEquals(422, post("{\"showingId\":\"SP2\",\"customer\":\"Mark\",\"email\":\"m@example.com\",\"seats\":[\"Z1\"]}").statusCode());
    }

    @Test
    void health() throws Exception {
        var r = get("/actuator/health");
        assertEquals(200, r.statusCode());
        assertTrue(r.body().contains("UP"));
    }
}
