package com.aurora.cinema.model;

public class Screen {
    private final String name;
    int capacity;
    public Screen(String name, int capacity) { this.name = name; this.capacity = capacity; }
    public String getName() { return name; }
}
