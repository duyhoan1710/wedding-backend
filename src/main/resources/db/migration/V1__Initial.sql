use wedding;

drop table if exists users;
CREATE TABLE users (
     id int primary key auto_increment,
     name varchar(255),
     email varchar(255) unique,
     password varchar(255),
     role varchar(10),
     created_at DATE,
     updated_at DATE
);

drop table if exists templates;
CREATE TABLE templates (
     id int primary key auto_increment,
     name varchar(255),
     user_id int,
     components JSON,
     created_at DATE,
     updated_at DATE
);

