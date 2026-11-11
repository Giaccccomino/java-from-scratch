package com.aurora.cinema.domain;

/** The request breaks one of the cinema's rules. */
public class RuleException extends AuroraException {
    public RuleException(String message) { super(message); }
}
