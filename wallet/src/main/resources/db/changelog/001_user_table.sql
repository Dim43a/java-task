-- liquibase formatted sql
-- changeset betpawa:liqubase run verification
-- validchecksum: ANY

CREATE TABLE IF NOT EXISTS users (
   id int(11) AUTO_INCREMENT PRIMARY KEY,
   password VARCHAR(50) NOT NULL,
   email VARCHAR(50) NOT NULL UNIQUE ,
   balance DECIMAL
) ENGINE=InnoDB AUTO_INCREMENT=543 DEFAULT CHARSET=utf8 ;

CREATE TABLE operations(
   operation_id INT(11) PRIMARY KEY NOT NULL,
   amount DECIMAL,
   operation_date DATETIME,
   operation_type VARCHAR(50),
   userId INT(11)
#    CONSTRAINT fk_id FOREIGN KEY id
#    REFERENCES users(id)
);