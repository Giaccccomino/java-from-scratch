package com.aurora.cinema.domain;

/** The root of the domain's exceptions: all unchecked. */
public class AuroraException extends RuntimeException {
    public AuroraException(String message) { super(message); }
}
