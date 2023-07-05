CREATE TABLE quarkus-social;

CREATE TABLE TB_USER(
    id bigserial not null primary key,
	name varchar(100) not null,
	age integer not null,
	email varchar(100) not null
)
CREATE TABLE TB_POST(
    id bigserial not null primary key,
	text varchar(400) not null,
	dateTime timestamp not null,
	user_id bigint not null references TB_USER(id)
)
CREATE TABLE TB_FOLLOWER(
    follow_id bigserial not null primary key,
	user_id bigint not null references TB_USER(id),
	follower_id bigint not null references TB_USER(id)
)