ALTER TABLE event_participant
    ADD CONSTRAINT pk_event_participant PRIMARY KEY (event_id, participant_id);