CREATE OR REPLACE FUNCTION update_driver_rating()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.c_rate IS NOT NULL AND OLD.c_rate IS NULL AND NEW.c_rate != 0 THEN
        UPDATE drivers_rating
        SET rating = (rating + NEW.c_rate) / 2
        WHERE id = (SELECT driver_id FROM trips WHERE id = NEW.id);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION update_customer_rating()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.d_rate IS NOT NULL AND OLD.d_rate IS NULL AND NEW.d_rate != 0 THEN
        UPDATE customers
        SET rating = (rating + NEW.d_rate) / 2
        WHERE id = (SELECT customer_id FROM trips WHERE id = NEW.id);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
