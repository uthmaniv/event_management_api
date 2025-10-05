package com.uthmaniv.event_management_api.participant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant,Long> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(Long phoneNumber);
    Optional<Participant> findByEmail(String email);
}
