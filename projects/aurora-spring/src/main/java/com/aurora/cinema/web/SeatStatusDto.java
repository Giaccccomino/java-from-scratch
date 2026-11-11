package com.aurora.cinema.web;

import java.util.List;

public record SeatStatusDto(String showing, int free, List<String> taken) {}
