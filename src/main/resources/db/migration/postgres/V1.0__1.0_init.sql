--| [1.0] Initial setup for the Portal Profile
--| Refer to <project_root>/db/init.sh scripts for user/role creation
--| Created by Allan Aquino <allana@buildingiq.com>

CREATE SCHEMA IF NOT EXISTS portal;

CREATE TABLE portal.dashboard_setting
(
  id bigserial NOT NULL PRIMARY KEY,
  dashboard_id integer NOT NULL,
  dashboard_name text,
  user_name text NOT NULL,
  is_enabled boolean NOT NULL DEFAULT true,
  setting JSONB,
  created_on timestamp with time zone NOT NULL,
  created_by text NOT NULL,
  modified_on timestamp with time zone,
  modified_by text
);
