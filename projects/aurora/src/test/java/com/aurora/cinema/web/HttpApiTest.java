package com.aurora.cinema.web;

import static org.junit.jupiter.api.Assertions.*;

import com.aurora.cinema.domain.*;
import com.aurora.cinema.service.*;
import com.aurora.cinema.store.InMemoryStore;
import java.net.URI;
import java.net.http.*;
import java.time.*;
import org.junit.jupiter.api.*;

class HttpApiTest {
    static final Clock NOW = Clock.fixed(Instant.parse("2026-10-26T00:00:00Z"), ZoneId.of("America/New_York"));

    HttpApi api;
    String base;
    final HttpClient http = HttpClient.newHttpClient();

    @BeforeEach
    void start() throws Exception {
        InMemoryStore store = new InMemoryStore()
                .add(new Screen("S2", "Screen 2", 8, 10))
                .add(new Showing("SP1", "The Deep Blue", "S2", LocalDateTime.of(2026, 10, 30, 21, 15), 950));
        api = new HttpApi(new BookingService(store, new Prices(), NOW));
        base = "http://localhost:" + api.start(0);          // port 0: any free port
    }

    @AfterEach
    void stop() { api.stop(); }

    HttpResponse<String> get(String path) throws Exception {
        return http.send(HttpRequest.newBuilder(URI.create(base + path)).build(), HttpResponse.BodyHandlers.ofString());
    }

    HttpResponse<String> post(String json) throws Exception {
        return http.send(HttpRequest.newBuilder(URI.create(base + "/api/bookings"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json)).build(), HttpResponse.BodyHandlers.ofString());
    }

    @Test
    void freeSeats() throws Exception {
        var r = get("/api/showings/SP1/seats");
        assertEquals(200, r.statusCode());
        assertEquals("{\"showing\":\"SP1\",\"free\":80,\"taken\":[]}", r.body());
    }

    @Test
    void bookingAndConflict() throws Exception {
        String json = """
                {"showingId":"SP1","customer":"Julia Ferris","email":"julia@example.com","seats":["C5","C6"]}""";
        var created = post(json);
        assertEquals(201, created.statusCode());
        assertEquals("/api/bookings/AUR-000001", created.headers().firstValue("location").orElseThrow());
        var conflict = post(json);
        assertEquals(409, conflict.statusCode());
        assertTrue(conflict.body().contains("seats already taken: [C5, C6]"));
    }

    @Test
    void requestErrors() throws Exception {
        assertEquals(400, post("{\"showingId\":\"SP1\",\"customer\":\"Mark\",\"email\":\"mark\",\"seats\":[\"D1\"]}").statusCode());
        assertEquals(400, post("{\"showingId\":\"SP1\",").statusCode());
        assertEquals(422, post("{\"showingId\":\"SP1\",\"customer\":\"Mark\",\"email\":\"m@example.com\",\"seats\":[\"Z1\"]}").statusCode());
        assertEquals(404, get("/api/showings/SP9/seats").statusCode());
        assertEquals(404, get("/api/nothing").statusCode());
    }
}
