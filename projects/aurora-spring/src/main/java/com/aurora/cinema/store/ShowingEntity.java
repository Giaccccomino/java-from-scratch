package com.aurora.cinema.store;

import com.aurora.cinema.domain.Showing;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "showings")
public class ShowingEntity {
    @Id
    private String id;
    private String movie;
    private String screenId;
    @Column(name = "starts_at")                     // START is a reserved word in some databases
    private LocalDateTime start;
    private long priceCents;

    protected ShowingEntity() {}

    public static ShowingEntity from(Showing s) {
        ShowingEntity e = new ShowingEntity();
        e.id = s.id(); e.movie = s.movie(); e.screenId = s.screenId(); e.start = s.start(); e.priceCents = s.priceCents();
        return e;
    }

    public Showing toDomain() { return new Showing(id, movie, screenId, start, priceCents); }
}
