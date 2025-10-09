
UPDATE participant SET event_id = 1 WHERE id = 1;
UPDATE participant SET event_id = 1 WHERE id = 2;
UPDATE participant SET event_id = 2 WHERE id = 3;
UPDATE participant SET event_id = 2 WHERE id = 4;
UPDATE participant SET event_id = 2 WHERE id = 5;
UPDATE participant SET event_id = 3 WHERE id = 7;
UPDATE participant SET event_id = 3 WHERE id = 8;
UPDATE participant SET event_id = 3 WHERE id = 9;
UPDATE participant SET event_id = 1 WHERE id = 10;
UPDATE participant SET event_id = 1 WHERE id = 11;
UPDATE participant SET event_id = 1 WHERE id = 12;

-- Enforce the NOT NULL constraint now that all rows have event_id values
ALTER TABLE participant
ALTER COLUMN event_id SET NOT NULL;
