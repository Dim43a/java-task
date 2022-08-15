-- liquibase formatted sql
-- changeset betpawa:liqubase run verification
-- validchecksum: ANY

CREATE TABLE IF NOT EXISTS users (
   id int(11) AUTO_INCREMENT PRIMARY KEY,
   password VARCHAR(50) NOT NULL,
   email VARCHAR(50) NOT NULL UNIQUE ,
   balance LONG
) ENGINE=InnoDB AUTO_INCREMENT=543 DEFAULT CHARSET=utf8 ;

CREATE TABLE IF NOT EXISTS transactions (
    id int(11) PRIMARY KEY,
    amount LONG,
    reference VARCHAR(255),
    FOREIGN KEY (id) REFERENCES users(id)
)

# -- liquibase formatted sql
# -- changeset author:dima
# -- validchecksum: ANY
