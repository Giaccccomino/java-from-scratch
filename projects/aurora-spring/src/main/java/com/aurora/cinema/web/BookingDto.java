package com.aurora.cinema.web;

import com.aurora.cinema.domain.Booking;
import com.aurora.cinema.domain.Seat;
import java.util.List;

/** What the API shows of a booking: no email (Section 46.3). */
public record BookingDto(String code, String showingId, String customer,
                         List<String> seats, long totalCents, String status) {
    static BookingDto from(Booking b) {
        return new BookingDto(b.code(), b.showingId(), b.customer(),
                b.seats().stream().map(Seat::toString).toList(), b.totalCents(), b.status().name());
    }
}
