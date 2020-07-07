drop schema if exists how_to_get_app;

create schema how_to_get_app;

DROP USER IF EXISTS 'how-to-get-connection'@'localhost';
CREATE USER 'how-to-get-connection'@'localhost' IDENTIFIED BY 'ruslan_db_51694931';
GRANT ALL PRIVILEGES ON how_to_get_app.* TO 'root'@'how-to-get-connection'@'localhost';

use how_to_get_app;

set foreign_key_checks=0;

drop table if exists users;

create table users(
id int AUTO_INCREMENT,
pass VARCHAR(60),
first_name VARCHAR(255),
last_name VARCHAR(255),
email VARCHAR(255),
age int,
roles VARCHAR(255),
primary key (id));

drop table if exists flights;

create table flights(
id int AUTO_INCREMENT,
ufn VARCHAR(10),
company VARCHAR(255),
price DOUBLE,
departure_from varchar(255),
arrival_to VARCHAR(255),
departure_date VARCHAR(30),
arrival_date VARCHAR(30),
tickets_available int,
PRIMARY KEY (id));

drop table if exists buses;

create table buses(
id int AUTO_INCREMENT,
ufn VARCHAR(10),
company VARCHAR(255),
price DOUBLE,
departure_from varchar(255),
arrival_to VARCHAR(255),
departure_date VARCHAR(30),
arrival_date VARCHAR(30),
tickets_available int,
PRIMARY KEY (id)) ;

drop table if exists commercial_accounts ;
 
create table commercial_accounts (
id int PRIMARY KEY auto_increment,
company_name VARCHAR(255),
telephone VARCHAR(60),
address varchar(255),
transport_types VARCHAR(60),
user_profile_id int,
foreign key (user_profile_id) references user_profiles(id) ON DELETE NO ACTION);


drop table if exists orders_bus;

create table orders_bus(
id bigint PRIMARY KEY auto_increment,
bus int,
tickets_number int,
foreign key (bus) references buses(id)
);

drop table if exists user_profiles;

create table user_profiles(
id int PRIMARY KEY auto_increment,
user_id int,
foreign key (user_id) REFERENCES users(id)
);

drop table if exists orders_flight;

create table orders_flight(
id bigint PRIMARY KEY auto_increment,
flight int,
tickets_number int,
foreign key (flight) references flights(id)
);

drop table if exists userprofile_orders_bus;

create table userprofile_orders_bus(
profile_id int,
order_bus_id bigint,
primary key (profile_id, order_bus_id),
foreign key (profile_id) references user_profiles(id),
foreign key (order_bus_id) references orders_bus(id)
);

drop table if exists userprofile_orders_flight;

create table userprofile_orders_flight(
profile_id int,
order_flight_id bigint,
primary key (profile_id, order_flight_id),
foreign key (profile_id) references user_profiles(id),
foreign key (order_flight_id) references orders_flight(id)
);

set foreign_key_checks=1;