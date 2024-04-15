CREATE OR REPLACE FUNCTION add_start_time()
RETURNS TRIGGER AS $$
BEGIN
  NEW.start_time := CURRENT_TIMESTAMP;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_start_time_trigger
BEFORE INSERT ON trips
FOR EACH ROW
EXECUTE FUNCTION add_start_time();

CREATE OR REPLACE FUNCTION update_end_time()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.status = 'Completed' THEN
    NEW.end_time := CURRENT_TIMESTAMP;
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_end_time_trigger
BEFORE UPDATE ON trips
FOR EACH ROW
EXECUTE FUNCTION update_end_time();
