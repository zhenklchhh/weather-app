create table users (
   id serial primary key,
   login varchar(255),
   email varchar(255),
   password varchar(255)
);

create table locations (
   id serial primary key,
   locationName varchar(255),
   userid int not null,
   latitude decimal not null,
   longitude decimal not null
);

create table sessions (
    id uuid primary key,
    userId int not null,
    expriresAt TIMESTAMP WITH TIME ZONE NOT NULL
);
