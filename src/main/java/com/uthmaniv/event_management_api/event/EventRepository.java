package com.uthmaniv.event_management_api.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByTitle(String title);

    Optional<Event> findByDescription(String description);

    Optional<List<Event> >findByLocation(String location);

    boolean existsByTitle(String title);
}
