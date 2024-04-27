CREATE TABLE logs(
    id BIGINT PRIMARY KEY,
    message TEXT,
    date TIMESTAMP
);

CREATE SEQUENCE logs_seq START 1;

ALTER TABLE logs ALTER COLUMN id SET DEFAULT nextval('logs_seq');
