
drop table if exists InvoiceProduct;
drop table if exists Invoice;
drop table if exists Product;
drop table if exists Customer;
drop table if exists PersonEmail;
drop table if exists Person;
drop table if exists Address;
drop table if exists Email;
drop table if exists State;
drop table if exists Country;


create table State(
stateId int not null primary key auto_increment,
stateName varchar(40));

create table Country(
countryId int not null primary key auto_increment,
countryName varchar(40));

Create table Address(
addressId int not null primary key auto_increment,
street varchar(250) not null,
city varchar(50) not null,
state int,
zip int,
country int not null,
foreign key (State) references State(stateId),
foreign key (country) references Country(countryId)
);

create table Email(
emailId int not null primary key auto_increment,
email varchar(255) not null);

create table Person (
personId int not null primary key auto_increment,
personCode varchar(20) not null unique,
firstName varchar(30) ,
lastName varchar(30) ,
address int,
foreign key(address) references Address(addressId));

create table PersonEmail(
personEmailId int not null primary key auto_increment,
personId int not null,
emailId int not null,
foreign key (personId) references Person(personId),
foreign key (emailId) references Email(emailId));

create table Customer(
customerId int not null primary key auto_increment,
customerCode varchar(30) not null unique,
customerType varchar(1) not null,
customerName varchar(250) not null,
primaryContact int not null,
address int not null,
foreign key (primaryContact) references Person(personId),
foreign key (address) references Address(addressId));

create table Product (
-- All Products
productId int not null primary key auto_increment,
productCode varchar(255) not null,
productType varchar(1) not null,
productLabel varchar(255) not null,
-- Rental
dailyCost double default null,
deposit double default null,
cleaningFee double default null,
-- Repair
partsCost double default null,
hourlyLaborCost double default null,
-- Towing
costPerMile double default null,
-- Concession
unitCost double default null,
associatedRepair boolean default false);

create table Invoice(
invoiceId int not null primary key auto_increment,
invoiceCode varchar(30) not null unique,
owner int not null,
customer int not null,
foreign key (owner) references Person(personId),
foreign key (customer) references Customer(customerId));

create table InvoiceProduct (
invoiceProductId int primary key not null auto_increment,
workValue double not null,
productCode double not null,

invoiceId int not null,
foreign key (invoiceId) references Invoice(invoiceId),
    
productId int not null,
foreign key (productId) references Product(productId));

-- Test Case 1 Inv001
-- Country 1
Insert into Country(countryName) values ('USA');
-- State 1
Insert into State(stateName) values ('MN');
-- Address 1
insert into Address(street, city, state, zip, country) values ('Moon','Moon',1,60613,1);
-- Person 1
insert into Person(personCode, firstName, lastName,address) values ('jaoiejf','Neil','Armstrong',1);
-- Email 1 and 2
insert into Email(email) values ('neilA@gmail.com');
insert into Email(email) values ('pinkfloyd@yahoo.com');
-- PersonEmail 1 and 2
insert into PersonEmail(personId,emailId) values (1,1);
insert into PersonEmail(personId,emailId) values (1,2);
-- Person 2, empty person code
insert into Person(personCode) values ('klakk');
-- State 2
Insert into State(stateName) values ('EA');
-- Address 2
insert into Address(street, city, state, zip, country) 
values ('690 N Shady St','Moon',2,80902,1);
-- Customer 1
insert into Customer(customerCode, customerType, customerName, primaryContact,address)
values ('lkjjdbbn','B','Dark Side of the Moon',2, 2);
-- Invoice 1
insert into Invoice(invoiceCode,owner,customer) values ('INV001',1,1);

-- Test Case 2 INV002
-- State 3
insert into State(stateName) values ('NY');
-- Address 3
insert into Address(street, city, state, zip, country)
values ('2 Broadway St','New York',3,68117,1);
-- Person 3
insert into Person(personCode, firstName, lastName, address)
values ('nvnoee', 'Kyrie', 'Irving',3);
-- Customer 2
insert into Customer(customerCode, customerType, customerName, primaryContact, address)
values ('99999ndkf90','B','Tesla',3,3);
-- Invoice 2
insert into Invoice(invoiceCode, owner, customer) 
values ('INV002', 3, 2);

-- Test Case 3 INV003
-- State 4
insert into State(stateName) values ('CO');
-- Address 4
insert into Address(street, city, state, country)
values ('23450 1st St','South Park',4,1);
-- Person 4
insert into Person(personCode, firstName, lastName, address)
values ('oiasudem','Eric','Cartman',4);
-- Person 5
insert into Person(personCode) values ('zyx321');
-- Address 5
insert into Address(street, city, state, country)
values ('123 1st St','South Park',4,1);
-- Customer 3
insert into Customer(customerCode, customerType, customerName, primaryContact, address)
values('uebgmk','P','Big Al',5,5);
-- Invoice 3
insert into Invoice(invoiceCode, owner, customer)
values ('INV003',4,3);

