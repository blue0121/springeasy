create table test_user
(
	id          bigint       primary key,
	name        varchar(50)  not null,
	status      int          not null,
	body        json         not null,
	create_time timestamp(0) not null,
	update_time timestamp(0) not null
);
