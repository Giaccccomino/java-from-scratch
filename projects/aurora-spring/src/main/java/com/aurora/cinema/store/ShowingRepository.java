package com.aurora.cinema.store;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowingRepository extends JpaRepository<ShowingEntity, String> {
    List<ShowingEntity> findAllByOrderByStart();
}
