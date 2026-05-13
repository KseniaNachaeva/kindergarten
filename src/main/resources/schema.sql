CREATE TABLE IF NOT EXISTS groups (
    id     BIGSERIAL PRIMARY KEY,
    name   VARCHAR(100) NOT NULL UNIQUE,
    number INT NOT NULL
);

CREATE TABLE IF NOT EXISTS children (
    id        BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(200) NOT NULL,
    male      BOOLEAN NOT NULL,
    age       INT NOT NULL,
    group_id  BIGINT REFERENCES groups(id) ON DELETE SET NULL
)
