ALTER TABLE event
    ADD CONSTRAINT uq_title UNIQUE (title);

ALTER TABLE participant
    ADD CONSTRAINT uq_email UNIQUE (email),
    ADD CONSTRAINT uq_phone_no UNIQUE (phone_no);
