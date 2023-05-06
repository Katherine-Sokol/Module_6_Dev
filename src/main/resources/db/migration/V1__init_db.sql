CREATE TABLE worker (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name TEXT NOT NULL,
       birthday DATE,
       level ENUM('Trainee', 'Junior', 'Middle', 'Senior') NOT NULL,
       salary int,
       CONSTRAINT worker_check_birthday CHECK(birthday > '1900-01-01'),
	   CONSTRAINT worker_check_name CHECK(CHAR_LENGTH(name) >= 2 AND CHAR_LENGTH(name) <= 1000),
	   CONSTRAINT worker_check_salary CHECK(salary >= 100 AND salary <= 100000)
);

CREATE TABLE client (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name TEXT NOT NULL,
       CONSTRAINT client_check_name CHECK (CHAR_LENGTH (name) >= 2 AND CHAR_LENGTH (name) <= 1000)
);

CREATE TABLE project (
       id INT AUTO_INCREMENT PRIMARY KEY,
       client_id INT,
       start_date DATE,
       finish_date DATE,
       FOREIGN KEY (client_id) REFERENCES client (id),
       CONSTRAINT project_check_date CHECK (finish_date >= start_date)
);

CREATE TABLE project_worker (
       project_id INT,
       worker_id INT,
       FOREIGN KEY (project_id) REFERENCES project (id),
       FOREIGN KEY (worker_id) REFERENCES worker (id),
       CONSTRAINT pk_project_worker PRIMARY KEY (project_id, worker_id)
);