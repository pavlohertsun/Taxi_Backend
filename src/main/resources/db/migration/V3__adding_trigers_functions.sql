CREATE OR REPLACE FUNCTION update_transaction_status()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE transactions
    SET status = 'Completed'
    WHERE trip_id = NEW.id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_transaction_status_trigger
AFTER UPDATE OF status ON trips
FOR EACH ROW
WHEN (OLD.status <> 'Completed' AND NEW.status = 'Completed')
EXECUTE FUNCTION update_transaction_status();
