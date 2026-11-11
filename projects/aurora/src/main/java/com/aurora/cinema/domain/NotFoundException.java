package com.aurora.cinema.domain;

/** Something requested doesn't exist: "showing SP9 does not exist". */
public class NotFoundException extends AuroraException {
    public NotFoundException(String what, String id) { super(what + " " + id + " does not exist"); }
}
