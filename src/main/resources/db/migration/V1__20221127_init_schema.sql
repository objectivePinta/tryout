CREATE SEQUENCE hibernate_sequence START 1;

CREATE TYPE "user_type" AS ENUM (
	'CUSTOMER',
	'BUSINESS');

CREATE TYPE "user_role" AS ENUM (
     'ADMIN', 'BUSINESS', 'CUSTOMER', 'NO_ROLE'
);

CREATE TABLE "user_info"
(
    id                    SERIAL       NOT NULL,
    "name"                varchar(255) NOT NULL,
    "username"            varchar(255) NOT NULL,
    "email"               varchar(255) NOT NULL,
    "password"            varchar(255) NOT NULL,
    "address"             varchar(255),
    "has_confirmed_email" boolean DEFAULT false,
    "roles"               user_role ARRAY,
    "user_type"           "user_type"  NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);

CREATE TABLE "appointment"
(
    id            SERIAL       NOT NULL,
    "name"        varchar(255) NOT NULL,
    starting_time timestamp    NOT NULL,
    duration      smallint     NOT NULL,
    customer_id   SERIAL REFERENCES user_info (id),
    CONSTRAINT appointment_pkey PRIMARY KEY (id)
);


CREATE TABLE "business"
(
    id         SERIAL       NOT NULL,
    "name"     varchar(255) NOT NULL,
    "address"  varchar(255) NOT NULL,
    "admin_id" SERIAL REFERENCES user_info (id),
    CONSTRAINT business_pkey PRIMARY KEY (id)
);
