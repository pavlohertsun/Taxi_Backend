CREATE OR REPLACE FUNCTION insert_support_request()
RETURNS TRIGGER AS $$
BEGIN
    NEW.date := CURRENT_TIMESTAMP;
    NEW.status := 'Processing';

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_support_request_trigger
BEFORE INSERT ON support_requests
FOR EACH ROW
EXECUTE FUNCTION insert_support_request();