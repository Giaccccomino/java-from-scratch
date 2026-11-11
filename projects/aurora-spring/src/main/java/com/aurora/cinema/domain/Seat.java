package com.aurora.cinema.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** A seat in a screen, like "C5": row (letter) and number. */
public record Seat(char row, int number) implements Comparable<Seat> {

    public Seat {
        if (row < 'A' || row > 'Z') throw new RuleException("invalid row: " + row);
        if (number < 1 || number > 99) throw new RuleException("invalid seat number: " + number);
    }

    @JsonCreator
    public static Seat of(String code) {
        if (code == null || !code.matches("[A-Za-z][0-9]{1,2}"))
            throw new RuleException("invalid seat code: " + code);
        return new Seat(Character.toUpperCase(code.charAt(0)), Integer.parseInt(code.substring(1)));
    }

    @Override
    public int compareTo(Seat other) {
        return row != other.row ? Character.compare(row, other.row) : Integer.compare(number, other.number);
    }

    @JsonValue
    @Override
    public String toString() { return "" + row + number; }
}
