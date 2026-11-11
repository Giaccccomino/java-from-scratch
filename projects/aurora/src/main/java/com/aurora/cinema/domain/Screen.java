package com.aurora.cinema.domain;

/** A screen with identical rows of seats: rows A, B, C... and seats numbered from 1. */
public record Screen(String id, String name, int rows, int seatsPerRow) {

    public Screen {
        if (rows < 1 || rows > 26 || seatsPerRow < 1 || seatsPerRow > 99)
            throw new RuleException("invalid screen size");
    }

    public int capacity() { return rows * seatsPerRow; }

    public boolean contains(Seat s) {
        return s.row() - 'A' < rows && s.number() <= seatsPerRow;
    }
}
