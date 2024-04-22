CREATE OR REPLACE FUNCTION update_driver_rating_trips_count()
RETURNS TRIGGER AS $$
BEGIN
    IF OLD.driver_id IS NULL AND NEW.driver_id IS NOT NULL THEN
        UPDATE drivers_rating
        SET trips_count = trips_count + 1
        WHERE id = NEW.driver_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION update_customer_rating()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.d_rate IS NOT NULL AND OLD.d_rate IS NULL THEN
        UPDATE customers
        SET rating = (rating + NEW.d_rate) / 2
        WHERE id = (SELECT customer_id FROM trips WHERE id = NEW.id);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;