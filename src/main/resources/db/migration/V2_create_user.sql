CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS "users" (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    first_name VARCHAR(15) NOT NULL,
    last_name VARCHAR(15) NOT NULL,
    email VARCHAR(40) NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    gender VARCHAR(6) NOT NULL,
    role VARCHAR(7) NOT NULL,
    created_at timestamptz,
    updated_at timestamptz,
    CONSTRAINT pkey_users PRIMARY KEY (id)
);