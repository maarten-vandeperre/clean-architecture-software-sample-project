CREATE TABLE people
(
    id         serial PRIMARY KEY,
    ref        VARCHAR(50) UNIQUE NOT NULL,
    address    INTEGER,
    first_name VARCHAR(50)        NOT NULL,
    last_name  VARCHAR(50)        NOT NULL,
    birth_date VARCHAR(10),
    CONSTRAINT fk_address FOREIGN KEY (address) REFERENCES addresses (id)
);

INSERT INTO people (ref, address, first_name, last_name, birth_date)
VALUES ('13ed6a67-a4c4-4307-85da-2accbcf25aa7',
        (select id from addresses where ref = '925a7e8e-8b13-429c-80ed-9ae2f788b3dc'),
        'Maarten', 'Vandeperre', '17/04/1989');