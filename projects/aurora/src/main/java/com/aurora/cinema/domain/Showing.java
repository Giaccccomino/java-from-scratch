package com.aurora.cinema.domain;

import java.time.LocalDateTime;

public record Showing(String id, String movie, String screenId, LocalDateTime start, long priceCents) {}
