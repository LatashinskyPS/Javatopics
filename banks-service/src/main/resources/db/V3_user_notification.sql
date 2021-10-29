alter table if exists users
    add chat_id varchar(70);

alter table if exists users
    add email varchar(70) default 'example@examle.com';

alter table if exists users
    add enabled_notifications bool default true;
