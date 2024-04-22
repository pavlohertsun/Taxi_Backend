CREATE OR REPLACE FUNCTION update_driver_balance()
RETURNS TRIGGER AS $$
DECLARE
    driver_id BIGINT;
BEGIN
    SELECT t.driver_id INTO driver_id
    FROM trips t
    INNER JOIN transactions tr ON t.id = tr.trip_id
    WHERE tr.id = NEW.id;

    UPDATE drivers
    SET balance = balance + NEW.sum
    WHERE id = driver_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_driver_balance_trigger
AFTER INSERT ON drivers_payment
FOR EACH ROW
EXECUTE FUNCTION update_driver_balance();
