ALTER TABLE trips
ALTER COLUMN status TYPE VARCHAR(20),
ADD CONSTRAINT status_check CHECK (status IN ('Created', 'In progress', 'Completed', 'Cancelled'));

ALTER TABLE customers
ADD COLUMN balance NUMERIC;

ALTER TABLE drivers
ALTER COLUMN license TYPE BOOLEAN
USING license::BOOLEAN;

ALTER TABLE cars
DROP COLUMN type,
ALTER COLUMN document TYPE BOOLEAN
USING document::BOOLEAN;
