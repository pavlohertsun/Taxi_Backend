CREATE TABLE users(
  id BIGINT PRIMARY KEY,
  name VARCHAR(50),
  surname VARCHAR(50),
  email VARCHAR(50),
  phone_number VARCHAR(20),
  password VARCHAR(150),
  rating INT
);

CREATE TABLE drivers(
  id BIGINT PRIMARY KEY,
  name VARCHAR(50),
  surname VARCHAR(50),
  email VARCHAR(50),
  phone_number VARCHAR(50),
  license VARCHAR(50),
  balance NUMERIC,
  password VARCHAR(150)
);

CREATE TABLE trips(
  id BIGINT PRIMARY KEY,
  start_time TIMESTAMP,
  end_time TIMESTAMP,
  start_point VARCHAR(100),
  end_point VARCHAR(100),
  price NUMERIC,
  status VARCHAR(20) CHECK(status IN ('In progress', 'Completed', 'Cancelled')),
  rate VARCHAR(20) CHECK(rate IN ('Low', 'Normal', 'High')),
  description TEXT,
  user_id BIGINT,
  driver_id BIGINT
);

CREATE TABLE cars(
  id BIGINT PRIMARY KEY,
  license_plate VARCHAR(30),
  document VARCHAR(300),
  type VARCHAR(20) CHECK(type IN('Economy', 'Standart', 'Comfort', 'Business'))
);

CREATE TABLE trips_rating(
  id BIGINT PRIMARY KEY,
  rate INT,
  comment TEXT
);

CREATE TABLE transactions(
  id BIGINT PRIMARY KEY,
  date TIMESTAMP,
  sum NUMERIC,
  status VARCHAR(20) CHECK(status IN ('In progress', 'Completed', 'Cancelled')),
  trip_id BIGINT
);

CREATE TABLE accountings(
  id BIGINT PRIMARY KEY,
  start_date TIMESTAMP,
  end_date TIMESTAMP,
  incomes NUMERIC,
  expenses NUMERIC,
  profit NUMERIC
);

CREATE TABLE incomes(
  id BIGINT PRIMARY KEY,
  date TIMESTAMP,
  sum NUMERIC,
  description TEXT,
  accounting_id BIGINT
);

CREATE TABLE expenses(
  id bigint PRIMARY KEY,
  date TIMESTAMP,
  sum NUMERIC,
  type VARCHAR(200),
  description TEXT,
  accounting_id BIGINT
);

CREATE TABLE drivers_payment(
  id BIGINT PRIMARY KEY,
  sum NUMERIC
);

CREATE TABLE employees(
  id BIGINT PRIMARY KEY,
  name VARCHAR(50),
  surname VARCHAR(50),
  position VARCHAR(50),
  salary NUMERIC
);

CREATE TABLE support_requests(
  id BIGINT PRIMARY KEY,
  date TIMESTAMP,
  description TEXT,
  employee_id BIGINT,
  user_id BIGINT,
  driver_id BIGINT
);

CREATE TABLE drivers_rating(
  id BIGINT PRIMARY KEY,
  trips_count INT,
  comments TEXT
);

CREATE SEQUENCE users_seq START 1;
CREATE SEQUENCE drivers_seq START 1;
CREATE SEQUENCE trips_seq START 1;
CREATE SEQUENCE cars_seq START 1;
CREATE SEQUENCE trips_rating_seq START 1;
CREATE SEQUENCE transactions_seq START 1;
CREATE SEQUENCE accountings_seq START 1;
CREATE SEQUENCE incomes_seq START 1;
CREATE SEQUENCE expenses_seq START 1;
CREATE SEQUENCE drivers_payment_seq START 1;
CREATE SEQUENCE employees_seq START 1;
CREATE SEQUENCE support_requests_seq START 1;
CREATE SEQUENCE drivers_rating_seq START 1;

ALTER TABLE users ALTER COLUMN id SET DEFAULT nextval('users_seq');
ALTER TABLE drivers ALTER COLUMN id SET DEFAULT nextval('drivers_seq');
ALTER TABLE trips ALTER COLUMN id SET DEFAULT nextval('trips_seq');
ALTER TABLE cars ALTER COLUMN id SET DEFAULT nextval('cars_seq');
ALTER TABLE trips_rating ALTER COLUMN id SET DEFAULT nextval('trips_rating_seq');
ALTER TABLE transactions ALTER COLUMN id SET DEFAULT nextval('transactions_seq');
ALTER TABLE accountings ALTER COLUMN id SET DEFAULT nextval('accountings_seq');
ALTER TABLE incomes ALTER COLUMN id SET DEFAULT nextval('incomes_seq');
ALTER TABLE expenses ALTER COLUMN id SET DEFAULT nextval('expenses_seq');
ALTER TABLE drivers_payment ALTER COLUMN id SET DEFAULT nextval('drivers_payment_seq');
ALTER TABLE employees ALTER COLUMN id SET DEFAULT nextval('employees_seq');
ALTER TABLE support_requests ALTER COLUMN id SET DEFAULT nextval('support_requests_seq');
ALTER TABLE drivers_rating ALTER COLUMN id SET DEFAULT nextval('drivers_rating_seq');

ALTER TABLE trips
ADD CONSTRAINT user_id_fk
FOREIGN KEY (user_id)
REFERENCES users(id);

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

ALTER TABLE incomes
ADD CONSTRAINT accountings_id_fk
FOREIGN KEY (accounting_id)
REFERENCES accountings(id);

ALTER TABLE expenses
ADD CONSTRAINT accountings_id_fk
FOREIGN KEY (accounting_id)
REFERENCES accountings(id);

ALTER TABLE support_requests
ADD CONSTRAINT employees_id_fk
FOREIGN KEY (employee_id)
REFERENCES employees(id);

ALTER TABLE support_requests
ADD CONSTRAINT users_id_fk
FOREIGN KEY (user_id)
REFERENCES users(id);

ALTER TABLE support_requests
ADD CONSTRAINT drivers_id_fk
FOREIGN KEY (driver_id)
REFERENCES drivers(id);

ALTER TABLE drivers_rating
ADD CONSTRAINT drivers_id_fk
FOREIGN KEY (id)
REFERENCES drivers(id);