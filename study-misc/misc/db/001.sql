create database if not exists study default character set = "utf8";

use study;

create table users (
	user_id int unsigned auto_increment not null,
	username varchar(255) not null,
	email varchar(255) not null,
	password char(32) not null,
	creation_date datetime not null,
	creator_id int unsigned not null,
	modification_date datetime null,
	enabled enum ('true', 'false') not null default 'false',
	
	primary key(user_id),
	
	constraint `creator_id_in_users_fk`
	foreign key (creator_id) references users(user_id)
	on update cascade
	on delete restrict,
	
	unique(email),
	unique(username)
)ENGINE=InnoDB;

create table user_roles (
    user_role varchar(20) not null,
	primary key (user_role)
)ENGINE=InnoDB;

create table users_user_roles_link (
	user_id int unsigned not null,
	user_role varchar(20) not null default 'user',

	primary key (user_id, user_role),

	constraint `user_id_in_users_user_roles_link_fk` foreign key (user_id) references users(user_id)
	on update cascade
	on delete cascade,

	constraint `user_role_in_users_user_roles_link_fk` foreign key (user_role) references user_roles(user_role)
	on update cascade
	on delete cascade
)ENGINE=InnoDB;

insert into user_roles values('system');
insert into user_roles values('administrator');
insert into user_roles values('developer');
insert into user_roles values('approved_user');
insert into user_roles values('user');
insert into user_roles values('student');
insert into user_roles values('teacher');
insert into user_roles values('school_employee');


insert into users values (1, 'system', '', '', now(), 1, null, 'false');
insert into users values (2, 'serso', 'serso1988@gmail.com', 'f03df3dff8f672ea78015bb2fe84a419', now(), 1, null, 'true');
insert into users values (3, 'andrew.feoktistov', 'andrew.feoktistov@gmail.com', '25a043732281a2a5324a6ae105508ba3', now(), 1, null, 'true');
insert into users values (4, 'vitken', 'vitken@gmail.com', '8c2642f335f8c78b9ca4b18a884faf54', now(), 1, null, 'true');
insert into users values (5, 'ka.serova', 'ka.serova@gmail.com', 'e1b2c5f57bdaf3a59fcdb5488a116b98', now(), 1, null, 'true');

insert into users_user_roles_link values (1, 'system');
insert into users_user_roles_link values (2, 'administrator');
insert into users_user_roles_link values (2, 'developer');
insert into users_user_roles_link values (3, 'administrator');
insert into users_user_roles_link values (4, 'administrator');
insert into users_user_roles_link values (5, 'administrator');