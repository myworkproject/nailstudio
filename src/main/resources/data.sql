DELETE FROM clients;
DELETE FROM employees;

INSERT INTO clients (name, phone)
VALUES ('Саша Дресь', '+380993366889'), ('Анна Дресь', '+380665544123'),
  ('Даша Жадан', '+380954488963'), ('Юля Фамилия', '+380504412369');

INSERT INTO employees (name, phone, salary)
VALUES ('Саша Дресь', '+380993366889', 20), ('Анна Дресь', '+380665544123', 50),
  ('Даша Жадан', '+380954488963', 100), ('Юля Фамилия', '+380504412369', 40);
