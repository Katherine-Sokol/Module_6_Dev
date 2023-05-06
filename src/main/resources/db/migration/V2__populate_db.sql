INSERT INTO worker
       (name, birthday, level, salary)
VALUES
	   ('Meredith Grey', '1987-02-17', 'Junior', 1200),
       ('Miranda Bailey', '1970-04-25', 'Middle', 4500),
       ('Richard Webber', '1958-12-22', 'Senior', 10000),
       ('Alex Karev', '1991-06-16', 'Junior', 1000),
       ('Owen Hunt', '1979-08-01', 'Middle', 5000),
       ('Jackson Avery', '1995-04-08','Junior', 1800),
       ('Derek Shepherd', '1985-02-28', 'Senior', 10000),
       ('Jo Wilson', '1995-04-08', 'Trainee', 800),
       ('April Kepner', '1991-11-06', 'Trainee', 900),
       ('Ben Warren', '1989-01-26', 'Trainee', 800)
;

INSERT INTO client
       (name)
VALUES
       ('Ellen Pompeo'),
       ('Justin Chambers'),
       ('Jesse Williams'),
       ('Patrick Dempsey'),
       ('Sandra Oh');

ALTER TABLE project
ADD CONSTRAINT check_project_term CHECK (
		TIMESTAMPDIFF (MONTH, start_date, finish_date) >= 1
		AND TIMESTAMPDIFF (MONTH, start_date, finish_date) <= 100
);

INSERT INTO project
       (client_id, start_date, finish_date)
VALUES
       (1, '2023-04-01', '2023-06-30'),
       (1, '2023-06-10', '2023-10-15'),
       (2, '2023-05-20', '2023-11-01'),
       (2, '2023-07-01', '2024-03-31'),
       (2, '2023-04-25', '2023-06-25'),
       (2, '2023-06-15', '2023-09-15'),
       (3, '2023-05-01', '2023-10-15'),
       (4, '2023-04-20', '2023-05-30'),
       (4, '2023-05-01', '2023-09-01'),
       (5, '2023-06-20', '2023-12-31');

INSERT INTO project_worker
       (project_id, worker_id)
SELECT project.id, worker.id
FROM project
JOIN worker ON RAND() < 0.5
GROUP BY project.id, worker.id
HAVING COUNT(*) <= 5;