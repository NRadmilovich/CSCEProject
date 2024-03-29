-- Assignment 4 Tables/Data
-- Authors: Caden Kirby and Nick Radmilovich
-- CSCE 156 Section 150
-- 10/17/2020

drop table if exists InvoiceProduct;
drop table if exists Invoice;
drop table if exists Product;
drop table if exists Customer;
drop table if exists Email;
drop table if exists Person;
drop table if exists Address;
drop table if exists State;
drop table if exists Country;


create table State(
stateId int not null primary key auto_increment,
stateName varchar(40) unique);

create table Country(
countryId int not null primary key auto_increment,
countryName varchar(40) unique);

CREATE TABLE Address (
    addressId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    street VARCHAR(250) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state INT,
    zip VARCHAR(10),
    country INT NOT NULL,
    FOREIGN KEY (State)
        REFERENCES State (stateId),
    FOREIGN KEY (country)
        REFERENCES Country (countryId)
);

create table Person (
personId int not null primary key auto_increment,
personCode varchar(20) not null unique,
firstName varchar(30) not null,
lastName varchar(30) not null,
address int not null,
foreign key(address) references Address(addressId));

create table Email(
emailId int not null primary key auto_increment,
personId int not null,
email varchar(255) not null,
foreign key (personId) references Person(personId));

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

unique (productCode));

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
associatedRepair varchar(30) default null,

invoiceId int not null,
foreign key (invoiceId) references Invoice(invoiceId),
    
productId int not null,
foreign key (productId) references Product(productId));


-- Inserting Products
insert into Product (productCode, productType, productLabel, dailyCost, deposit, cleaningFee)
	values ('qeuriopt', 'R', '1964 Chevy Impala', 89.29, 400, 25.69);
insert into Product (productCode, productType, productLabel, partsCost, hourlyLaborCost)
	values ('nfein2929','F','Light Replacement',233,2);
insert into Product (productCode, productType, productLabel, unitCost)
	values ('aldkjflaksjdfh','C','Hot Doggo',89898.23);
insert into Product (productCode, productType, productLabel, costPerMile)
	values ('utieow39','T','Tow 20 Miles',6.45);

-- Test Case 1 Inv001
-- Country 1
Insert into Country(countryName) values ('USA');
-- State 1
Insert into State(stateName) values ('MN');
-- Address 1
insert into Address(street, city, state, zip, country) values ('Moon','Moon',1,'60613',1);
-- Person 1
insert into Person(personCode, firstName, lastName,address) values ('jaoiejf','Neil','Armstrong',1);
-- Email 1 and 2
insert into Email(email,personId) values ('neilA@gmail.com',1);
insert into Email(email,personId) values ('pinkfloyd@yahoo.com',1);
-- State 2
Insert into State(stateName) values ('EA');
-- Address 2
insert into Address(street, city, state, zip, country) 
values ('690 N Shady St','Moon',2,'80902',1);
-- Customer 1
insert into Customer(customerCode, customerType, customerName, primaryContact,address)
values ('lkjjdbbn','B','Dark Side of the Moon',1, 2);
-- Invoice 1
insert into Invoice(invoiceCode,owner,customer) values ('INV001',1,1);
-- InvoiceProduct 1
insert into InvoiceProduct(invoiceId, productId, workValue) values (1,1,5.0);
-- InvoiceProduct 2
insert into InvoiceProduct(invoiceId, productId, workValue) values (1,2,12.0);
-- InvoiceProduct 3
insert into InvoiceProduct(invoiceId, productId, workValue, associatedRepair) values (1,3,2.0, 'nfein2929');
-- InvoiceProduct 4
insert into InvoiceProduct(invoiceId, productId, workValue) values (1,4,22.0);

-- Test Case 2 INV002
-- State 3
insert into State(stateName) values ('NY');
-- Address 3
insert into Address(street, city, state, zip, country)
values ('2 Broadway St','New York',3,'68117',1);
-- Person 2
insert into Person(personCode, firstName, lastName, address)
values ('nvnoee', 'Kyrie', 'Irving',3);
-- Customer 2
insert into Customer(customerCode, customerType, customerName, primaryContact, address)
values ('99999ndkf90','B','Tesla',2,3);
-- Invoice 2
insert into Invoice(invoiceCode, owner, customer) 
values ('INV002', 2, 2);
-- InvoiceProduct 5
insert into InvoiceProduct(invoiceId, productId, workValue) values (2,4,12.0);
-- Invoice Product 6
insert into InvoiceProduct(invoiceId, productId, workValue) values (2,3,3.0);


-- Test Case 3 INV003
-- State 4
insert into State(stateName) values ('CO');
-- Address 4
insert into Address(street, city, state, country)
values ('23450 1st St','South Park',4,1);
-- Person 3
insert into Person(personCode, firstName, lastName, address)
values ('oiasudem','Eric','Cartman',4);
-- Address 5
insert into Address(street, city, state, country)
values ('123 1st St','South Park',4,1);
-- Customer 3
insert into Customer(customerCode, customerType, customerName, primaryContact, address)
values('uebgmk','P','Big Al',3,5);
-- Invoice 3
insert into Invoice(invoiceCode, owner, customer)
values ('INV003',3,3);
-- InvoiceProduct 7
insert into InvoiceProduct(invoiceId, productId, workValue) values (3,3,20);
