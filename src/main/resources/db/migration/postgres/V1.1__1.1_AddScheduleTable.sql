--| [1.0] Initial setup for the Portal Profile
--| Refer to <project_root>/db/init.sh scripts for user/role creation
--| Created by Allan Aquino <allana@buildingiq.com>

CREATE SCHEMA IF NOT EXISTS portal;

CREATE TABLE portal.schedule
(
    id SERIAL PRIMARY KEY NOT NULL,
    widget_id text NOT NULL,
    schedule_name text,
    include_custom_message boolean,
    custom_message text,
    initial_deliver_time timestamp with time zone,
    schedule_period_type integer,
    custom_schedule_period integer,
    include_end_time boolean,
    schedule_end_time timestamp with time zone
);
