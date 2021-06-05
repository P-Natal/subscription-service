create table "public"."client"
(
    id              bigserial constraint firstkey primary key,
    name            varchar(50) not null,
    document        varchar(20) not null,
    status          varchar(30) not null,
    registry_date   timestamp not null
    last_update     timestamp not null
);