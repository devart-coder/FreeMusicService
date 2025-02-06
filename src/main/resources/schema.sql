create database if not exists musicservicedb;
use musicservicedb;

create table if not exists users(
	id bigint not null auto_increment primary key,
	username varchar(50) not null unique,
	password text not null,
	role varchar(25) not null,
	enabled boolean not null default true,
	creation_time timestamp not null unique
);
create table if not exists playlists(
	id bigint not null auto_increment primary key,
	name varchar(50) not null default "default",
	username varchar(50) not null, 
	size bigint not null default 0,
	creation_time timestamp not null unique,

	unique key (name, username),
	foreign key (username) references users(username) 
	on delete cascade 	
);
create table if not exists mainplaylist(
	id bigint not null auto_increment primary key,
	main_playlistname varchar(50) not null,
	username varchar(50) not null,
	
	unique key( main_playlistname, username),
	foreign key (main_playlistname) references playlists(name)
	on delete cascade, 	
	foreign key (username) references users(username) 
	on delete cascade 	
);