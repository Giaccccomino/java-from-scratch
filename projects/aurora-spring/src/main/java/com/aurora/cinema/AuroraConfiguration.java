package com.aurora.cinema;

import com.aurora.cinema.domain.*;
import com.aurora.cinema.service.*;
import com.aurora.cinema.store.*;
import java.time.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuroraConfiguration {

    @Bean
    Clock clock() {
        return Clock.system(ZoneId.of("America/New_York"));
    }

    @Bean
    Prices prices() {
        return new Prices();
    }

    @Bean
    BookingService bookingService(Store store, Prices prices, Clock clock) {
        return new BookingService(store, prices, clock);      // the parameters are other beans
    }

    /** Loads the schedule on first startup: the showings are always in the next few days. */
    @Bean
    CommandLineRunner loadSchedule(ScreenRepository screens, ShowingRepository showings, Clock clock) {
        return args -> {
            if (screens.count() > 0) return;
            screens.save(ScreenEntity.from(new Screen("S1", "Screen 1", 10, 12)));
            screens.save(ScreenEntity.from(new Screen("S2", "Screen 2", 8, 10)));
            screens.save(ScreenEntity.from(new Screen("S3", "Screen 3", 5, 9)));
            LocalDate today = LocalDate.now(clock);
            showings.save(ShowingEntity.from(new Showing("SP1", "The Deep Blue", "S2", today.plusDays(5).atTime(21, 15), 950)));
            showings.save(ShowingEntity.from(new Showing("SP2", "A Night in the City", "S1", today.plusDays(5).atTime(20, 30), 950)));
            showings.save(ShowingEntity.from(new Showing("SP3", "A Night in the City", "S1", today.plusDays(6).atTime(18, 0), 950)));
            showings.save(ShowingEntity.from(new Showing("SP4", "Éclair at Dawn", "S3", today.plusDays(6).atTime(21, 0), 950)));
        };
    }
}
