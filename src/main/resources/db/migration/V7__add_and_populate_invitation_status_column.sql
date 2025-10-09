-- Add the new column (nullable first to avoid constraint errors)
ALTER TABLE participant
ADD COLUMN invitation_status VARCHAR(20);

-- Populate it with default value 'PENDING' for all existing participants
UPDATE participant
SET invitation_status = 'PENDING'
WHERE invitation_status IS NULL;

-- Step 3: Enforce NOT NULL constraint now that all rows have values
ALTER TABLE participant
ALTER COLUMN invitation_status SET NOT NULL;

-- Add a check constraint to enforce allowed enum values
ALTER TABLE participant
ADD CONSTRAINT chk_invitation_status
CHECK (invitation_status IN ('PENDING', 'ACCEPTED', 'DECLINED'));
