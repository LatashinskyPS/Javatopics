create table accounts
(
    id          uuid not null,
    balance     numeric(19, 2),
    bank_id     uuid not null,
    currency_id uuid not null,
    user_id     uuid not null,
    primary key (id)
);

create table banks
(
    id               uuid not null,
    legal_commission numeric(19, 2),
    name             varchar(255),
    usual_commission numeric(19, 2),
    primary key (id)
);

create table currencies
(
    id   uuid not null,
    name varchar(3) not null,
    primary key (id)
);

create table transactions
(
    id               uuid not null,
    account_from_id  uuid not null,
    account_to_id    uuid not null,
    transaction_time date,
    value            numeric(19, 2),
    primary key (id)
);

create table users
(
    id        uuid not null,
    name      varchar(255),
    user_type varchar(255),
    primary key (id)
);

alter table if exists accounts
    add constraint FKb78evw9x9jyy66ld572kl8rgx
        foreign key (bank_id)
            references banks;

alter table if exists accounts
    add constraint FKs08d0ccyak63pou9tfk093dbk
        foreign key (currency_id)
            references currencies;

alter table if exists accounts
    add constraint FKnjuop33mo69pd79ctplkck40n
        foreign key (user_id)
            references users;

alter table if exists transactions
    add constraint FKobhalglpsyu2c1h350il0x6mj
        foreign key (account_from_id)
            references accounts;

alter table if exists transactions
    add constraint FKmhdwjrt3qqlanpfwq18a316rs
        foreign key (account_to_id)
            references accounts;
