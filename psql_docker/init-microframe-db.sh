#!/bin/bash
set -e
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	CREATE USER Alex WITH PASSWORD 'postgres';
	CREATE DATABASE microframe_dev;
	GRANT ALL PRIVILEGES ON DATABASE microframe_dev TO Alex;
EOSQL
