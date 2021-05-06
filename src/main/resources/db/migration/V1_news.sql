CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS "news" (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    title VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(65535) NOT NULL,
    created_at timestamptz,
    published_at timestamptz,
    updated_at timestamptz,
    owner_id uuid NOT NULL,
    CONSTRAINT pkey_news PRIMARY KEY (id),
    CONSTRAINT fkey_user FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);