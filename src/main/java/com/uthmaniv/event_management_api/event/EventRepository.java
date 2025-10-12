package com.uthmaniv.event_management_api.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByTitleAndUserId(String title, Long userId);

    Optional<Event> findByDescriptionAndUserId(String description, Long userId);

    Optional<List<Event> > findByLocationAndUserId(String location, Long userId);

    List<Event> findByUserId(Long userId);

    Optional<Event> findByIdAndUserId(Long id, Long userId);

    boolean existsByTitleAndLocationAndDateTime(String title, String location, LocalDateTime dateTime);
}
