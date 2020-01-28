--| [1.1] Create schedule table
--| Created by Kane <kanez@buildingiq.com>

CREATE SCHEMA IF NOT EXISTS portal;

CREATE TABLE portal.schedule
(
    id bigserial NOT NULL PRIMARY KEY,
    widget_id text NOT NULL UNIQUE,
    schedule_name text NOT NULL,
    include_custom_message boolean NOT NULL,
    custom_message text,
    initial_deliver_time timestamp with time zone NOT NULL,
    schedule_repeat_type integer NOT NULL,
    custom_repeat_type integer,
    custom_repeat_value integer,
    include_end_time boolean NOT NULL,
    schedule_end_time timestamp with time zone
);

CREATE TABLE portal.schedule_email_recipients
(
    id bigserial NOT NULL PRIMARY KEY,
    schedule_id bigserial NOT NULL,
    email_recipients text,
    FOREIGN KEY (schedule_id) REFERENCES portal.schedule (id)
);