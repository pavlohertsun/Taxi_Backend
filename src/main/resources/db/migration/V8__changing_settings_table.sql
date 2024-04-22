DROP TABLE settings;

CREATE TABLE settings(
    id BIGINT PRIMARY KEY,
    rate VARCHAR(20) CHECK(rate IN ('Low', 'Normal', 'High')),
    percent INT
);