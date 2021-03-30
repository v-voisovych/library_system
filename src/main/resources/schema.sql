DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    user_number VARCHAR(50),
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    PRIMARY KEY (id)
);

INSERT INTO users(id, user_number, first_name, last_name) values
    (1, 'u1', 'Volodymyr', 'Ivaniv'),
    (2, 'u2', 'Oleg', 'Olegovych'),
    (3, 'u3', 'Stepan', 'Petrenko'),
    (4, 'u4', 'Taras', 'Ivanenko'),
    (5, 'u5', 'Roman', 'Romanenko'),
    (6, 'u6', 'Petro', 'Petrenko'),
    (7, 'u7', 'Igor', 'Igorovych');

CREATE TABLE books (
    id BIGINT NOT NULL AUTO_INCREMENT,
    book_number VARCHAR(50),
    author VARCHAR(50),
    title VARCHAR(50),
    status VARCHAR(50),
    user_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)

);

INSERT INTO books(id, book_number, author, title, status, user_id) values
    (1, 'b1', 'The cat in the hat', 'Dr. Seuss', 'TAKEN', 1),
    (2, 'b2', 'Blueberries for Sal', 'Robert McCloskey', 'TAKEN', 2),
    (3, 'b3', 'The House on the Hill', 'Elizabeth Laird', 'TAKEN', 3),
    (4, 'b4', 'Winnie-The-Pooh and All, All, All', 'A.A. Milne', 'TAKEN', 4),
    (5, 'b5', 'Robinson Crusoe', 'D. Defoe', 'TAKEN', 5),
    (6, 'b6', 'Road to Nowhere', 'Evan Shapiro', 'TAKEN', 6),
    (7, 'b7', 'The Canterville Ghost and Other Stories', 'Oskar Wilde', 'TAKEN', 7),
    (8, 'b8', 'The black hen', 'Antony Pogorelsky', 'TAKEN', 1),
    (9, 'b9', 'Home for Christmas', 'A.Hutchinson', 'TAKEN', 1),
    (10, 'b10', 'Moby Dick', 'H.Melville', 'TAKEN', 2),
    (11, 'b11', 'Humpty and his family', 'Yulia Puchkova', 'FREE', null),
    (12, 'b12', 'Robin Hood', 'Austin Liz', 'FREE', null);