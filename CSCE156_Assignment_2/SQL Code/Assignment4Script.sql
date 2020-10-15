use nradmilo;

drop table if exists Invoice;
drop table if exists Customer;
drop table if exists PersonAddress;
drop table if exists PersonEmail;
drop table if exists Person;
drop table if exists Email;
drop table if exists Address;


Create table Address(
addressId int not null primary key auto_increment,
street varchar(250) not null,
city varchar(50) not null,
State varchar(15),
zip int,
Country varchar(255) not null);

create table Email(
emailId int not null primary key auto_increment,
email varchar(255) not null);

create table Person (
personId int not null primary key auto_increment,
personCode varchar(20) not null,
firstName varchar(30) not null,
lastName varchar(30) not null,
address int not null,
foreign key(address) references Address(addressId),
unique (personId));

create table PersonEmail(
personEmailId int not null primary key auto_increment,
personId int not null,
emailId int not null,
foreign key (personId) references Person(personId),
foreign key (emailId) references Email(emailId));

create table PersonAddress(
personEmailId int not null primary key auto_increment,
personId int not null,
addressId int not null,
foreign key (personId) references Person(personId),
foreign key (addressId) references Address(addressId));

create table Customer(
customerId int not null primary key auto_increment,
customerCode varchar(30) not null,
customerType varchar(1) not null,
customerName varchar(250) not null,
primaryContact int not null,
address int not null,
foreign key (primaryContact) references Person(personId),
foreign key (address) references Address(addressId),
unique (customerId));

create table Invoice(
invoiceId int not null primary key auto_increment,
invoiceCode varchar(30) not null,
owner int not null,
customer int not null,
foreign key (owner) references Person(personId),
foreign key (customer) references Customer(customerId),
unique (invoiceCode));