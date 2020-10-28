-- Assignment 4 Queries
-- Authors: Caden Kirby and Nick Radmilovich
-- CSCE 156 Section 150
-- 10/17/2020

-- 1 returns all major fields for every person
Select Person.personCode, Person.lastName, Person.firstName, Address.street, Address.city, State.stateName, Country.countryName, Email.email from Person
left join Address on Address.addressId = Person.address
left join State on State.stateId = Address.state
left join Country on Country.countryId = Address.country
left join Email on Email.personId = Person.personId;

-- 2 returns all emails for a specific person
select Email.email from Person
inner join Email on Email.personId = Person.personId
and Person.personId = 1;

-- 3 adds an email for a specific person
insert into Email(email,personId) values ('thisisanEmail@yahoo.com',3);

-- 4
update Email set email = 'thisisanotheremail@email.email' where Email.emailId = 1;

-- 5 Deletes a person, their address, and their emails
delete from Email where Email.emailId = 1;
delete from Email where Email.emailId = 2;
update Invoice set owner = 3 where owner = 1;
delete from Person where Person.personId = 1;
delete from Address where Address.addressId = 6;

-- 6 Inserts a full person record
-- Address 
insert into Address(street, city, state, zip, country) values ('23 Added person house','Moon',1,60613,1);
-- Person 
insert into Person(personCode, firstName, lastName,address) values ('newPerson','Neil','Armstrong',1);
-- Email 1 and 2
insert into Email(email,personId) values ('Email1@gmail.com',4);
insert into Email(email,personId) values ('email2@yahoo.com',4);

-- 7
select p.productCode from Invoice i 
	join InvoiceProduct ip on ip.invoiceId = i.invoiceId
    join Product p on p.productId = ip.productId
    where (i.invoiceId = 2);

-- 8
select Invoice.owner as Person, Product.productCode from Invoice
	inner join InvoiceProduct on InvoiceProduct.invoiceId = Invoice.invoiceId
    inner join Product on Product.productId = InvoiceProduct.productId
    where (Invoice.owner = 2);

-- 9
select invoiceCode from Invoice where (owner = 2);

-- 10
insert into InvoiceProduct(invoiceId, productId, workValue) values (3,3,15);

-- 11
select sum(unitCost) totalUnitCost from Product;

-- 12 
select c.customerName, count(i.customer) from Customer c
	join Invoice i on c.customerId = i.customer
    group by c.customerName, i.customer
    having count(i.customer) > 1;
 
-- 13
select count(*) from Invoice i 
	join InvoiceProduct ip on ip.invoiceId = i.invoiceId
	join Product p on p.productId = ip.productId
    where (p.partsCost is not null);
    
-- 14 
select sum(p.costPerMile * ip.workValue) totalRevenue from Product p
	join InvoiceProduct ip on p.productId = ip.productId;
    
-- 15 
select Invoice.invoiceCode, count(Product.productType)from Invoice
	left join InvoiceProduct on InvoiceProduct.invoiceId = Invoice.invoiceId
    left join Product on Product.productId = InvoiceProduct.productId
    group by Invoice.invoiceCode, Product.productType
    having count(distinct Product.productType) < count(Product.productType);