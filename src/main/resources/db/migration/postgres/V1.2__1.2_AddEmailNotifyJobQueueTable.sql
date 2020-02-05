--| [1.2] Create email_notify_jobs_queue table
--| Created by Kane <kanez@buildingiq.com>

CREATE SCHEMA IF NOT EXISTS portal;

CREATE TABLE portal.email_notify_jobs_queue
(
    id bigserial NOT NULL PRIMARY KEY,
    user_name text,
    dashboard_id bigserial NOT NULL,
    widget_id text NOT NULL UNIQUE,
    schedule_id bigserial NOT NULL,
    status integer
);

