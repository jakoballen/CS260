--- Assignment 2, Computer Science Book Library; CS 260, Fall 2019
--- Student Name: Jakob Allen

--- 1. Find the ISBN, title, and publication date of each book that has the word 
---    'database' (any combination of upper case and lower case letters) in the title,
---    sorted by title alphabetically.  Show all four digits of the year in the 
---    publication date (HINT: use the TO_CHAR function).

SELECT ISBN, Title, TO_CHAR (PublicationDate, 'DD-MM-YYYY')
FROM Books
WHERE LOWER (Title) LIKE '%database%'
ORDER BY Title;

--- 2. Find the title and publication date of each book that is in its 1st edition 
---    and published before June 1st, 2004, ordered by publication date 
---    (most recent to earliest)

SELECT Title, PublicationDate
FROM Books
WHERE PublicationDate < '01-JUN-2004' AND EditionNum = 1
ORDER BY PublicationDate DESC;

--- 3. Find each publisher ID and the number of books published by that
---    publisher

SELECT PublisherID, COUNT(PublisherID) AS BooksPublished
FROM Books
GROUP BY PublisherID
ORDER BY PublisherID;

--- 4. List the title of each book and the number of authors for that book

SELECT Title, COUNT(AuthorID) AS NumberOfAuthors
FROM Books B
JOIN BookAuthors BA ON (B.BookID = BA.BookID)
GROUP BY Title
ORDER BY NumberOfAuthors;
 
--- 5. Find each publisher ID and the number of books published by that
---    publisher, for those publishers who've published more than two
---    books

SELECT PublisherID, COUNT(PublisherID) AS BooksPublished
FROM Books
GROUP BY PublisherID
HAVING COUNT(PublisherID) > 2
ORDER BY PublisherID;

--- 6. List the title of each book and the number of authors for that book
--- for books that have at least three authors

SELECT Title, COUNT(AuthorID) AS NumberOfAuthors
FROM Books B
JOIN BookAuthors BA ON (B.BookID = BA.BookID)
GROUP BY Title
HAVING COUNT(AuthorID) >= 3
ORDER BY Title;

--- 7. Find the book title and publisher name for each book published by
---    Addison Wesley

SELECT Title, PubName
FROM Books B
JOIN Publishers P ON (B.PublisherID = P.PublisherID)
WHERE PubName = 'Addison Wesley'
ORDER BY Title;

--- 8. List each ISBN and book title for each book which has at least one
---    author with either of the letters 'o' or 'u' in either their first name
---    or their last name, sorted by title alphabetically

SELECT DISTINCT ISBN, Title
FROM BOOKS B
JOIN BookAuthors BA ON (B.BookID = BA.BookID)
WHERE BA.AuthorID IN (SELECT AuthorID 
                      FROM Authors 
                      WHERE ((LOWER (ALastName) LIKE '%o%') OR (LOWER (ALastName) LIKE '%u%') OR 
                            (LOWER (AFirstName) LIKE '%o%') OR (LOWER (AFirstName) LIKE '%u%')))
ORDER BY Title;

--- 9. USING SUBQUERIES ONLY (NO JOINS), find each book ISBN and title
---    for each book published by O'Reilly.  NOTE: you must test for
---    the publisher named O'Reilly, not for publisher id 4

SELECT ISBN, Title
FROM Books
WHERE PublisherID = (SELECT PublisherID
                     FROM Publishers
                     WHERE PubName = 'O''Reilly')
ORDER BY Title;


--- 10. USING SUBQUERIES ONLY (NO JOINS), find the first and last name of
---     each author who has authored at least one book with both JDBC and
---     Java (any case) in the title.

SELECT AFirstName, ALastName
FROM Authors
WHERE AuthorID IN(SELECT DISTINCT AuthorID
                  FROM BookAuthors
                  WHERE BookID IN (SELECT BookID
                                   FROM Books
                                   WHERE (LOWER (Title) LIKE '%jdbc%' AND LOWER (Title) LIKE '%java%')))
ORDER BY AFirstName;
  
--- 11. USING SUBQUERIES ONLY (NO JOINS), find the
---     isbn, title and number of pages for each book that has more pages
---     than the average number of pages for all books published by the same
---     publisher.

SELECT ISBN, Title, NumPages
FROM Books B1
WHERE NumPages > (SELECT AVG(NumPages)
                  FROM Books B2
                  WHERE (B2.PublisherID = B1.PublisherID)
                  GROUP BY PublisherID)
ORDER BY PublisherID;
                        
--- 12. Find the ISBN, title and publisher id of each book 
---     which is not published by Wrox 
---     (NOTE: you must use Wrox, not publisher id 3, for this query)
SELECT ISBN, Title, PublisherID
FROM Books
WHERE PublisherID !=(SELECT PublisherID
                     FROM Publishers
                     WHERE PubName = 'Wrox')
ORDER BY PublisherID;

--- 13. Find each unique pair of book titles with different publishers 
---     and the word 'Hibernate' (in any capitalization) in the title.  By unique, 
---     we mean: 
---     1) no rows with the same value as both elements of the pair (e.g.
---        A A should not be included), 
---     2) duplicate rows (e.g. the pairs A B and A B are duplicate rows), and 
---     3) no rows with the same pair in reverse order (e.g. A B and B A are
---        not unique rows in this sense.)

SELECT DISTINCT B1.Title, B2.Title
FROM Books B1
JOIN Books B2 ON (B1.BookID < B2.BookID)
WHERE B1.PublisherID != B2.PublisherID AND (LOWER (B1.Title) LIKE '%hibernate%' AND LOWER (B2.Title) LIKE '%hibernate%')
ORDER BY B1.Title, B2.Title;

--I assumed that we want the pairs where both titles contain the word 'hibernate'


--- 14. Find the author first name, last name, and count of books 
---     written by that author even if the author has not written any books

SELECT AFirstName, ALastName, COUNT(BA.BookID)
FROM Authors A
LEFT OUTER JOIN BookAuthors BA ON (A.AuthorID = BA.AuthorID)
GROUP BY AFirstName, ALastName,BA.AuthorID
ORDER BY BA.AuthorID, AFirstName, ALastName;

--- 15. (Extra Credit - not required) Find each author id
---     who has authored at least one book for every publisher.

SELECT AuthorID
FROM BookAuthors BA
JOIN Books B ON (BA.BookID = B.BookID)
GROUP BY AuthorID
HAVING COUNT(PublisherID)= (SELECT COUNT(DISTINCT PublisherID) 
                            FROM Publishers);

SELECT AuthorID 
FROM BookAuthors BA
    MINUS
SELECT AuthorID 
FROM(SELECT AuthorID, PublisherID
     FROM BookAuthors BA
     CROSS JOIN Publishers P
        MINUS
     SELECT AuthorID, PublisherID
     FROM BookAuthors BA
     JOIN Books B ON (B.BookID = BA.BookID));

