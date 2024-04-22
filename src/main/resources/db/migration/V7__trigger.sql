CREATE OR REPLACE FUNCTION update_transaction_status_on_trip_cancelled()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE transactions
    SET status = 'Cancelled'
    WHERE trip_id = NEW.id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_transaction_status_on_trip_cancelled_trigger
AFTER UPDATE OF status ON trips
FOR EACH ROW
WHEN (OLD.status <> 'Cancelled' AND NEW.status = 'Cancelled')
EXECUTE FUNCTION update_transaction_status_on_trip_cancelled();
