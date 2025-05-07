CREATE MATERIALIZED VIEW books_by_author AS
SELECT author_id, COUNT(*) AS book_count
FROM book_authors
GROUP BY author_id;


CREATE MATERIALIZED VIEW authors_per_country AS
SELECT c.id as country_id, COUNT (*) as author_count FROM
             Author as a JOIN Country as c
             on
             a.country_id = c.id
GROUP BY c.id;




CREATE MATERIALIZED VIEW user_author_rentedBooks AS
SELECT DISTINCT bsu.username as user_username, a.name as author_name, COUNT(*) as number_books_rented FROM
             book_shop_user as bsu
             join
             wishlist_books as wb
             on bsu.wishlist_id = wb.wishlist_id
             join
             book_authors as ba
             on ba.book_id = wb.book_id
             join
             author as a
             on ba.author_id = a.id
GROUP BY bsu.username, a.name