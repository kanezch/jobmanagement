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
    schedule_period_type integer NOT NULL,
    custom_schedule_period integer,
    include_end_time boolean NOT NULL,
    schedule_end_time timestamp with time zone
);
