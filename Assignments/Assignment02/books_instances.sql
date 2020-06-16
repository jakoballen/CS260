-- CS Library Instances

-- Publisher instances (PublisherID, PubName, PubPrimaryCity)
insert into Publishers values (1, 'Manning', 'Greenwich');
insert into Publishers values (2, 'Addison Wesley', 'Upper Saddle River');
insert into Publishers values (3, 'Wrox', 'Birmingham');
insert into Publishers values (4, 'O''Reilly', 'Sebastopol');
insert into Publishers values (5, 'Wiley', 'Hoboken');
insert into Publishers values (6, 'Morgan Kaufman', 'San Francisco');
insert into Publishers values (7, 'McGraw-Hill/Osborne', 'Emeryville');
insert into Publishers values (8, 'Auerbach Publishers', 'Boca Raton');

-- Books instances (BookID, ISBN, Title, NumPage, PublisherID, Edition, PubDate)
insert into Books values (1, '193239415X', 'Hibernate In Action', 406, 1, 1, '01-OCT-2004');
insert into Books values (2, '0321268199', 'Hibernate: A J2EE Developer''s Guide', 351, 2, 1, '01-NOV-2004');
insert into Books values (3, '1861004370', 'Beginning Java Databases', 973, 3, 1, '15-JUN-2001');
insert into Books values (4, '0596005229', 'Java Database Best Practices', 271, 4, 1, '01-MAY-2003');
insert into Books values (5, '1861004370', 'Database Programming with JDBC and Java', 324, 4, 2, '01-AUG-2000');
insert into Books values (6, '0471202835', 'Agile Database Techniques', 447, 5, 1, '17-OCT-2003');
insert into Books values (7, '1449305008', 'Privacy and Big Data', 91, 4, 1, '02-OCT-2011');
insert into Books values (8, '0596527993', 'SQL Hacks', 386, 4, 1, '01-DEC-2006');
insert into Books values (9, '0131401572', 'Data Access Patterns', 469, 2, 1, '21-SEP-2003');
insert into Books values (10, '1558605150', 'Database Design for Smarties', 442, 6, 1, '08-MAR-1999');
insert into Books values (11, '0071598758', 'Oracle Database 11g: The Complete Reference', 1368, 7, 1, '07-JAN-2009');
insert into Books values (12, '0849318173', 'Physical Database Design Using Oracle', 247, 8, 1, '27-JUL-2004');
insert into Books values (13, '0072133252', 'Oracle Security Handbook', 624, 7, 1, '14-AUG-2001');
insert into Books values (14, '9781449324', 'Oracle PL/SQL Programming', 1392, 4, 6, '06-FEB-2014');
insert into Books values (15, '059600088X', 'Java Programming With Oracle JDBC', 450, 4, 1, '08-DEC-2001');
insert into Books values (16, '0123456789', 'The World of Databases, Part 1', 250, 1, 1, '01-JAN-2003');
insert into Books values (17, '1234567890', 'The World of Databases, Part 2', 275, 2, 1, '01-JAN-2004');
insert into Books values (18, '2345678901', 'The World of Databases, Part 3', 225, 3, 1, '01-JAN-2005');
insert into Books values (19, '3456789012', 'The World of Databases, Part 4', 295, 4, 1, '01-JAN-2006');
insert into Books values (20, '4567890123', 'The World of Databases, Part 5', 325, 5, 1, '01-JAN-2007');
insert into Books values (21, '5678901234', 'The World of Databases, Part 6', 350, 6, 1, '01-JAN-2008');
insert into Books values (22, '6789012345', 'The World of Databases, Part 7', 375, 7, 1, '01-JAN-2009');
insert into Books values (23, '7890123456', 'The World of Databases, Part 8', 400, 8, 1, '01-JAN-2010');

-- Authors instances (AuthorID, ALastName, AFirstName)
insert into Authors values (1, 'Bauer', 'Christian');
insert into Authors values (2, 'King', 'Gavin');
insert into Authors values (3, 'Iverson', 'Will');
insert into Authors values (4, 'Mukhar', 'Kevin');
insert into Authors values (5, 'Lauinger', 'Todd');
insert into Authors values (6, 'Carnell', 'John');
insert into Authors values (7, 'Reese', 'George');
insert into Authors values (8, 'Ambler', 'Scott');
insert into Authors values (9, 'Craig', 'Terence');
insert into Authors values (10, 'Ludloff', 'Mary');
insert into Authors values (11, 'Cumming', 'Andrew');
insert into Authors values (12, 'Russell', 'Gordon');
insert into Authors values (13, 'Nock', 'Clifton');
insert into Authors values (14, 'Muller', 'Robert');
insert into Authors values (15, 'Loney', 'Kevin');
insert into Authors values (16, 'Burleson', 'Donald');
insert into Authors values (17, 'Theriault', 'Marlene');
insert into Authors values (18, 'Newman', 'Aaron');
insert into Authors values (19, 'Feuerstein', 'Steven');
insert into Authors values (20, 'Bales', 'Donald');
insert into Authors values (21, 'Prolific', 'I.M.');

-- BookAuthors instances (BookID, AuthorID)
insert into BookAuthors values (1, 1);
insert into BookAuthors values (1, 2);
insert into BookAuthors values (2, 3);
insert into BookAuthors values (3, 4);
insert into BookAuthors values (3, 5);
insert into BookAuthors values (3, 6);
insert into BookAuthors values (4, 7);
insert into BookAuthors values (5, 7);
insert into BookAuthors values (6, 8);
insert into BookAuthors values (7, 9);
insert into BookAuthors values (7, 10);
insert into BookAuthors values (8, 11);
insert into BookAuthors values (8, 12);
insert into BookAuthors values (9, 13);
insert into BookAuthors values (10, 14);
insert into BookAuthors values (11, 15);
insert into BookAuthors values (12, 16);
insert into BookAuthors values (13, 17);
insert into BookAuthors values (13, 18);
insert into BookAuthors values (14, 19);
insert into BookAuthors values (15, 20);
insert into BookAuthors values (16, 21);
insert into BookAuthors values (17, 21);
insert into BookAuthors values (18, 21);
insert into BookAuthors values (19, 21);
insert into BookAuthors values (20, 21);
insert into BookAuthors values (21, 21);
insert into BookAuthors values (22, 21);
insert into BookAuthors values (23, 21);

