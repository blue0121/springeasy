create table if not exists test_user
(
	id          int8 primary key,
	name        varchar(50)  not null,
	create_time timestamp(0) not null,
	update_time timestamp(0) not null
);
