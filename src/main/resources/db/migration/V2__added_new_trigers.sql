CREATE OR REPLACE FUNCTION create_transaction_for_trip()
RETURNS TRIGGER AS $$
BEGIN
  INSERT INTO transactions (date, sum, status, trip_id)
  VALUES (CURRENT_TIMESTAMP, NEW.price, 'In progress', NEW.id);
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER create_transaction_trigger
AFTER INSERT ON trips
FOR EACH ROW
EXECUTE FUNCTION create_transaction_for_trip();

CREATE OR REPLACE FUNCTION create_incomes_and_drivers_payment()
RETURNS TRIGGER AS $$
DECLARE
    income_amount NUMERIC;
    driver_payment_amount NUMERIC;
BEGIN
    income_amount := NEW.sum * (SELECT percent / 100.0 FROM settings);
    driver_payment_amount := NEW.sum - income_amount;

    INSERT INTO incomes (date, sum, description)
    VALUES (CURRENT_TIMESTAMP, income_amount, 'Income from trip');

    INSERT INTO drivers_payment (sum)
    VALUES (driver_payment_amount);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER create_incomes_and_drivers_payment_trigger
AFTER UPDATE OF status ON transactions
FOR EACH ROW
WHEN (OLD.status <> 'Completed' AND NEW.status = 'Completed')
EXECUTE FUNCTION create_incomes_and_drivers_payment();
