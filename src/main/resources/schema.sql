DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS clients;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 1;

CREATE TABLE clients (
  id    INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name  VARCHAR,
  phone VARCHAR
);

CREATE TABLE employees (
  id      INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name    VARCHAR,
  email   VARCHAR,
  phone   VARCHAR,
  salary  INTEGER             DEFAULT 0,
  percent INTEGER             DEFAULT 100,
  admin   BOOLEAN             DEFAULT FALSE
);

CREATE TABLE events (
  id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  client_id   INTEGER REFERENCES clients (id)
  ON UPDATE NO ACTION
  ON DELETE SET NULL,
  employee_id INTEGER REFERENCES employees (id)
  ON UPDATE NO ACTION
  ON DELETE SET NULL,
  title       VARCHAR,
  start       TIMESTAMP           DEFAULT now(),
  "end"       TIMESTAMP           DEFAULT now(),
  sum         INTEGER             DEFAULT 0
);

CREATE INDEX clients_idx
  ON clients (id);
CREATE INDEX employees_idx
  ON employees (id);
CREATE INDEX events_idx
  ON events (id);

CREATE VIEW employee_salary AS
  SELECT
    emp.name,
    extract(YEAR FROM evn.start)                      AS year,
    extract(MONTH FROM evn.start)                     AS month,
    sum(evn.sum)                                      AS total,
    ((sum(evn.sum) * emp.percent) / 100) + emp.salary AS salary
  FROM events evn
    LEFT JOIN employees emp ON evn.employee_id = emp.id
  GROUP BY emp.name, year, month, emp.percent, emp.salary, admin
  HAVING admin = FALSE;

DELETE FROM clients;
DELETE FROM employees;
DELETE FROM events;
ALTER SEQUENCE global_seq RESTART WITH 1;

INSERT INTO clients (name, phone)
VALUES ('John Smith', '+380993366889'), ('Anna Smith', '+380665544123'),
  ('Daria Smith', '+380954488963'), ('Julia Smith', '+380504412369');

INSERT INTO employees (name, phone, percent)
VALUES ('Daria Smith', '+380993366889', 20), ('Julia Smith', '+380665544123', 50),
  ('John Smith', '+380954488963', 100), ('Anna Smith', '+380504412369', 40);

INSERT INTO events (employee_id, client_id, title, start, "end")
VALUES (5, 2, 'Some title', '2016-12-12T13:00:00', '2016-12-12T14:00:00'),
  (6, 2, 'Some title', '2016-12-12T15:00:00', '2016-12-12T16:00:00'),
  (7, 3, 'Some title', '2016-12-13T13:00:00', '2016-12-13T14:00:00'),
  (8, 4, 'Some title', '2016-12-13T15:00:00', '2016-12-13T16:00:00');