DROP TABLE IF EXISTS customer;

CREATE TABLE customer(
	id INTEGER,
	name varchar(255),
	gender char(2),
    age INTEGER,
	birthday date,
	salary decimal(36,2),
	is_active boolean,
	description text,
	created_at timestamp,
	updated_at timestamp,
	PRIMARY KEY(id)
);