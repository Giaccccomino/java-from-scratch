package com.aurora.cinema.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.junit.jupiter.api.Test;

class SeatTest {
    @Test
    void fromCode() {
        assertEquals(new Seat('C', 5), Seat.of("C5"));
        assertEquals(new Seat('C', 12), Seat.of("c12"));
        assertEquals("C5", Seat.of("c5").toString());
    }

    @Test
    void invalidCodes() {
        for (String s : new String[]{"5C", "C", "C123", "", "C-1", "CC5"})
            assertThrows(RuleException.class, () -> Seat.of(s), s);
        assertThrows(RuleException.class, () -> Seat.of(null));
        assertThrows(RuleException.class, () -> Seat.of("C0"));
    }

    @Test
    void naturalOrder() {
        List<Seat> seats = new ArrayList<>(List.of(Seat.of("C10"), Seat.of("C9"), Seat.of("A2"), Seat.of("C1")));
        Collections.sort(seats);
        assertEquals("[A2, C1, C9, C10]", seats.toString());
    }

    @Test
    void equalityInSets() {
        assertEquals(1, new HashSet<>(List.of(Seat.of("C5"), Seat.of("c5"))).size());
    }
}
