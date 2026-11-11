package com.aurora.cinema.service;

import com.aurora.cinema.domain.RuleException;
import java.util.List;

/** What the customer asks for. It comes from outside: every field is checked. */
public record BookingRequest(String showingId, String customer, String email, List<String> seats) {
    public BookingRequest {
        if (showingId == null || showingId.isBlank()) throw new RuleException("missing showing");
        if (customer == null || customer.isBlank()) throw new RuleException("missing customer name");
        if (email == null || !email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) throw new RuleException("invalid email");
        if (seats == null || seats.isEmpty()) throw new RuleException("no seats requested");
        seats = List.copyOf(seats);
    }
}
