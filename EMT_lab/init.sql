-- 🔥 Drop in dependency order
DROP TABLE IF EXISTS wishlist_books CASCADE;
DROP TABLE IF EXISTS wishlist CASCADE;
DROP TABLE IF EXISTS book_authors CASCADE;
DROP TABLE IF EXISTS book CASCADE;
DROP TABLE IF EXISTS author CASCADE;
DROP TABLE IF EXISTS book_shop_user CASCADE;
DROP TABLE IF EXISTS country CASCADE;

CREATE TABLE country (
                         id        SERIAL PRIMARY KEY,
                         name      VARCHAR(255) NOT NULL,
                         continent VARCHAR(255) NOT NULL
);


CREATE TABLE author (
                        id         SERIAL PRIMARY KEY,
                        name       VARCHAR(255) NOT NULL,
                        surname    VARCHAR(255) NOT NULL,
                        country_id INTEGER
);

-- 📚 book
CREATE TABLE book (
                      id                 SERIAL PRIMARY KEY,
                      name               VARCHAR(255) NOT NULL,
                      category           VARCHAR(50)  NOT NULL,
                      available_copies   INTEGER      NOT NULL,
                      rented             BOOLEAN      NOT NULL,
                      is_deleted         BOOLEAN      NOT NULL,
                      rented_by_username VARCHAR(255)
);

CREATE TABLE book_authors (
                              book_id   INTEGER NOT NULL,
                              author_id INTEGER NOT NULL,
                              PRIMARY KEY (book_id, author_id)
);

CREATE TABLE wishlist (
                          id            SERIAL PRIMARY KEY,
                          title         VARCHAR(255) NOT NULL,
                          user_username VARCHAR(255) UNIQUE
);

CREATE TABLE book_shop_user (
                                username                   VARCHAR(255) PRIMARY KEY,
                                password                   VARCHAR(255),
                                name                       VARCHAR(255),
                                surname                    VARCHAR(255),
                                is_account_non_expired     BOOLEAN DEFAULT TRUE,
                                is_account_non_locked      BOOLEAN DEFAULT TRUE,
                                is_credentials_non_expired BOOLEAN DEFAULT TRUE,
                                is_enabled                 BOOLEAN DEFAULT TRUE,
                                role                       VARCHAR(50) NOT NULL,
                                wishlist_id                INTEGER UNIQUE
);

CREATE TABLE wishlist_books (
                                wishlist_id INTEGER NOT NULL,
                                book_id     INTEGER NOT NULL,
                                PRIMARY KEY (wishlist_id, book_id)
);

ALTER TABLE author
    ADD CONSTRAINT fk_author_country
        FOREIGN KEY (country_id) REFERENCES country (id) ON DELETE SET NULL;

ALTER TABLE book
    ADD CONSTRAINT fk_book_user
        FOREIGN KEY (rented_by_username) REFERENCES book_shop_user (username) ON DELETE SET NULL;

ALTER TABLE book_authors
    ADD CONSTRAINT fk_book_authors_book
        FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE;

ALTER TABLE book_authors
    ADD CONSTRAINT fk_book_authors_author
        FOREIGN KEY (author_id) REFERENCES author (id) ON DELETE CASCADE;

ALTER TABLE wishlist
    ADD CONSTRAINT fk_wishlist_user
        FOREIGN KEY (user_username) REFERENCES book_shop_user (username) ON DELETE CASCADE;

ALTER TABLE wishlist_books
    ADD CONSTRAINT fk_wishlist_books_wishlist
        FOREIGN KEY (wishlist_id) REFERENCES wishlist (id) ON DELETE CASCADE;

ALTER TABLE wishlist_books
    ADD CONSTRAINT fk_wishlist_books_book
        FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE;

ALTER TABLE book_shop_user
    ADD CONSTRAINT fk_user_wishlist
        FOREIGN KEY (wishlist_id) REFERENCES wishlist (id) ON DELETE CASCADE;

INSERT INTO book_shop_user (
    username,
    password,
    name,
    surname,
    is_account_non_expired,
    is_account_non_locked,
    is_credentials_non_expired,
    is_enabled,
    role,
    wishlist_id
) VALUES (
             'librarian',
             'librarian',
             'A',
             'E',
             TRUE,
             TRUE,
             TRUE,
             TRUE,
             'LIBRARIAN',
             NULL
         );
