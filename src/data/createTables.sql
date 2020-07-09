create table citystate(csid int primary key, city varchar(30) not null, state varchar(30) not null, updated timestamp not null default current_timestamp);
create table contact(contactid int primary key, firstname varchar(20) not null, lastname varchar(30) not null, phone int not null, address1 varchar(50), address2 varchar(50), cityid int references citystate(csid), updated timestamp not null default current_timestamp);
create table type(typeid int primary key, name varchar(30) not null unique, updated timestamp not null default current_timestamp);
create table booking(bookid int primary key, contactid int references contact(contactid), title varchar(50) not null, description varchar(255), typeid int references type(typeid), start timestamp not null unique, end timestamp not null unique, updated timestamp not null default current_timestamp);
create sequence seq_csid start with 1;
create sequence seq_contactid start with 1;
create sequence seq_typeid start with 1;
create sequence seq_bookid start with 1;