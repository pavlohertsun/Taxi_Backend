ALTER TABLE support_requests
DROP CONSTRAINT employees_id_fk;

ALTER TABLE support_requests
DROP COLUMN employee_id;

ALTER TABLE support_requests
DROP COLUMN description;

ALTER TABLE support_requests
ADD COLUMN request VARCHAR(500);

ALTER TABLE support_requests
ADD COLUMN response VARCHAR(500);

ALTER TABLE support_requests
ADD COLUMN status VARCHAR(30) CHECK (status in ('Processing', 'Processed'));