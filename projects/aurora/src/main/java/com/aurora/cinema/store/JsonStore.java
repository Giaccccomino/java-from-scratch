package com.aurora.cinema.store;

import com.aurora.cinema.domain.Booking;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;

/** An in-memory store that saves the bookings to a JSON file on every change. */
public class JsonStore extends InMemoryStore {
    private final Path file;
    private final ObjectMapper json = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(SerializationFeature.INDENT_OUTPUT);

    public JsonStore(Path file) {
        this.file = file;
        if (Files.exists(file)) {
            try {
                List<Booking> read = json.readValue(file.toFile(), new TypeReference<List<Booking>>() {});
                read.forEach(b -> bookings.put(b.code(), b));
            } catch (IOException e) {
                throw new UncheckedIOException("unreadable store: " + file, e);
            }
        }
    }

    @Override
    public synchronized void save(Booking b) {
        super.save(b);
        List<Booking> all = bookings.values().stream()
                .sorted(Comparator.comparing(Booking::code)).toList();
        try {
            Path temp = file.resolveSibling(file.getFileName() + ".tmp");
            json.writeValue(temp.toFile(), all);
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            throw new UncheckedIOException("save failed", e);
        }
    }
}
