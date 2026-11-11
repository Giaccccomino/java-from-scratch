package com.aurora.cinema.store;

import com.aurora.cinema.domain.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStore implements Store {
    protected final Map<String, Screen> screens = new ConcurrentHashMap<>();
    protected final Map<String, Showing> showings = new ConcurrentHashMap<>();
    protected final Map<String, Booking> bookings = new ConcurrentHashMap<>();

    public InMemoryStore add(Screen s) { screens.put(s.id(), s); return this; }
    public InMemoryStore add(Showing s) { showings.put(s.id(), s); return this; }

    @Override public List<Screen> screens() {
        return screens.values().stream().sorted(Comparator.comparing(Screen::id)).toList();
    }
    @Override public List<Showing> showings() {
        return showings.values().stream().sorted(Comparator.comparing(Showing::start)).toList();
    }
    @Override public Optional<Screen> screen(String id) { return Optional.ofNullable(screens.get(id)); }
    @Override public Optional<Showing> showing(String id) { return Optional.ofNullable(showings.get(id)); }
    @Override public List<Booking> bookingsFor(String showingId) {
        return bookings.values().stream().filter(b -> b.showingId().equals(showingId)).toList();
    }
    @Override public Optional<Booking> booking(String code) { return Optional.ofNullable(bookings.get(code)); }
    @Override public void save(Booking b) { bookings.put(b.code(), b); }
}
