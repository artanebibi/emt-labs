CREATE MATERIALIZED VIEW books_by_author AS
SELECT author_id, COUNT(*) AS book_count
FROM book_authors
GROUP BY author_id;


CREATE MATERIALIZED VIEW authors_per_country AS
SELECT c.id as country_id, COUNT (*) as author_count FROM
             Author as a JOIN Country as c
             on
             a.country_id = c.id
GROUP BY c.id