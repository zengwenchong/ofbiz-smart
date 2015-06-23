DROP TABLE IF EXISTS customer;

CREATE TABLE customer(
	id bigint(20) NOT NULL auto_increment PRIMARY KEY,
	name varchar(255) NOT NULL,
	gender char(2) DEFAULT 'M',
    age int(10),
	birthday date,
	salary decimal(36,2),
	is_active boolean,
	description text,
	created_at timestamp,
	updated_at timestamp NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;