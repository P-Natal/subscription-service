create table "public"."address"
(
    id              bigserial primary key,
    cep             varchar(8) not null,
    number          int not null,
    complement      varchar(50) not null,
    registry_date   timestamp not null,
    last_update     timestamp not null
);

create table "public"."client"
(
    id              bigserial constraint firstkey primary key,
    address_id      bigserial unique not null references address(id),
    name            varchar(50) not null,
    document        varchar(20) unique not null,
    email           varchar(50) not null,
    status          varchar(30) not null,
    registry_date   timestamp not null,
    last_update     timestamp not null
);