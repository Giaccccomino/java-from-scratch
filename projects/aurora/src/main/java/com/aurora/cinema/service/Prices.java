package com.aurora.cinema.service;

import com.aurora.cinema.domain.Showing;
import java.time.DayOfWeek;

/** The pricing rules: at most $6 on Wednesdays, 10% off from 10 tickets up. */
public class Prices {
    static final long WEDNESDAY_PRICE = 600;
    static final int TICKETS_FOR_GROUP_DISCOUNT = 10;
    static final int GROUP_PERCENT_PAID = 90;

    public long ticketPrice(Showing s) {
        long base = s.priceCents();
        return s.start().getDayOfWeek() == DayOfWeek.WEDNESDAY ? Math.min(base, WEDNESDAY_PRICE) : base;
    }

    public long total(Showing s, int tickets) {
        long gross = ticketPrice(s) * tickets;
        return tickets >= TICKETS_FOR_GROUP_DISCOUNT ? gross * GROUP_PERCENT_PAID / 100 : gross;
    }
}
