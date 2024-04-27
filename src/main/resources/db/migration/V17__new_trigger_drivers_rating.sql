CREATE OR REPLACE FUNCTION insert_driver_rating()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO drivers_rating (id, rating, trips_count)
    VALUES (NEW.id, 5.0, 0);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_driver_rating_trigger
AFTER INSERT ON drivers
FOR EACH ROW
EXECUTE FUNCTION insert_driver_rating();
