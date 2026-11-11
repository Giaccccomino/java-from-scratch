package com.aurora.cinema.service;

import com.aurora.cinema.domain.*;
import com.aurora.cinema.store.Store;
import java.time.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/** The cinema's rules. It knows nothing about HTTP, JSON or files. */
public class BookingService {
    static final int MAX_SEATS = 10;
    static final Duration CANCELLATION_NOTICE = Duration.ofHours(2);

    private final Store store;
    private final Prices prices;
    private final Clock clock;
    private final Map<String, Object> locks = new ConcurrentHashMap<>();   // one lock per showing
    private final AtomicInteger counter;

    public BookingService(Store store, Prices prices, Clock clock) {
        this.store = store;
        this.prices = prices;
        this.clock = clock;
        int last = store.showings().stream()
                .flatMap(s -> store.bookingsFor(s.id()).stream())
                .mapToInt(b -> Integer.parseInt(b.code().substring(4))).max().orElse(0);
        this.counter = new AtomicInteger(last);                     // restarts from the last saved code
    }

    public List<Showing> schedule() {
        LocalDateTime now = LocalDateTime.now(clock);
        return store.showings().stream().filter(s -> s.start().isAfter(now)).toList();
    }

    public SortedSet<Seat> takenSeats(String showingId) {
        findShowing(showingId);
        return store.bookingsFor(showingId).stream()
                .filter(Booking::active)
                .flatMap(b -> b.seats().stream())
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public int freeSeats(String showingId) {
        Showing s = findShowing(showingId);
        return findScreen(s.screenId()).capacity() - takenSeats(showingId).size();
    }

    public Booking book(BookingRequest r) {
        Showing s = findShowing(r.showingId());
        if (!s.start().isAfter(LocalDateTime.now(clock))) throw new RuleException("the showing has already started");

        List<Seat> requested = r.seats().stream().map(Seat::of).toList();
        if (requested.size() > MAX_SEATS) throw new RuleException("at most " + MAX_SEATS + " seats per booking");
        if (new HashSet<>(requested).size() != requested.size()) throw new RuleException("repeated seats in the request");
        Screen screen = findScreen(s.screenId());
        List<Seat> outside = requested.stream().filter(seat -> !screen.contains(seat)).toList();
        if (!outside.isEmpty()) throw new RuleException("seats that don't exist in " + screen.name() + ": " + outside);

        synchronized (locks.computeIfAbsent(s.id(), k -> new Object())) {         // atomic check-then-act
            Set<Seat> taken = takenSeats(s.id());
            List<Seat> alreadyTaken = requested.stream().filter(taken::contains).sorted().toList();
            if (!alreadyTaken.isEmpty()) throw new SeatTakenException(alreadyTaken);

            Booking b = new Booking(
                    String.format("AUR-%06d", counter.incrementAndGet()), s.id(), r.customer().strip(), r.email(),
                    requested.stream().sorted().toList(), prices.total(s, requested.size()),
                    Instant.now(clock), Booking.Status.CONFIRMED);
            store.save(b);
            return b;
        }
    }

    public Booking cancel(String code) {
        Booking b = store.booking(code).orElseThrow(() -> new NotFoundException("booking", code));
        if (!b.active()) throw new RuleException("booking already cancelled");
        Showing s = findShowing(b.showingId());
        if (LocalDateTime.now(clock).plus(CANCELLATION_NOTICE).isAfter(s.start()))
            throw new RuleException("cancellation is possible until 2 hours before the start");
        synchronized (locks.computeIfAbsent(s.id(), k -> new Object())) {
            Booking cancelled = b.cancelled();
            store.save(cancelled);
            return cancelled;
        }
    }

    private Showing findShowing(String id) {
        return store.showing(id).orElseThrow(() -> new NotFoundException("showing", id));
    }

    private Screen findScreen(String id) {
        return store.screen(id).orElseThrow(() -> new NotFoundException("screen", id));
    }
}
