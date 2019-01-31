create table person
(
  person_id   serial      not null
    constraint person_pk
      primary key,
  person_name varchar(20) not null,
  person_date timestamp   not null
);

alter table person
  owner to postgres;

create unique index person_person_id_uindex
  on person (person_id);

INSERT INTO public.person (person_id, person_name, person_date) VALUES (39, 'Mark', '1991-10-18 00:00:00.000000');
INSERT INTO public.person (person_id, person_name, person_date) VALUES (40, 'Sam', '1993-03-01 00:00:00.000000');
INSERT INTO public.person (person_id, person_name, person_date) VALUES (42, 'Jimmy', '1988-05-23 00:00:00.000000');
INSERT INTO public.person (person_id, person_name, person_date) VALUES (41, 'Bobby', '12141-03-07 05:09:18.251000');