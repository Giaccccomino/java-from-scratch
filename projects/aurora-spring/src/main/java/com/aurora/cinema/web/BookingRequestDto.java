package com.aurora.cinema.web;

import com.aurora.cinema.service.BookingRequest;
import jakarta.validation.constraints.*;
import java.util.List;

public record BookingRequestDto(
        @NotBlank String showingId,
        @NotBlank @Size(max = 100) String customer,
        @NotBlank @Email String email,
        @NotEmpty @Size(max = 10) List<@Pattern(regexp = "[A-Za-z][0-9]{1,2}") String> seats) {

    BookingRequest toRequest() {
        return new BookingRequest(showingId, customer, email, seats);
    }
}
