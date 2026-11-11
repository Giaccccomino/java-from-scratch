package com.aurora.cinema.app;

import com.aurora.cinema.domain.Screen;
import com.aurora.cinema.domain.Showing;
import com.aurora.cinema.service.BookingService;
import com.aurora.cinema.service.Prices;
import com.aurora.cinema.store.JsonStore;
import com.aurora.cinema.web.HttpApi;
import java.nio.file.Path;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

/** The place where all the pieces are created and wired together. */
public class Aurora {
    static final ZoneId NEW_YORK = ZoneId.of("America/New_York");

    public static void main(String[] args) throws Exception {
        run(Clock.system(NEW_YORK));
    }

    static void run(Clock clock) throws Exception {
        Path file = Path.of(System.getenv().getOrDefault("AURORA_DATA", "bookings.json"));
        int port = Integer.parseInt(System.getenv().getOrDefault("AURORA_PORT", "8080"));

        JsonStore store = new JsonStore(file);
        loadSchedule(store);
        BookingService service = new BookingService(store, new Prices(), clock);
        HttpApi api = new HttpApi(service);
        int actual = api.start(port);
        System.out.println("Aurora Cinema listening on http://localhost:" + actual + "/api/showings");
        Runtime.getRuntime().addShutdownHook(new Thread(api::stop));
    }

    static void loadSchedule(JsonStore s) {
        s.add(new Screen("S1", "Screen 1", 10, 12))
         .add(new Screen("S2", "Screen 2", 8, 10))
         .add(new Screen("S3", "Screen 3", 5, 9))
         .add(new Showing("SP1", "The Deep Blue", "S2", LocalDateTime.of(2026, 10, 30, 21, 15), 950))
         .add(new Showing("SP2", "A Night in the City", "S1", LocalDateTime.of(2026, 10, 30, 20, 30), 950))
         .add(new Showing("SP3", "A Night in the City", "S1", LocalDateTime.of(2026, 11, 4, 18, 0), 950))
         .add(new Showing("SP4", "Éclair at Dawn", "S3", LocalDateTime.of(2026, 10, 31, 21, 0), 950));
    }
}
