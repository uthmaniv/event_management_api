-- Create event table
CREATE TABLE event(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    date_time TIMESTAMP NOT NULL
);

-- Create participant table
CREATE TABLE participant(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_no BIGINT NOT NULL UNIQUE
);

-- Create event_participant table
CREATE TABLE event_participant(
    event_id BIGINT NOT NULL,
    participant_id BIGINT NOT NULL,
    CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE,
    CONSTRAINT fk_participant_id FOREIGN KEY (participant_id) REFERENCES participant(id) ON DELETE CASCADE
);