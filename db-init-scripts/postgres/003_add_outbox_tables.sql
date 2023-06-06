CREATE TABLE people_changed
(
    id          serial PRIMARY KEY,
    data_ref    VARCHAR(50) NOT NULL,
    action      VARCHAR(15),
    time_stamp  VARCHAR(15)
);

CREATE TABLE addresses_changed
(
    id          serial PRIMARY KEY,
    data_ref    VARCHAR(50) NOT NULL,
    action      VARCHAR(15),
    time_stamp  VARCHAR(15)
);
