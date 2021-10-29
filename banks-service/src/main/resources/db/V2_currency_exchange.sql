
    create table currencies_exchange (
       id uuid not null,
        currency_id uuid,
        date date,
        rate numeric(19, 2) not null,
        primary key (id)
    );

    alter table if exists currencies_exchange 
       add constraint FKwmi72x0if3oew03wajowb32q 
       foreign key (currency_id) 
       references currencies;