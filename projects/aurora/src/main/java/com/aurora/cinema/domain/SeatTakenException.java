package com.aurora.cinema.domain;

import java.util.Collection;

/** One or more of the requested seats are already taken: "seats already taken: [C5]". */
public class SeatTakenException extends AuroraException {
    public SeatTakenException(Collection<Seat> seats) { super("seats already taken: " + seats); }
}
