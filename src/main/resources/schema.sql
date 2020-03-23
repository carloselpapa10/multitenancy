-- create tables into public database
create table if not exists DataSourceConfig (
       id bigint not null,
       driverClassName varchar(255),
       url varchar(255),
       name varchar(255),
       username varchar(255),
       password varchar(255),
       initialize bit not null,
       primary key (id)
);
create table if not exists hibernate_sequence (next_val bigint);
insert into hibernate_sequence values (1);

-- register each tenant manually in the public database. So, each row, each tenant
-- note: you have to re start the application to apply the changes.
-- INSERT INTO public.DataSourceConfig VALUES (1, 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3309/test1?ApplicationName=MultiTenant', 'test1', 'root', 'rootpassword', true);
-- INSERT INTO public.DataSourceConfig VALUES (2, 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3309/test2?ApplicationName=MultiTenant', 'test2', 'root', 'rootpassword', true);

-- tenant1 = test1
create database if not exists test1;
create table if not exists test1.city(id bigint, name varchar(200));
create table if not exists test1.hibernate_sequence(next_val bigint);
insert into test1.hibernate_sequence values (1);

-- tenant2 = test2
create schema if not exists test2;
create table if not exists test2.city(id bigint, name varchar(200));
create table if not exists test2.hibernate_sequence(next_val bigint);
insert into test2.hibernate_sequence values (1);