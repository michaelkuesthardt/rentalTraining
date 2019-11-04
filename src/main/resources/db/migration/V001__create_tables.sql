create table author (id uuid not null, first_name varchar(255), last_name varchar(255), primary key (id));
create table book (id uuid not null, isbn varchar(255), title varchar(255), borrower_id uuid, primary key (id));
create table book_author (fk_book uuid not null, fk_author uuid not null, primary key (fk_book, fk_author));
create table borrower (id uuid not null, email varchar(255), first_name varchar(255), last_name varchar(255), primary key (id));
alter table book add constraint FK4nrwjj91jgcf42c3h5i55ar7k foreign key (borrower_id) references borrower;
alter table book_author add constraint FK2xwe4e9xvkh69tcsbktc9qa1f foreign key (fk_author) references author;
alter table book_author add constraint FK1fnhtttsxrymu4vnhpy7jxc9f foreign key (fk_book) references book;
