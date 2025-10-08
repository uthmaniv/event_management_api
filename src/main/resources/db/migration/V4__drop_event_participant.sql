DROP TABLE event-participant;

ALTER TABLE participant
ADD COLUMN event_id NOT NULL,
CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE;
