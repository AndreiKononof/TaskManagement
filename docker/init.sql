CREATE SCHEMA IF EXISTS task_schema;

CREATE TABLE IF EXISTS task_schema.task
(
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    status VARCHAR(50),
    priority VARCHAR(50),
    author_id INTEGER NOT NULL,
    executor_id INTEGER,
    create TIMESTAMP,
    update TIMESTAMP
);