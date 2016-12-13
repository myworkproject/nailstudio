DELETE FROM clients;
DELETE FROM employees;
DELETE FROM events;

INSERT INTO clients (name, phone)
VALUES ('John Smith', '+380993366889'), ('Anna Smith', '+380665544123'),
  ('Daria Smith', '+380954488963'), ('Julia Smith', '+380504412369');

INSERT INTO employees (name, phone, salary)
VALUES ('Daria Smith', '+380993366889', 20), ('Julia Smith', '+380665544123', 50),
  ('John Smith', '+380954488963', 100), ('Anna Smith', '+380504412369', 40);

INSERT INTO events (employee_id, client_id, title, start, end)
VALUES (1, 2, 'Some title', '2016-12-12T13:00:00', '2016-12-12T14:00:00'),
  (2, 2, 'Some title', '2016-12-12T15:00:00', '2016-12-12T16:00:00'),
  (3, 3, 'Some title', '2016-12-13T13:00:00', '2016-12-13T14:00:00'),
  (4, 4, 'Some title', '2016-12-13T15:00:00', '2016-12-13T16:00:00');
