SET
MODE MYSQL;

CREATE TABLE movie
(
    id          integer PRIMARY KEY,
    title       varchar(30) NULL,
    description varchar(30) NULL,
    rating      float(2) NULL,
    genre       varchar(30) NULL
);

create table viewers
(
  name varchar(30),
  age integer,
  preferred_movie_type varchar(40)
);
