CREATE SCHEMA game_rental_system AUTHORIZATION postgres;

SET search_path TO game_rental_system;

CREATE TABLE _user(
    user_name varchar(32) PRIMARY KEY,
    user_password varchar(32) NOT NULL,
    user_level integer NOT NULL CHECK (user_level = 0 OR user_level = 1),
    user_address_city varchar(32),
    user_address_zip integer,
    user_address_street varchar(32),
    user_address_number varchar(32),
    user_phone bigint
);

CREATE TABLE _type (
    type_name varchar(16) PRIMARY KEY
);

CREATE TABLE _game (
	game_id serial PRIMARY KEY,
    game_name varchar(16) NOT NULL,
    game_details varchar(512),
    game_price double precision NOT NULL CHECK (game_price > 0),
    game_user varchar(32) REFERENCES _user(user_name) ON DELETE CASCADE ON UPDATE CASCADE,
    game_genre varchar(32),
    game_type varchar(16) REFERENCES _type(type_name)
);

CREATE TABLE _rent (
	rent_id serial PRIMARY KEY,
    rent_from timestamp NOT NULL,
    rent_to timestamp NOT NULL CHECK (rent_to > rent_from),
    rent_user varchar(32) REFERENCES _user(user_name) ON DELETE CASCADE ON UPDATE CASCADE,
    rent_game integer REFERENCES _game(game_id) ON DELETE CASCADE
);

CREATE TABLE _comment (
	comment_id serial PRIMARY KEY,
    comment_text varchar(512) NOT NULL,
    comment_date timestamp DEFAULT NOW(),
    comment_game integer REFERENCES _game(game_id) ON DELETE CASCADE,
    comment_user varchar(32) REFERENCES _user(user_name) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE _game_rate (
	grate_game integer REFERENCES _game(game_id) ON DELETE CASCADE,
    grate_user varchar(32) REFERENCES _user(user_name) ON DELETE CASCADE ON UPDATE CASCADE,
    grate integer NOT NULL CHECK (grate >= 0 AND grate <= 5)
);
ALTER TABLE _game_rate ADD CONSTRAINT
PK_game_rate PRIMARY KEY(grate_game,grate_user);

insert into _type (type_name) values ('Board');
insert into _type (type_name) values ('PC');
insert into _type (type_name) values ('PS4');
insert into _type (type_name) values ('PS5');
insert into _type (type_name) values ('XBOX 360');
insert into _type (type_name) values ('XBOX ONE');
insert into _type (type_name) values ('SWITCH');
insert into _type (type_name) values ('PSP');
insert into _type (type_name) values ('PS2');
insert into _type (type_name) values ('PS3');
insert into _type (type_name) values ('PS1');
insert into _type (type_name) values ('CARD');
insert into _type (type_name) values ('NES');
insert into _type (type_name) values ('SEGA');
insert into _type (type_name) values ('GAMEBOY');
insert into _type (type_name) values ('GAMECUBE');