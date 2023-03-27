create database if not exists springcloud_demo;
use springcloud_demo;
create table if not exists user_info
(
    id            bigint auto_increment
    primary key,
    username      varchar(255)     not null,
    password      varchar(255)     not null,
    email         varchar(255)     null,
    sex           bit              null,
    delete_status bit default b'0' not null
    );

