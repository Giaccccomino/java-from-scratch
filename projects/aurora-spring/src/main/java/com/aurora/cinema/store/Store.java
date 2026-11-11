package com.aurora.cinema.store;

import com.aurora.cinema.domain.*;
import java.util.List;
import java.util.Optional;

/** Where the data lives: the service knows only this interface. */
public interface Store {
    List<Screen> screens();
    List<Showing> showings();
    Optional<Screen> screen(String id);
    Optional<Showing> showing(String id);
    List<Booking> bookingsFor(String showingId);
    Optional<Booking> booking(String code);
    void save(Booking b);          // inserts or replaces (same code)
}
