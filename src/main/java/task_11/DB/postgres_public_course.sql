create table course
(
  person_id  integer not null
    constraint course_person_id_fk
      references person
      on update restrict on delete restrict,
  subject_id integer not null
    constraint course_subject_subject_id_fk
      references subject
      on update restrict on delete restrict,
  constraint course_pk
    primary key (person_id, subject_id)
);

alter table course
  owner to postgres;

create index course_person_id_subject_id_index
  on course (person_id, subject_id);

INSERT INTO public.course (person_id, subject_id) VALUES (39, 25);
INSERT INTO public.course (person_id, subject_id) VALUES (39, 26);
INSERT INTO public.course (person_id, subject_id) VALUES (39, 27);
INSERT INTO public.course (person_id, subject_id) VALUES (40, 27);
INSERT INTO public.course (person_id, subject_id) VALUES (41, 27);
INSERT INTO public.course (person_id, subject_id) VALUES (40, 25);
INSERT INTO public.course (person_id, subject_id) VALUES (40, 26);
INSERT INTO public.course (person_id, subject_id) VALUES (41, 25);
INSERT INTO public.course (person_id, subject_id) VALUES (41, 26);