alter table if exists users
    drop column if exists chat_id;

alter table if exists users
    drop column if exists email;

alter table if exists users
    drop column if exists cenabled_notifications;