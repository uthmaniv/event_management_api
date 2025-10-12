CREATE TABLE "user"(
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

INSERT INTO "user"(username, password)
VALUES
    ('uthman', 'uthmaniv12'),
    ('anas', 'anas12');

