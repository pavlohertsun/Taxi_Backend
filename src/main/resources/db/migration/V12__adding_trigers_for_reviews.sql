CREATE SEQUENCE reviews_seq START 1;

ALTER TABLE reviews ALTER COLUMN id SET DEFAULT nextval('reviews_seq');

CREATE OR REPLACE FUNCTION update_driver_rating_trips_count()
RETURNS TRIGGER AS $$
BEGIN
    IF OLD.driver_id IS NULL AND NEW.driver_id IS NOT NULL THEN
        UPDATE drivers_rating
        SET trips_count = trips_count + 1
        WHERE driver_id = NEW.driver_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_driver_rating_trips_count_trigger
AFTER UPDATE OF driver_id ON trips
FOR EACH ROW
EXECUTE FUNCTION update_driver_rating_trips_count();

CREATE OR REPLACE FUNCTION update_customer_rating()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.d_rate IS NOT NULL AND OLD.d_rate IS NULL THEN
        UPDATE customers
        SET rating = (rating + NEW.d_rate) / 2
        WHERE id = (SELECT user_id FROM trips WHERE id = NEW.id);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_customer_rating_trigger
AFTER INSERT OR UPDATE ON trips_rating
FOR EACH ROW
EXECUTE FUNCTION update_customer_rating();

CREATE OR REPLACE FUNCTION update_driver_rating()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.c_rate IS NOT NULL AND OLD.c_rate IS NULL THEN
        UPDATE drivers_rating
        SET rating = (rating + NEW.c_rate) / 2
        WHERE id = (SELECT driver_id FROM trips WHERE id = NEW.id);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_driver_rating_trigger
AFTER INSERT OR UPDATE ON trips_rating
FOR EACH ROW
EXECUTE FUNCTION update_driver_rating();

CREATE OR REPLACE FUNCTION insert_into_reviews()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.comment IS NOT NULL AND (OLD.comment IS NULL OR OLD.comment = '') THEN
        INSERT INTO reviews (comment, driver_id)
        SELECT NEW.comment, trips.driver_id
        FROM trips
        WHERE trips.id = NEW.id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_into_reviews_trigger
AFTER INSERT OR UPDATE ON trips_rating
FOR EACH ROW
EXECUTE FUNCTION insert_into_reviews();
