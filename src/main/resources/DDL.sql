CREATE TABLE if not exists public.DATASOURCECONFIG (
	id bigint PRIMARY KEY,
	driverclassname VARCHAR(255),
	url VARCHAR(255),
	name VARCHAR(255),
	username VARCHAR(255),
	password VARCHAR(255),
	initialize BOOLEAN
);
##### Schema Creation ############
create schema if not exists test1;
create schema if not exists test2;
create table test1.city(id bigint, name varchar(200));
create table test2.city(id bigint, name varchar(200));

CREATE SEQUENCE "test1".hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE "test2".hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
INSERT INTO DATASOURCECONFIG VALUES (1, 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:8080/tenant?currentSchema=test1&ApplicationName=MultiTenant', 'test1', 'mysqluser', 'mysqlpw', true);
INSERT INTO DATASOURCECONFIG VALUES (2, 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:8080/tenant?currentSchema=test2&ApplicationName=MultiTenant', 'test2', 'mysqluser', 'mysqlpw', true);