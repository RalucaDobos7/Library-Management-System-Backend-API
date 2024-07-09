CREATE TABLE author (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    bio TEXT
);

CREATE TABLE book (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    isbn VARCHAR(13) NOT NULL UNIQUE,
    author_id VARCHAR(36),
    CONSTRAINT fk_author
        FOREIGN KEY (author_id)
        REFERENCES author(id)
);