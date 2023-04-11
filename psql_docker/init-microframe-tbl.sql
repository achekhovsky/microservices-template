\c microframe_dev

DROP TABLE IF EXISTS first_service;

CREATE TABLE IF NOT EXISTS first_service 
(
	first_id uuid NOT NULL, 
	first_name text NOT NULL DEFAULT 'default', 
	second_id text, 
	description text, 
	comment_field text, 
	spare_field text, 
	CONSTRAINT first_service_pkey PRIMARY KEY (first_id),
	CONSTRAINT uk_first_second UNIQUE (first_name, second_id)
);

ALTER TABLE first_service OWNER to "Alex";
