use study;

create table genders (
	gender varchar(6) not null,
	primary key (gender)
)ENGINE=InnoDB;

insert into genders values ('male');
insert into genders values ('female');

create table partner_types (
	partner_type varchar(20) not null,
	primary key (partner_type)
)ENGINE=InnoDB;

insert into partner_types values ('natural_person');
insert into partner_types values ('legal_person');

create table partners (
	partner_id int unsigned auto_increment not null,
	first_name varchar(255) null,
	last_name varchar(255) null,
	company_name varchar(255) null,
	birthdate date null,
	incorporation_date date null,
	gender varchar(6) null,
	partner_type varchar(20) not null,

	creation_date datetime not null,
	creator_id int unsigned not null,
	modification_date datetime null,
	
	primary key(partner_id),
	
	constraint `gender_in_partners_fk`
	foreign key (gender) references genders(gender)
	on update cascade
	on delete restrict,

	constraint `partner_type_in_partners_fk`
	foreign key (partner_type) references partner_types(partner_type)
	on update cascade
	on delete restrict,

	constraint `creator_id_in_partners_fk`
	foreign key (creator_id) references users(user_id)
	on update cascade
	on delete restrict
)ENGINE=InnoDB;

create table countries (
	country varchar (100) not null,
	primary key (country)
)ENGINE=InnoDB;

create table partner_roles(
	partner_role varchar (100) not null,
	primary key (partner_role)
)ENGINE=InnoDB;

create table partners_partner_roles_link(
	partner_id int unsigned auto_increment not null,
	partner_role varchar (100) not null,

	constraint `partner_id_in_partners_partner_roles_link_fk`
	foreign key (partner_id) references partners (partner_id)
	on update cascade
	on delete cascade,

	constraint `partner_role_in_partners_partner_roles_link_fk`
	foreign key (partner_role) references partner_roles (partner_role)
	on update cascade
	on delete cascade,

	primary key (partner_id, partner_role)
)ENGINE=InnoDB;

insert into partner_roles values ('student');
insert into partner_roles values ('teacher');
insert into partner_roles values ('school_employee');
insert into partner_roles values ('school');
insert into partner_roles values ('institute');
insert into partner_roles values ('university');

create table users_partners_link (
	user_id int unsigned not null,
	partner_id int unsigned not null,

	constraint `user_id_in_users_partners_link_fk`
	foreign key (user_id) references users(user_id)
	on update cascade
	on delete cascade,

	constraint `partner_id_users_partners_link_fk`
	foreign key (partner_id) references partners(partner_id)
	on update cascade
	on delete cascade,

	primary key (user_id, partner_id)
)ENGINE=InnoDB;

create table school_details (
	school_id int unsigned not null,

	school_number int unsigned null,

	constraint `school_id_in_school_details_fk`
	foreign key (school_id) references partners(partner_id)
	on update cascade
	on delete cascade
)ENGINE=InnoDB;
