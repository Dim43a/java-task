-- liquibase formatted sql
-- changeset betpawa:liqubase run verification

create table verif_table (
 `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,
 name VARCHAR(55)
) ENGINE=InnoDB AUTO_INCREMENT=543 DEFAULT CHARSET=utf8 ;