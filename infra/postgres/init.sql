CREATE TABLE IF NOT EXISTS messages (
    id        UUID PRIMARY KEY,
    "msgId"   TEXT        NOT NULL,
    full_name TEXT        NOT NULL,
    inn       VARCHAR(12) NOT NULL,
    "time"    TIMESTAMP   NOT NULL
);
