-- Aurora Cinema: the schema and data used in Chapter 42.
-- Works in H2 and PostgreSQL. Load it with Jdbc.java, or paste it into your database's console.
CREATE TABLE screens (
    id        INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name      VARCHAR(50) NOT NULL UNIQUE,
    capacity  INTEGER NOT NULL CHECK (capacity > 0)
);
CREATE TABLE showings (
    id           INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    movie        VARCHAR(200) NOT NULL,
    screen_id    INTEGER NOT NULL REFERENCES screens(id),
    starts_at    TIMESTAMP NOT NULL,
    price_cents  INTEGER NOT NULL
);
CREATE TABLE bookings (
    id          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    showing_id  INTEGER NOT NULL REFERENCES showings(id),
    customer    VARCHAR(100) NOT NULL,
    seat        VARCHAR(4) NOT NULL,
    UNIQUE (showing_id, seat)
);
INSERT INTO screens (name, capacity) VALUES ('Screen 1', 120), ('Screen 2', 80), ('Screen 3', 45);
INSERT INTO showings (movie, screen_id, starts_at, price_cents) VALUES
    ('The Deep Blue',       2, '2026-10-30 21:15', 950),
    ('A Night in the City', 1, '2026-10-30 20:30', 950),
    ('A Night in the City', 1, '2026-10-31 18:00', 700),
    ('Éclair at Dawn',      3, '2026-10-31 21:00', 950);
INSERT INTO bookings (showing_id, customer, seat) VALUES
    (1, 'Julia Ferris', 'C5'), (1, 'Julia Ferris', 'C6'),
    (1, 'Mark Brown', 'D1'), (2, 'Anna Smith', 'A1'), (4, 'Mark Brown', 'F7');
