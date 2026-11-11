package com.aurora.cinema.store;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, String> {
    List<BookingEntity> findByShowingId(String showingId);
}
