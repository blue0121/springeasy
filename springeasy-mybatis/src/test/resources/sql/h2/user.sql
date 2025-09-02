create table test_user
(
	id          int8         primary key,
	name        varchar(50)  not null,
	status      int4         not null,
	body        json         not null,
	create_time timestamp(0) not null,
	update_time timestamp(0) not null
);
