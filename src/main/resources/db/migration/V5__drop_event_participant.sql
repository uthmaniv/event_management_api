DROP TABLE event_participant;

ALTER TABLE participant
ADD COLUMN event_id INT NOT NULL,
CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE;

ALTER TABLE event
ADD CONSTRAINT unique_event_combination UNIQUE (title, location, date_time);
