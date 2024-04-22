DROP TRIGGER IF EXISTS update_customer_balance_trigger ON trips;

CREATE OR REPLACE FUNCTION update_customer_balance()
RETURNS TRIGGER AS $$
BEGIN
    DECLARE
        current_balance NUMERIC;
    BEGIN
        SELECT balance INTO current_balance
        FROM customers
        WHERE id = NEW.customer_id
        FOR UPDATE;

        UPDATE customers
        SET balance = current_balance - NEW.price
        WHERE id = NEW.customer_id;
    END;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_customer_balance_trigger
AFTER UPDATE OF status ON trips
FOR EACH ROW
WHEN (OLD.status <> 'Completed' AND NEW.status = 'Completed')
EXECUTE FUNCTION update_customer_balance();