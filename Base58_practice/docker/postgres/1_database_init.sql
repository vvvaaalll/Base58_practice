-- Create the admin user

CREATE ROLE keycloak_admin LOGIN
    PASSWORD 'keycloak_admin'
    SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION;

-- Create the databases

CREATE DATABASE keycloak
    WITH OWNER = keycloak_admin
    ENCODING = 'UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Create the standard users

CREATE USER keycloak_user WITH PASSWORD 'keycloak_user' NOSUPERUSER;