CREATE TABLE author (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    bio VARCHAR(255)
);

CREATE TABLE book (
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    isbn VARCHAR(255),
    author_id VARCHAR(255),
    CONSTRAINT fk_author
        FOREIGN KEY (author_id)
        REFERENCES author(id)
);