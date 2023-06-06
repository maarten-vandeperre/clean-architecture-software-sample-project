CREATE TABLE addresses
(
    id           serial PRIMARY KEY,
    ref          VARCHAR(50) UNIQUE NOT NULL,
    address_line1 VARCHAR(250)       NOT NULL,
    address_line2 VARCHAR(250)       NOT NULL,
    address_line3 VARCHAR(250),
    country_code VARCHAR(10)        NOT NULL
);

INSERT INTO addresses (ref, address_line1, address_line2, address_line3, country_code)
VALUES ('925a7e8e-8b13-429c-80ed-9ae2f788b3dc', 'Leonardo Da Vinci Laan 27', '9000 Gent', null, 'BE');