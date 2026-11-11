package com.aurora.cinema.store;

import com.aurora.cinema.domain.Screen;
import jakarta.persistence.*;

@Entity
@Table(name = "screens")
public class ScreenEntity {
    @Id
    private String id;
    private String name;
    @Column(name = "seat_rows")                     // ROWS is a reserved word in SQL
    private int rows;
    private int seatsPerRow;

    protected ScreenEntity() {}                     // required by JPA

    public static ScreenEntity from(Screen s) {
        ScreenEntity e = new ScreenEntity();
        e.id = s.id(); e.name = s.name(); e.rows = s.rows(); e.seatsPerRow = s.seatsPerRow();
        return e;
    }

    public Screen toDomain() { return new Screen(id, name, rows, seatsPerRow); }
}
