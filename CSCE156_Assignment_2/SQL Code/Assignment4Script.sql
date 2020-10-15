use nradmilo;

drop table if exists InvoiceProduct;
drop table if exists Invoice;
drop table if exists Product;
drop table if exists Customer;
drop table if exists PersonAddress;
drop table if exists PersonEmail;
drop table if exists Person;
drop table if exists Email;
drop table if exists Address;
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
State int,
zip int,
Country int not null);

create table Email(
emailId int not null primary key auto_increment,
email varchar(255) not null);

create table Person (
personId int not null primary key auto_increment,
personCode varchar(20) not null unique,
firstName varchar(30) not null,
lastName varchar(30) not null,
address int not null,
foreign key(address) references Address(addressId));

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

-- Inserting Products
insert into Product (productCode, productType, productLabel, dailyCost, deposit, cleaningFee)
	values ('qeuriopt', 'R', '1964 Chevy Impala', 89.29, 400, 25.69);
insert into Product (productCode, productType, productLabel, partsCost, hourlyLaborCost)
	values ('nfein2929','F','Light Replacement',233,2);
insert into Product (productCode, productType, productLabel, unitCost)
	values ('aldkjflaksjdfh','C','Hot Doggo',89898.23);
insert into Product (productCode, productType, productLabel, costPerMile)
	values ('utieow39','T','Tow 20 Miles',6.45);
