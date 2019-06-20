create table resume
(
  uuid      text not null
    constraint resume_pk
      primary key,
  full_name text
);

alter table resume
  owner to postgres;

create table contact
(
  id          serial not null
    constraint contact_pk
      primary key,
  type        text   not null,
  value       text   not null,
  resume_uuid text
    constraint contact_resume_uuid_fk
      references resume
      on delete cascade
);

alter table contact
  owner to postgres;

create unique index contact_resume_uuid_type_index
  on contact (resume_uuid, type);
