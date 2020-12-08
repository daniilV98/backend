create table products (id bigserial primary key, title varchar(255), price int);
insert into products (title, price)
values
('Milk', 90),
('Bread', 30),
('Potato', 100),
('Cheese', 350),
('Fish', 400);