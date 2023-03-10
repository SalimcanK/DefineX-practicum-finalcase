DROP TABLE IF EXISTS loan;

DROP TABLE IF EXISTS customer;

CREATE TABLE IF NOT EXISTS customer
(
    tckn           BIGINT PRIMARY KEY NOT NULL,
    name           CHARACTER VARYING(255),
    last_name      CHARACTER VARYING(255),
	date_of_birth  DATE,
	phone_number   CHARACTER(10),
    monthly_salary DOUBLE PRECISION,
	deposit        DOUBLE PRECISION DEFAULT 0
);

CREATE TABLE IF NOT EXISTS loan
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    approval_date   TIMESTAMP WITHOUT TIME ZONE,
    approval_status BOOLEAN NOT NULL,
    loan_amount     DOUBLE PRECISION,
    customer_tckn   BIGINT,
    FOREIGN KEY (customer_tckn) REFERENCES customer (tckn)
);

INSERT INTO customer (tckn, name, last_name, date_of_birth, phone_number, monthly_salary, deposit)
VALUES (20000000671, 'Filiz', 'Karasu', '1995-09-28', '6204345395', 9540, 0),
       (20000000833, 'Hakan', 'Kaya', '1989-05-12', '6141373265', 12500, 4200),
       (20000000129, 'Ayca', 'Akman', '1992-10-05', '6903032403', 4500, 0),
       (20000000055, 'Ferhat', 'Avcı', '1987-11-12', '6542571746', 8500, 0),
       (20000001009, 'Eylül', 'Demir', '1997-11-10', '6732704593', 18850, 2500),
       (20000000648, 'Nesrin', 'Gücer', '2002-07-18', '6613159970', 4700, 0);

INSERT INTO loan (approval_date, approval_status, loan_amount, customer_tckn)
VALUES ('2021-03-08 21:38:39.161', 'true', 20000, 20000000671),
       ('2022-01-16 22:33:52.164', 'false', 0, 20000000671),
       ('2022-02-12 20:52:08.164', 'true', 10000, 20000000671);

INSERT INTO loan (approval_date, approval_status, loan_amount, customer_tckn)
VALUES ('2022-12-20 16:24:16.167', 'false', 0, 20000000833),
       ('2021-06-09 12:32:03.162', 'true', 20000, 20000000833),
       ('2023-01-29 20:37:00.167', 'true', 24520, 20000000833);

INSERT INTO loan (approval_date, approval_status, loan_amount, customer_tckn)
VALUES ('2022-03-23 18:45:09.164', 'false', 0, 20000000129),
       ('2021-07-20 00:16:36.162', 'false', 0, 20000000129);

INSERT INTO loan (approval_date, approval_status, loan_amount, customer_tckn)
VALUES ('2022-12-30 23:31:11.167', 'true', 10000, 20000000055);

INSERT INTO loan (approval_date, approval_status, loan_amount, customer_tckn)
VALUES ('2022-11-30 06:22:01.166', 'true', 15900, 20000001009),
       ('2021-10-13 10:44:42.163', 'false', 0, 20000001009),
       ('2021-05-27 09:29:03.162', 'true', 15100, 20000001009);

INSERT INTO loan (approval_date, approval_status, loan_amount, customer_tckn)
VALUES ('2021-10-13 10:44:42.163', 'false', 0, 20000000648),
       ('2022-11-03 15:26:32.166', 'false', 0, 20000000648);