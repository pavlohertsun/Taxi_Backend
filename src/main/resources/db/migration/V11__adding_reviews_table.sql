ALTER TABLE drivers_rating
DROP COLUMN comments;

CREATE TABLE reviews(
    id BIGINT PRIMARY KEY,
    comment VARCHAR(250),
    driver_id BIGINT
);

ALTER TABLE reviews
ADD CONSTRAINT drivers_review
FOREIGN KEY (driver_id)
REFERENCES drivers_rating(id);

ALTER TABLE trips_rating
DROP COLUMN rate;

ALTER TABLE trips_rating
ADD COLUMN c_rate INT;

ALTER TABLE trips_rating
ADD COLUMN d_rate INT;