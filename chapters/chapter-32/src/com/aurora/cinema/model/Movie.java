package com.aurora.cinema.model;

public record Movie(String title, int duration) {
    public String durationInHours() { return duration / 60 + "h " + duration % 60 + "min"; }
}
