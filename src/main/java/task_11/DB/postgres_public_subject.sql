create table subject
(
  subject_id   serial      not null
    constraint subject_pk
      primary key,
  subject_desc varchar(20) not null
);

alter table subject
  owner to postgres;

create unique index subject_subject_id_uindex
  on subject (subject_id);

INSERT INTO public.subject (subject_id, subject_desc) VALUES (25, 'chemistry');
INSERT INTO public.subject (subject_id, subject_desc) VALUES (27, 'geography');
INSERT INTO public.subject (subject_id, subject_desc) VALUES (28, 'physics');
INSERT INTO public.subject (subject_id, subject_desc) VALUES (29, 'computer science');
INSERT INTO public.subject (subject_id, subject_desc) VALUES (26, 'astronomy');