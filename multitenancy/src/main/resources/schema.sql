create schema if not exists test1;
create schema if not exists test2;
create table if not exists test1.city(id bigint, name varchar(200));
create table if not exists test2.city(id bigint, name varchar(200));
create table if not exists test1.hibernate_sequence(next_val bigint);
insert into test1.hibernate_sequence values (1);
create table if not exists test2.hibernate_sequence(next_val bigint);
insert into test2.hibernate_sequence values (1);