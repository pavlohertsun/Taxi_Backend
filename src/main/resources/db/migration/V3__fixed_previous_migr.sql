ALTER TABLE trips
DROP CONSTRAINT trips_status_check;

ALTER TABLE trips
ADD CONSTRAINT trips_status_check CHECK (status IN ('Created', 'In progress', 'Completed', 'Cancelled'));
