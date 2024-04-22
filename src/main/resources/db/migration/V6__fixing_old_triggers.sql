DROP TRIGGER IF EXISTS create_incomes_and_drivers_payment_trigger ON transactions;

CREATE OR REPLACE FUNCTION create_incomes_and_drivers_payment()
RETURNS TRIGGER AS $$
DECLARE
    income_amount NUMERIC;
    driver_payment_amount NUMERIC;
BEGIN
    income_amount := NEW.sum * (SELECT percent / 100.0 FROM settings);
    driver_payment_amount := NEW.sum - income_amount;

    INSERT INTO incomes (id, date, sum, description)
    VALUES (NEW.id ,CURRENT_TIMESTAMP, income_amount, 'Income from trip');

    INSERT INTO drivers_payment (id, sum)
    VALUES (NEW.id ,driver_payment_amount);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER create_incomes_and_drivers_payment_trigger
AFTER UPDATE OF status ON transactions
FOR EACH ROW
WHEN (OLD.status <> 'Completed' AND NEW.status = 'Completed')
EXECUTE FUNCTION create_incomes_and_drivers_payment();