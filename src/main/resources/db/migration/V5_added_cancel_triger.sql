CREATE OR REPLACE FUNCTION update_end_time_on_cancel()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.status = 'Cancelled' THEN
    NEW.end_time := CURRENT_TIMESTAMP;
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_end_time_trigger
BEFORE UPDATE ON trips
FOR EACH ROW
EXECUTE FUNCTION update_end_time_on_cancel();