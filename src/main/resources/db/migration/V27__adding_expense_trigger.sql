CREATE OR REPLACE FUNCTION insert_expense_date()
RETURNS TRIGGER AS $$
BEGIN
    NEW.date := CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_expense_date_trigger
BEFORE INSERT ON expenses
FOR EACH ROW
EXECUTE FUNCTION insert_expense_date();