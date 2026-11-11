package com.aurora.cinema.service;

import static org.junit.jupiter.api.Assertions.*;

import com.aurora.cinema.domain.*;
import com.aurora.cinema.store.InMemoryStore;
import java.time.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class BookingServiceTest {
    static final ZoneId NEW_YORK = ZoneId.of("America/New_York");
    // "now" is Sunday, October 25, 2026, 8 p.m. in New York
    static final Clock NOW = Clock.fixed(Instant.parse("2026-10-26T00:00:00Z"), NEW_YORK);

    InMemoryStore store;
    BookingService service;

    @BeforeEach
    void setUp() {
        store = new InMemoryStore()
                .add(new Screen("S1", "Screen 1", 10, 12))
                .add(new Screen("S2", "Screen 2", 8, 10))
                .add(new Showing("SP1", "The Deep Blue", "S2", LocalDateTime.of(2026, 10, 30, 21, 15), 950))
                .add(new Showing("SP3", "A Night in the City", "S1", LocalDateTime.of(2026, 11, 4, 18, 0), 950));
        service = new BookingService(store, new Prices(), NOW);
    }

    BookingRequest request(String showing, String... seats) {
        return new BookingRequest(showing, "Julia Ferris", "julia@example.com", List.of(seats));
    }

    @Test
    void bookingNormalizesSeatsAndComputesTotal() {
        Booking b = service.book(request("SP1", "c6", "C5"));
        assertEquals("AUR-000001", b.code());
        assertEquals(List.of(Seat.of("C5"), Seat.of("C6")), b.seats());
        assertEquals(1900, b.totalCents());
        assertEquals(78, service.freeSeats("SP1"));
    }

    @Test
    void oneTakenSeatBlocksTheWholeBooking() {
        service.book(request("SP1", "C5"));
        var e = assertThrows(SeatTakenException.class, () -> service.book(request("SP1", "D1", "C5")));
        assertEquals("seats already taken: [C5]", e.getMessage());
        assertEquals(79, service.freeSeats("SP1"));          // D1 wasn't taken
    }

    @Test
    void seatsOutsideTheScreenRejected() {
        var e = assertThrows(RuleException.class, () -> service.book(request("SP1", "K1", "A11")));
        assertEquals("seats that don't exist in Screen 2: [K1, A11]", e.getMessage());
    }

    @Test
    void repeatedSeatsRejected() {
        assertThrows(RuleException.class, () -> service.book(request("SP1", "A1", "a1")));
    }

    @Test
    void atMostTenSeats() {
        assertThrows(RuleException.class, () -> service.book(
                request("SP1", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "B1")));
    }

    @Test
    void nonexistentShowing() {
        var e = assertThrows(NotFoundException.class, () -> service.book(request("SP9", "A1")));
        assertEquals("showing SP9 does not exist", e.getMessage());
    }

    @Test
    void onWednesdayAGroupPaysSixDollarsLessTenPercent() {
        Booking b = service.book(request("SP3", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10"));
        assertEquals(5400, b.totalCents());                // 10 x 600 = 6000, less 10%
    }

    @Test
    void cancellationFreesTheSeats() {
        Booking b = service.book(request("SP1", "C5", "C6"));
        Booking cancelled = service.cancel(b.code());
        assertEquals(Booking.Status.CANCELLED, cancelled.status());
        assertEquals(80, service.freeSeats("SP1"));
        assertThrows(RuleException.class, () -> service.cancel(b.code()));
    }

    @Test
    void lateCancellationRejected() {
        Booking b = service.book(request("SP1", "E1"));
        Clock late = Clock.fixed(Instant.parse("2026-10-31T00:30:00Z"), NEW_YORK);   // 8:30 p.m. in New York
        BookingService later = new BookingService(store, new Prices(), late);
        var e = assertThrows(RuleException.class, () -> later.cancel(b.code()));
        assertEquals("cancellation is possible until 2 hours before the start", e.getMessage());
    }

    @Test
    void counterRestartsFromTheStore() {
        service.book(request("SP1", "A1"));
        BookingService restarted = new BookingService(store, new Prices(), NOW);
        assertEquals("AUR-000002", restarted.book(request("SP1", "A2")).code());
    }

    @RepeatedTest(20)
    void twoHundredCustomersForTheSameSeatsOnlyOneWins() {
        AtomicInteger succeeded = new AtomicInteger(), rejected = new AtomicInteger();
        try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 200; i++) {
                pool.submit(() -> {
                    try { service.book(request("SP1", "H10", "H9")); succeeded.incrementAndGet(); }
                    catch (SeatTakenException e) { rejected.incrementAndGet(); }
                });
            }
        }
        assertEquals(1, succeeded.get());
        assertEquals(199, rejected.get());
    }
}
