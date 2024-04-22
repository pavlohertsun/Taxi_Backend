CREATE OR REPLACE FUNCTION set_registration_date()
RETURNS TRIGGER AS $$
BEGIN
    NEW.reg_date := CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_registration_date_trigger
BEFORE INSERT ON users
FOR EACH ROW
EXECUTE FUNCTION set_registration_date();
