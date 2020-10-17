-- Assignment 4 Queries
-- Authors: Caden Kirby and Nick Radmilovich
-- CSCE 156 Section 150
-- 10/17/2020

-- 1
Select Person.personCode, Person.lastName, Person.firstName, Address.street, Address.city, State.stateName, Country.countryName, Email.email from Person
left join Address on Address.addressId = Person.address
left join State on State.stateId = Address.state
left join Country on Country.countryId = Address.country
left join PersonEmail on Person.personId = PersonEmail.personId
left join Email on PersonEmail.emailId = Email.emailId;
-- 2
select Email.email from Person
inner join PersonEmail on Person.personId = PersonEmail.personId
inner join Email on PersonEmail.emailId = Email.emailId
and Person.personId = 1;
-- 3
insert into Email(email) values ('thisisanEmail@yahoo.com');
insert into PersonEmail(personId, emailId) values (3,3);
-- 4
update Email set email = 'thisisanotheremail@email.email' where Email.emailId = 1;
-- 5
-- Delete email associations if the exist
delete from PersonEmail where PersonEmail.personId = 4;
-- Update owner on record 
update Invoice set owner = 3 where owner = 4;
-- delete old person info
delete from Person where Person.personId = 4;
-- 6

-- 7
select p.productCode from Invoice i 
	join InvoiceProduct ip on ip.invoiceId = i.invoiceId
    join Product p on p.productId = ip.productId
    where (i.invoiceId = 2);

-- 8
select * from Person where (personId = 1);

-- 9
select invoiceCode from Invoice where (customer = 1);

-- 10
insert into InvoiceProduct(invoiceId, productId, workValue) values (3,3,15);

-- 11
select sum(unitCost) totalUnitCost from Product;

-- 12 Not finished
select c.customerName, count(i.customer) from Customer c
	join Invoice i on c.customerId = i.customer
    having count(i.customer) > 1;
 
-- 13
select count(*) from Invoice i 
	join InvoiceProduct ip on ip.invoiceId = i.invoiceId
	join Product p on p.productId = ip.productId
    where (p.partsCost is not null);
    
-- 14 
select sum(p.costPerMile * ip.workValue) totalRevenue from Product p
	join InvoiceProduct ip on p.productId = ip.productId;
    
-- 15 not finished
select Invoice.invoiceCode, count(Product.productType)from Invoice
	left join InvoiceProduct on InvoiceProduct.invoiceId = Invoice.invoiceId
    left join Product on Product.productId = InvoiceProduct.productId
    group by Invoice.invoiceCode, Product.productType
    having count(Product.productType) > 1;