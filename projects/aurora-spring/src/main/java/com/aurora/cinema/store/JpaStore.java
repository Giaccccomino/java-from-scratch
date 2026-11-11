package com.aurora.cinema.store;

import com.aurora.cinema.domain.*;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** Adapter: connects the Spring Data repositories to the Store interface of Chapter 45. */
@Repository
@Transactional
public class JpaStore implements Store {
    private final ScreenRepository screens;
    private final ShowingRepository showings;
    private final BookingRepository bookings;

    public JpaStore(ScreenRepository screens, ShowingRepository showings, BookingRepository bookings) {
        this.screens = screens;
        this.showings = showings;
        this.bookings = bookings;
    }

    @Override public List<Screen> screens() {
        return screens.findAll().stream().map(ScreenEntity::toDomain).toList();
    }
    @Override public List<Showing> showings() {
        return showings.findAllByOrderByStart().stream().map(ShowingEntity::toDomain).toList();
    }
    @Override public Optional<Screen> screen(String id) {
        return screens.findById(id).map(ScreenEntity::toDomain);
    }
    @Override public Optional<Showing> showing(String id) {
        return showings.findById(id).map(ShowingEntity::toDomain);
    }
    @Override public List<Booking> bookingsFor(String showingId) {
        return bookings.findByShowingId(showingId).stream().map(BookingEntity::toDomain).toList();
    }
    @Override public Optional<Booking> booking(String code) {
        return bookings.findById(code).map(BookingEntity::toDomain);
    }
    @Override public void save(Booking b) {
        bookings.save(BookingEntity.from(b));
    }
}
