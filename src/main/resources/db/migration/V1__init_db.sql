CREATE TABLE customers(
  id BIGINT PRIMARY KEY,
  name VARCHAR(50) NOT NULL CHECK (LENGTH(name) >= 2),
  surname VARCHAR(50) NOT NULL CHECK (LENGTH(surname) >= 2),
  email VARCHAR(255) NOT NULL CHECK (email LIKE '%@%'),
  phone_number VARCHAR(13) NOT NULL,
  rating DECIMAL(3,2) NOT NULL,
  balance NUMERIC
  CONSTRAINT check_phone_number CHECK (phone_number LIKE '+380%' OR phone_number LIKE '380%')
);

CREATE TABLE drivers(
  id BIGINT PRIMARY KEY,
  name VARCHAR(50) NOT NULL CHECK (LENGTH(name) >= 2),
  surname VARCHAR(50) NOT NULL CHECK (LENGTH(name) >= 2),
  email VARCHAR(150) NOT NULL CHECK (email LIKE '%@%'),
  phone_number VARCHAR(13) NOT NULL,
  license BOOLEAN NOT NULL,
  status VARCHAR(30) NOT NULL CHECK(status IN ('Authenticated', 'Non-authenticated')),
  balance NUMERIC
  CONSTRAINT check_phone_number CHECK (phone_number LIKE '+380%' OR phone_number LIKE '380%')
);

CREATE TABLE trips(
  id BIGINT PRIMARY KEY,
  start_time TIMESTAMP NOT NULL,
  end_time TIMESTAMP,
  start_point VARCHAR(255) NOT NULL,
  end_point VARCHAR(255) NOT NULL,
  price NUMERIC NOT NULL,
  status VARCHAR(11) NOT NULL CHECK(status IN ('Created', 'In progress', 'Completed', 'Cancelled')),
  rate VARCHAR(6) NOT NULL CHECK(rate IN ('Low', 'Normal', 'High')),
  description TEXT,
  customer_id BIGINT NOT NULL,
  driver_id BIGINT
);

CREATE TABLE cars(
  id BIGINT PRIMARY KEY,
  license_plate VARCHAR(20) NOT NULL,
  document BOOLEAN NOT NULL
);

CREATE TABLE trips_rating(
  id BIGINT PRIMARY KEY,
  c_rate INT,
  d_rate INT,
  comment TEXT
);

CREATE TABLE transactions(
  id BIGINT PRIMARY KEY,
  date TIMESTAMP NOT NULL,
  sum NUMERIC NOT NULL,
  status VARCHAR(11) NOT NULL CHECK(status IN ('In progress', 'Completed', 'Cancelled')),
  trip_id BIGINT NOT NULL
);

CREATE TABLE incomes(
  id BIGINT PRIMARY KEY,
  date TIMESTAMP NOT NULL,
  sum NUMERIC NOT NULL,
  description TEXT
);

CREATE TABLE expenses(
  id bigint PRIMARY KEY,
  date TIMESTAMP NOT NULL,
  sum NUMERIC NOT NULL,
  type VARCHAR(255) NOT NULL,
  description TEXT
);

CREATE TABLE drivers_payment(
  id BIGINT PRIMARY KEY,
  sum NUMERIC NOT NULL
);

CREATE TABLE employees(
  id BIGINT PRIMARY KEY,
  name VARCHAR(50) NOT NULL CHECK (LENGTH(name) >= 2),
  surname VARCHAR(50) NOT NULL CHECK (LENGTH(name) >= 2),
  email VARCHAR(255) NOT NULL CHECK (email LIKE '%@%'),
  position VARCHAR(50) NOT NULL,
  salary NUMERIC
);

CREATE TABLE support_requests(
  id BIGINT PRIMARY KEY,
  date TIMESTAMP NOT NULL,
  request VARCHAR(500) NOT NULL,
  response VARCHAR(500),
  status VARCHAR(10) NOT NULL CHECK (status in ('Processing', 'Processed')),
  customer_id BIGINT,
  driver_id BIGINT
);

CREATE TABLE drivers_rating(
  id BIGINT PRIMARY KEY,
  rating DECIMAL(3,2) NOT NULL,
  trips_count INT NOT NULL
);

CREATE TABLE users(
    id BIGINT PRIMARY KEY,
    username VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(150) NOT NULL,
    reg_date TIMESTAMP NOT NULL,
    role INT NOT NULL
);

CREATE TABLE settings(
    id BIGINT PRIMARY KEY,
    rate VARCHAR(20) NOT NULL CHECK(rate IN ('Low', 'Normal', 'High')),
    percent INT NOT NULL
);

CREATE TABLE reviews(
    id BIGINT PRIMARY KEY,
    comment VARCHAR(250) NOT NULL,
    driver_id BIGINT NOT NULL
);
CREATE TABLE logs(
    id BIGINT PRIMARY KEY,
    message TEXT NOT NULL,
    date TIMESTAMP NOT NULL
);

CREATE SEQUENCE customers_seq START 1;
CREATE SEQUENCE drivers_seq START 1;
CREATE SEQUENCE trips_seq START 1;
CREATE SEQUENCE cars_seq START 1;
CREATE SEQUENCE trips_rating_seq START 1;
CREATE SEQUENCE transactions_seq START 1;
CREATE SEQUENCE incomes_seq START 1;
CREATE SEQUENCE expenses_seq START 1;
CREATE SEQUENCE drivers_payment_seq START 1;
CREATE SEQUENCE employees_seq START 1;
CREATE SEQUENCE support_requests_seq START 1;
CREATE SEQUENCE drivers_rating_seq START 1;
CREATE SEQUENCE users_seq START 1;
CREATE SEQUENCE reviews_seq START 1;
CREATE SEQUENCE logs_seq START 1;

ALTER TABLE customers ALTER COLUMN id SET DEFAULT nextval('customers_seq');
ALTER TABLE drivers ALTER COLUMN id SET DEFAULT nextval('drivers_seq');
ALTER TABLE trips ALTER COLUMN id SET DEFAULT nextval('trips_seq');
ALTER TABLE cars ALTER COLUMN id SET DEFAULT nextval('cars_seq');
ALTER TABLE trips_rating ALTER COLUMN id SET DEFAULT nextval('trips_rating_seq');
ALTER TABLE transactions ALTER COLUMN id SET DEFAULT nextval('transactions_seq');
ALTER TABLE incomes ALTER COLUMN id SET DEFAULT nextval('incomes_seq');
ALTER TABLE expenses ALTER COLUMN id SET DEFAULT nextval('expenses_seq');
ALTER TABLE drivers_payment ALTER COLUMN id SET DEFAULT nextval('drivers_payment_seq');
ALTER TABLE employees ALTER COLUMN id SET DEFAULT nextval('employees_seq');
ALTER TABLE support_requests ALTER COLUMN id SET DEFAULT nextval('support_requests_seq');
ALTER TABLE drivers_rating ALTER COLUMN id SET DEFAULT nextval('drivers_rating_seq');
ALTER TABLE users ALTER COLUMN id SET DEFAULT nextval('users_seq');
ALTER TABLE reviews ALTER COLUMN id SET DEFAULT nextval('reviews_seq');
ALTER TABLE logs ALTER COLUMN id SET DEFAULT nextval('logs_seq');

ALTER TABLE trips
ADD CONSTRAINT customer_id_fk
FOREIGN KEY (customer_id)
REFERENCES customers(id);

ALTER TABLE trips
ADD CONSTRAINT driver_id_fk
FOREIGN KEY (driver_id)
REFERENCES drivers(id);

ALTER TABLE cars
ADD CONSTRAINT driver_id_fk
FOREIGN KEY (id)
REFERENCES drivers(id);

ALTER TABLE trips_rating
ADD CONSTRAINT trip_id_fk
FOREIGN KEY (id)
REFERENCES trips(id);

ALTER TABLE transactions
ADD CONSTRAINT trip_id_fk
FOREIGN KEY (trip_id)
REFERENCES trips(id);

ALTER TABLE drivers_payment
ADD CONSTRAINT transactions_id_fk
FOREIGN KEY (id)
REFERENCES transactions(id);

ALTER TABLE incomes
ADD CONSTRAINT transactions_id_fk
FOREIGN KEY (id)
REFERENCES transactions(id);

ALTER TABLE support_requests
ADD CONSTRAINT users_id_fk
FOREIGN KEY (customer_id)
REFERENCES customers(id);

ALTER TABLE support_requests
ADD CONSTRAINT drivers_id_fk
FOREIGN KEY (driver_id)
REFERENCES drivers(id);

ALTER TABLE drivers_rating
ADD CONSTRAINT drivers_id_fk
FOREIGN KEY (id)
REFERENCES drivers(id);

ALTER TABLE customers
ADD CONSTRAINT user_email_fk
FOREIGN KEY (email)
REFERENCES users(username);

ALTER TABLE drivers
ADD CONSTRAINT user_email_fk
FOREIGN KEY (email)
REFERENCES users(username);

ALTER TABLE employees
ADD CONSTRAINT user_email_fk
FOREIGN KEY (email)
REFERENCES users(username);

ALTER TABLE reviews
ADD CONSTRAINT drivers_review
FOREIGN KEY (driver_id)
REFERENCES drivers_rating(id);

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

CREATE OR REPLACE FUNCTION update_end_time_on_cancel()
RETURNS TRIGGER AS $$
BEGIN
  IF NEW.status = 'Cancelled' THEN
    NEW.end_time := CURRENT_TIMESTAMP;
  END IF;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_end_time_on_cancel_trigger
BEFORE UPDATE ON trips
FOR EACH ROW
EXECUTE FUNCTION update_end_time_on_cancel();

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

CREATE OR REPLACE FUNCTION update_driver_balance()
RETURNS TRIGGER AS $$
DECLARE
    driver_id BIGINT;
BEGIN
    SELECT t.driver_id INTO driver_id
    FROM trips t
    INNER JOIN transactions tr ON t.id = tr.trip_id
    WHERE tr.id = NEW.id;

    UPDATE drivers
    SET balance = balance + NEW.sum
    WHERE id = driver_id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_driver_balance_trigger
AFTER INSERT ON drivers_payment
FOR EACH ROW
EXECUTE FUNCTION update_driver_balance();

CREATE OR REPLACE FUNCTION update_driver_rating_trips_count()
RETURNS TRIGGER AS $$
BEGIN
    IF OLD.driver_id IS NULL AND NEW.driver_id IS NOT NULL THEN
        UPDATE drivers_rating
        SET trips_count = trips_count + 1
        WHERE id = NEW.driver_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_driver_rating_trips_count_trigger
AFTER UPDATE OF driver_id ON trips
FOR EACH ROW
EXECUTE FUNCTION update_driver_rating_trips_count();

CREATE OR REPLACE FUNCTION update_customer_rating()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.d_rate IS NOT NULL AND OLD.d_rate IS NULL AND NEW.d_rate != 0 THEN
        UPDATE customers
        SET rating = (rating + NEW.d_rate) / 2
        WHERE id = (SELECT customer_id FROM trips WHERE id = NEW.id);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_customer_rating_trigger
AFTER INSERT OR UPDATE ON trips_rating
FOR EACH ROW
EXECUTE FUNCTION update_customer_rating();

CREATE OR REPLACE FUNCTION update_driver_rating()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.c_rate IS NOT NULL AND OLD.c_rate IS NULL AND NEW.c_rate != 0 THEN
        UPDATE drivers_rating
        SET rating = (rating + NEW.c_rate) / 2
        WHERE id = (SELECT driver_id FROM trips WHERE id = NEW.id);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_driver_rating_trigger
AFTER INSERT OR UPDATE ON trips_rating
FOR EACH ROW
EXECUTE FUNCTION update_driver_rating();

CREATE OR REPLACE FUNCTION insert_into_reviews()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.comment IS NOT NULL AND (OLD.comment IS NULL OR OLD.comment = '') THEN
        INSERT INTO reviews (comment, driver_id)
        SELECT NEW.comment, trips.driver_id
        FROM trips
        WHERE trips.id = NEW.id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_into_reviews_trigger
AFTER INSERT OR UPDATE ON trips_rating
FOR EACH ROW
EXECUTE FUNCTION insert_into_reviews();

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

CREATE OR REPLACE FUNCTION insert_driver_rating()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO drivers_rating (id, rating, trips_count)
    VALUES (NEW.id, 5.0, 0);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_driver_rating_trigger
AFTER INSERT ON drivers
FOR EACH ROW
EXECUTE FUNCTION insert_driver_rating();

CREATE OR REPLACE FUNCTION get_customer_data(customer_id bigint)
RETURNS json AS $$
DECLARE
    customer_data json;
BEGIN
    SELECT json_build_object(
        'customer', json_build_object(
            'name', c.name,
            'surname', c.surname,
            'email', c.email,
            'phone_number', c.phone_number,
            'rating', c.rating,
            'balance', c.balance
        ),
        'trips', (
            SELECT json_agg(
                json_build_object(
                    'trip', json_build_object(
                        'start_time', t.start_time,
                        'end_time', t.end_time,
                        'start_point', t.start_point,
                        'end_point', t.end_point,
                        'price', t.price,
                        'description', t.description,
                        'driver', json_build_object(
                            'name', d.name
                        )
                    )
                )
            )
            FROM trips t
            JOIN drivers d ON t.driver_id = d.id
            WHERE c.id = t.customer_id
        ),
        'support_requests', (
            SELECT json_agg(
                json_build_object(
                    'request', json_build_object(
                        'date', s.date,
                        'request', s.request,
                        'response', s.response
                    )
                )
            )
            FROM support_requests s
            WHERE c.id = s.customer_id
        )
    ) INTO customer_data
    FROM customers c
    WHERE c.id = customer_id;

    RETURN customer_data;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_driver_data(driver_id bigint)
RETURNS json AS $$
DECLARE
    driver_data json;
BEGIN
    SELECT json_build_object(
        'driver', json_build_object(
            'name', d.name,
            'surname', d.surname,
            'email', d.email,
            'phone_number', d.phone_number,
            'balance', d.balance,
            'rating', r.rating,
            'trips_count', r.trips_count
        ),
        'trips', (
            SELECT json_agg(
                json_build_object(
                    'trip', json_build_object(
                        'start_time', t.start_time,
                        'end_time', t.end_time,
                        'start_point', t.start_point,
                        'end_point', t.end_point,
                        'price', t.price,
                        'description', t.description,
                        'customer', json_build_object(
                            'name', c.name
                        )
                    )
                )
            )
            FROM trips t
            JOIN customers c ON t.customer_id = c.id
            WHERE d.id = t.driver_id
        ),
        'support_requests', (
            SELECT json_agg(
                json_build_object(
                    'request', json_build_object(
                        'date', s.date,
                        'request', s.request,
                        'response', s.response
                    )
                )
            )
            FROM support_requests s
            WHERE d.id = s.driver_id
        )
    ) INTO driver_data
    FROM drivers d
    JOIN drivers_rating r ON d.id = r.id
    WHERE d.id = driver_id;

    RETURN driver_data;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION insert_log_date()
RETURNS TRIGGER AS $$
BEGIN
    NEW.date := CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER insert_log_date_trigger
BEFORE INSERT ON logs
FOR EACH ROW
EXECUTE FUNCTION insert_log_date();

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
