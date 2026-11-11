package com.aurora.cinema.store;

import com.aurora.cinema.domain.*;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookings")
public class BookingEntity {
    @Id
    private String code;
    private String showingId;
    private String customer;
    private String email;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "booking_seats", joinColumns = @JoinColumn(name = "code"))
    @Column(name = "seat")
    private List<String> seats = new ArrayList<>();
    private long totalCents;
    private Instant created;
    @Enumerated(EnumType.STRING)
    private Booking.Status status;

    protected BookingEntity() {}                    // required by JPA

    public static BookingEntity from(Booking b) {
        BookingEntity e = new BookingEntity();
        e.code = b.code(); e.showingId = b.showingId(); e.customer = b.customer(); e.email = b.email();
        e.seats = new ArrayList<>(b.seats().stream().map(Seat::toString).toList());
        e.totalCents = b.totalCents(); e.created = b.created(); e.status = b.status();
        return e;
    }

    public Booking toDomain() {
        return new Booking(code, showingId, customer, email,
                seats.stream().map(Seat::of).toList(), totalCents, created, status);
    }
}
