package com.aurora.cinema.app;

import java.time.Clock;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Starts the same application with the clock moved back to Sunday, October 25, 2026, 8 p.m. in New York,
 * so the showings written in the code are always in the future. Time still flows normally from there.
 */
public class DemoAurora {
    public static void main(String[] args) throws Exception {
        Clock real = Clock.system(Aurora.NEW_YORK);
        ZonedDateTime demoStart = ZonedDateTime.of(2026, 10, 25, 20, 0, 0, 0, Aurora.NEW_YORK);
        Aurora.run(Clock.offset(real, Duration.between(real.instant(), demoStart.toInstant())));
    }
}
