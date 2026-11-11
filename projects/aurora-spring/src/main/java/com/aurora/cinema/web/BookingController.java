package com.aurora.cinema.web;

import com.aurora.cinema.domain.*;
import com.aurora.cinema.service.BookingService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BookingController {
    private final BookingService service;

    public BookingController(BookingService service) {     // constructor injection
        this.service = service;
    }

    @GetMapping("/showings")
    public List<Showing> schedule() {
        return service.schedule();
    }

    @GetMapping("/showings/{id}/seats")
    public SeatStatusDto seats(@PathVariable String id) {
        return new SeatStatusDto(id, service.freeSeats(id),
                service.takenSeats(id).stream().map(Seat::toString).toList());
    }

    @PostMapping("/bookings")
    public ResponseEntity<BookingDto> book(@Valid @RequestBody BookingRequestDto request) {
        Booking b = service.book(request.toRequest());
        return ResponseEntity
                .created(URI.create("/api/bookings/" + b.code()))      // 201 + Location
                .body(BookingDto.from(b));
    }

    @DeleteMapping("/bookings/{code}")
    public BookingDto cancel(@PathVariable String code) {
        return BookingDto.from(service.cancel(code));
    }
}
