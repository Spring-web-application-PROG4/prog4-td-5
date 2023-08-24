create extension if not exists "uuid-ossp";

CREATE TABLE employee_cnaps (
id VARCHAR CONSTRAINT employee_cnaps_pk PRIMARY KEY DEFAULT uuid_generate_v4(),
cin VARCHAR,
cnaps VARCHAR,
image VARCHAR,
address VARCHAR,
last_name VARCHAR,
first_name VARCHAR,
personal_email VARCHAR,
professional_email VARCHAR,
registration_number VARCHAR,
birthdate DATE,
entrance_date DATE,
departure_date DATE,
children_number INTEGER,
end_to_end_id  VARCHAR
);
