package com.aurora.cinema.domain;

import java.time.Instant;
import java.util.List;

public record Booking(String code, String showingId, String customer, String email,
                      List<Seat> seats, long totalCents, Instant created, Status status) {

    public enum Status { CONFIRMED, CANCELLED }

    public Booking {
        seats = List.copyOf(seats);                 // defensive, immutable copy
    }

    public Booking cancelled() {
        return new Booking(code, showingId, customer, email, seats, totalCents, created, Status.CANCELLED);
    }

    public boolean active() { return status == Status.CONFIRMED; }
}
