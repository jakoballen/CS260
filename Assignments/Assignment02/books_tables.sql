-- books tables

create table Publishers
(
	PublisherID		integer,
	PubName		varchar(50),
	PubPrimaryCity	varchar(30),
	primary key (PublisherID)
);

create table Books
(
	BookId		integer,
	ISBN			varchar(10),
	Title			varchar(50),
	NumPages		integer,
	PublisherID 	integer references Publishers(PublisherID),
	EditionNum		integer,
	PublicationDate	date,
	primary key (BookID)
); 

create table Authors
(
	AuthorID		integer,
	ALastName		varchar(30),
	AFirstName		varchar(20),
	primary key (AuthorID)
);

create table BookAuthors
(
	BookID		integer references Books(BookID),
	AuthorID		integer references Authors(AuthorID),
	primary key (BookID, AuthorID)
);
