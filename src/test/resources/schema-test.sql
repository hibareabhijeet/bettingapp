CREATE SEQUENCE IF NOT EXISTS public.user_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 922337
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.user_table
(
    id integer NOT NULL DEFAULT nextval('user_id_seq'::regclass),
    username character varying(255) COLLATE pg_catalog."default",
    first_name character varying(100) COLLATE pg_catalog."default",
    last_name character varying(100) COLLATE pg_catalog."default",
    balance DOUBLE PRECISION,
    CONSTRAINT user_pkey PRIMARY KEY (id),
    CONSTRAINT username_unique UNIQUE (username)
);