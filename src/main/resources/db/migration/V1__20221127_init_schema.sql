CREATE SEQUENCE hibernate_sequence START 1;

CREATE TABLE "appointment" (
    id SERIAL NOT NULL,
    "name" varchar(255) NOT NULL,
    CONSTRAINT appointment_pkey PRIMARY KEY (id)
);