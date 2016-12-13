DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS events;

CREATE TABLE clients (
  id    INT PRIMARY KEY AUTO_INCREMENT,
  name  VARCHAR(80),
  phone VARCHAR(15)
);

CREATE TABLE employees (
  id     INT PRIMARY KEY AUTO_INCREMENT,
  name   VARCHAR(80),
  phone  VARCHAR(15),
  salary INT
);

CREATE TABLE events (
  id int PRIMARY KEY AUTO_INCREMENT,
  client_id int,
  title VARCHAR(200),
  start TIMESTAMP DEFAULT now(),
  end TIMESTAMP DEFAULT now(),
  sum int DEFAULT 0
);
