-- we don't know how to generate root <with-no-name> (class Root) :(
grant super on *.* to 'mysql.session'@localhost;

grant alter, alter routine, create, create routine, create tablespace, create temporary tables, create user, create view, delete, drop, event, execute, file, index, insert, lock tables, process, references, reload, replication client, replication slave, select, show databases, show view, shutdown, super, trigger, update, grant option on *.* to root;

grant alter, alter routine, create, create routine, create tablespace, create temporary tables, create user, create view, delete, drop, event, execute, file, index, insert, lock tables, process, references, reload, replication client, replication slave, select, show databases, show view, shutdown, super, trigger, update, grant option on *.* to root@localhost;

create table day
(
	day_id bigint auto_increment
		primary key,
	date date null
);

create table ingredient
(
	ingredient_id bigint auto_increment
		primary key,
	calories_per100g int not null,
	carbohydrates int not null,
	fat int not null,
	name varchar(255) null,
	protein int not null
);

create table meal
(
	meal_id bigint auto_increment
		primary key,
	img_link varchar(255) null,
	name varchar(255) null
);

create table day_meal
(
	day_id bigint not null,
	meal_id bigint not null,
	constraint FKd2vq33th886l8havkts8kgc1p
		foreign key (day_id) references day (day_id),
	constraint FKl5gkln63c88otawdcheerael9
		foreign key (meal_id) references meal (meal_id)
);

create table ingredient_meal
(
	meal_id bigint not null,
	ingredient_id bigint not null,
	constraint FKb56eijmch4iuegvmbchsh7ysp
		foreign key (ingredient_id) references meal (meal_id),
	constraint FKho0r8uouk3dxn8en37yqtc8n
		foreign key (meal_id) references ingredient (ingredient_id)
);

create table quantity
(
	quantity_id bigint auto_increment
		primary key,
	grams int null,
	meal_meal_id bigint null,
	constraint FK69xy0qiyybdvye694ap3s3pt9
		foreign key (meal_meal_id) references meal (meal_id)
);

create table users
(
	user_id bigint auto_increment
		primary key,
	day_limit int null,
	email varchar(255) null,
	enabled bit null,
	first_name varchar(255) null,
	last_name varchar(255) null,
	password varchar(255) null,
	username varchar(255) not null,
	constraint UK_r43af9ap4edm43mmtq01oddj6
		unique (username)
);

create table authorities
(
	id bigint auto_increment
		primary key,
	authority varchar(255) null,
	username varchar(255) null,
	constraint FKhjuy9y4fd8v5m3klig05ktofg
		foreign key (username) references users (username)
);

create table users_authorities
(
	users_user_id bigint not null,
	authorities_id bigint not null,
	constraint UK_4k9modmi5xv8km1qoyfyrjhjt
		unique (authorities_id),
	constraint FKbdjmc2xd1h0t7cy1thjd4wfy2
		foreign key (users_user_id) references users (user_id),
	constraint FKmfxncv8ke1jjgna64c8kclry5
		foreign key (authorities_id) references authorities (id)
);

create table users_days
(
	users_user_id bigint not null,
	days_day_id bigint not null,
	constraint UK_60gpi2mveyh93d0dmm5v9hsna
		unique (days_day_id),
	constraint FK4gf3sl7l1q7s6cck0mnlypap4
		foreign key (days_day_id) references day (day_id),
	constraint FKgrqcl7owem90vwdqxpyeafeq1
		foreign key (users_user_id) references users (user_id)
);

