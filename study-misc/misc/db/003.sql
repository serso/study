use study;

create table address_types (
    address_type varchar(20) not null,
	primary key (address_type)
)ENGINE=InnoDB;

insert into address_types values ('residence');
insert into address_types values ('office');

create table addresses (
	partner_id int unsigned not null,
    address_type varchar(20) not null,
    country varchar(255) null,
    city varchar(255) null,
    street varchar(255) null,
    house varchar(255) null,
    apartment varchar(255) null,
    phone_number varchar(255) null,
    email varchar(255) null,
    postal_code varchar(255) null,
    is_main varchar(5) not null default 'false',

	constraint `address_type_in_addresses_fk`
	foreign key (address_type) references address_types(address_type)
	on update cascade
	on delete cascade,

	constraint `partner_id_in_addresses_fk`
	foreign key (partner_id) references partners(partner_id)
	on update cascade
	on delete cascade

)ENGINE=InnoDB;