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
  GROUP BY emp.name, year, month, emp.percent, emp.salary;
