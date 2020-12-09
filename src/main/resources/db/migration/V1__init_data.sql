create table authors (id bigserial primary key, name varchar(255), booksCount int);
insert into authors (name)
values
('Anna Todd'),
('Jojo Moyes'),
('Cecelia Ahern'),
('Alexander Pushkin');

create table books (id bigserial primary key, title varchar(255), author_id bigint references authors (id));
insert into books (title, author_id)
values
('After', 1),
('After quarrel', 1),
('Before', 1),
('Before you', 2),
('After you', 2),
('I love you!', 3),
('I do not believe. I do not hope', 3),
('Stigma', 3),
('Eugene Onegin', 4);