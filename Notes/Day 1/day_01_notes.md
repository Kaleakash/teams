DAY 1 – DATABASE, SQL & LIQUIBASE

Data

Data is a collection of raw facts and figures that have no meaning by themselves.

Example: 101, Akash, 22, Pune, 95

Information

Information is processed and organized data that has meaning and is useful for decision-making.

Example: Roll No: 101, Name: Akash, Marks: 95%

Database

A Database is an organized collection of related data stored electronically for easy access and management.

Example: A Student Database containing all student records.

DBMS (Database Management System)

A DBMS is software that helps users create, store, retrieve, update, and delete data from a database.

Examples: MySQL, Oracle Database, PostgreSQL, Microsoft SQL Server.

RDBMS (Relational Database Management System)

An RDBMS is a type of DBMS that stores data in related tables using relationships between them.

Examples: MySQL, Oracle Database, PostgreSQL, Microsoft SQL Server.

Simple Flow
Data
   ↓
Information
   ↓
Database
   ↓
DBMS
   ↓
RDBMS (Data stored in related tables)

MySQL

MySQL is a free and open-source relational database. It is easy to learn, fast, and mainly used for small and medium-sized applications.

Examples: Student Management System, College Management System, E-commerce Website.

Oracle Database

Oracle Database is a commercial relational database. It is highly secure, powerful, and mainly used by large organizations to handle huge amounts of data.

Examples: Banking System, Railway Reservation System, Government Applications.

PostgreSQL

PostgreSQL is a free and open-source relational database. It provides advanced features and is mainly used for enterprise applications, data analytics, and modern software development.

Examples: Financial Applications, AI Applications, Data Analytics Systems.

SQL (Structured Query Language)

SQL stands for Structured Query Language.

SQL is a standard language used to communicate with a relational database. It is used to create, store, retrieve, update, and delete data from a database.


Before writing SQL queries, we must first understand where the database is running and how applications communicate with it.

Many beginners think that SQL is executed directly inside pgAdmin or psql.

This is incorrect.

The actual SQL execution always happens inside the PostgreSQL Server.

Think of PostgreSQL as a restaurant.

• Customer places an order.
• Waiter takes the order.
• Chef prepares the food.
• Waiter serves the food.

Similarly,

• User writes SQL.
• Client sends SQL.
• PostgreSQL executes SQL.
• Result is returned.

The client never executes SQL.

What is PostgreSQL?

PostgreSQL is an Open Source Relational Database Management System (RDBMS).

It is responsible for:

• Storing data permanently.
• Managing multiple databases.
• Executing SQL queries.
• Managing users and permissions.
• Handling transactions.
• Maintaining data consistency.
• Providing backup and recovery.
• Supporting multiple users simultaneously.

PostgreSQL follows ACID properties, making it suitable for enterprise banking applications.

A → Atomicity   → All or Nothing
C → Consistency → Data remains Correct
I → Isolation   → Transactions don't Interfere
D → Durability  → Data is Permanently Saved

Why Deutsche Bank Uses PostgreSQL

Explain to students that banking applications require:

• Data consistency
• High performance
• Security
• Transactions
• Backup
• Reliability

PostgreSQL provides all these features.

That is why many enterprise organizations choose PostgreSQL.

Client-Server Architecture

The most important concept of this topic is Client-Server Architecture.

Applications never directly access database files.

Everything happens through the PostgreSQL Server.

Flow:

Client
↓
SQL Request
↓
PostgreSQL Server
↓
Database Files
↓
Result Returned


The PostgreSQL Server acts as the middle layer between applications and stored data.

Real-Time Example

Suppose a trader clicks

"Show Today's Trades"

What happens?

React Dashboard
↓
Spring Boot REST API
↓
JDBC Driver/ ORM 
↓
PostgreSQL Server
↓
Trade Table
↓
Results Returned
↓

Displayed on Dashboard

Notice that React never communicates directly with the database.

Everything goes through the backend application.

PostgreSQL Client Tools

There are two commonly used client tools.

1. psql

psql is the official command-line client provided by PostgreSQL.

It is preferred by developers because it is lightweight and fast.

Common commands:

\l
Lists all databases.

\c reconx
Connects to the reconx database.

\dt
Lists all tables.

\d trades
Displays the structure of the trades table.

Important Note:

Commands beginning with "" are called Meta Commands.

They are psql commands, not SQL commands.

2. pgAdmin

pgAdmin is a graphical administration tool.

It provides:

• Database Explorer
• Query Tool
• Table Designer
• User Management
• Backup & Restore
• Index Management

It is preferred by beginners because everything can be performed using a graphical interface.

Internally, pgAdmin still sends SQL to PostgreSQL.

Difference Between psql and pgAdmin

psql

• Command Line
• Fast
• Lightweight
• Preferred by Developers
• Easy to automate

pgAdmin

• Graphical Interface
• Beginner Friendly
• Easy to browse database objects
• Better for administration

Remember:

Both connect to the same PostgreSQL Server.

Only the interface changes.

Port 5432 (by default)

Applications communicate with PostgreSQL using Port 5432.

Example

localhost:5432

or

192.168.10.25:5432

This means

Computer Address

PostgreSQL Listening Port

Some commonly used database ports are:

PostgreSQL → 5432

MySQL → 3306

SQL Server → 1433

Oracle → 1521

Whenever Spring Boot connects to PostgreSQL, it uses the host name and port number.

Example:

jdbc:postgresql://localhost:5432/reconx

SQL Query Execution Flow

This is the most important concept of this section.

Whenever we execute a SQL statement, PostgreSQL performs several internal steps.

Flow:

User
↓
psql / pgAdmin / Spring Boot
↓
PostgreSQL Server
↓
Parser
↓
Planner
↓
Executor
↓
Buffer Manager
↓
Storage
↓
Result Returned

Students should remember this sequence.

Step 1 – Client

The client sends SQL to PostgreSQL.

Example:

SELECT *
FROM trades;

The client does not execute SQL.

It only sends the request.

Step 2 – PostgreSQL Server

The PostgreSQL Server receives the SQL request.

It authenticates the user.

It creates a backend process for the session.

Then the SQL is passed to the Parser.

Step 3 – Parser

Parser checks SQL syntax.

Example:

Correct SQL

SELECT *
FROM trades;

Incorrect SQL

SELEC *
FROM trades;

Parser immediately reports:

Syntax Error

No table access occurs because the SQL itself is invalid.

Step 4 – Planner (Query Optimizer)

The Planner decides the fastest way to execute the query.

It checks:

• Available indexes

• Table statistics

• Estimated rows

• Estimated cost

Example:

Query

SELECT *
FROM trades
WHERE trade_ref='TRD1001';

If an index exists,

Planner chooses

Index Scan

Otherwise,

Planner performs

Sequential Scan

The planner's job is to select the cheapest execution plan.

Step 5 – Executor

Executor actually runs the query.

Planner creates the plan.

Executor performs the work.

Think of it like this:

Planner = GPS deciding the route.

Executor = Driver following the route.

Step 6 – Buffer Manager

Before reading the disk,

PostgreSQL checks memory.

If data already exists in memory,

Result is returned immediately.

Otherwise,

Data is loaded from disk.

This reduces disk access and improves performance.

Step 7 – Storage

Finally, PostgreSQL reads data from storage.

Storage contains:

• Tables

• Indexes

• System Catalog

The required rows are read and returned to the client.

Why Indexes Matter

Suppose the Trades table contains 100 million records.

Query:

SELECT *
FROM trades
WHERE trade_ref='TRD202600100';

Without an index,

PostgreSQL scans every row.

With an index,

PostgreSQL directly locates the matching row.

This is why indexes dramatically improve performance.

Real-Time Banking Example

Imagine the operations team needs details of a single trade.

Trade Reference:

TRD202600105

The Trades table contains 80 million records.

Without an index, PostgreSQL may take several seconds because it scans the entire table.

With an index on trade_ref, PostgreSQL immediately jumps to the required record, reducing the response time to milliseconds.

This is critical for banking systems where users expect fast responses.

Common Mistakes

• Thinking pgAdmin stores data.

• Thinking psql executes SQL.

• Confusing PostgreSQL Server with PostgreSQL Client.

• Assuming every query reads from disk.

• Creating indexes without checking whether the query actually uses them.

Best Practices

• Use pgAdmin for learning and administration.

• Learn psql because it is widely used in production environments.

• Always understand the query execution flow before optimizing SQL.

• Use EXPLAIN ANALYZE to verify how PostgreSQL executes a query.

• Create indexes only on frequently searched columns.


Key Takeaways

• PostgreSQL is the database server.

• psql and pgAdmin are client tools.

• SQL is executed by the PostgreSQL Server, not by the client.

• Every SQL query passes through Parser, Planner, Executor, Buffer Manager, and Storage.

• Indexes help the Planner choose faster execution paths.

• Understanding the query execution flow is essential before learning SQL optimization.



Every application starts with a database structure.

Before storing any data, we first need to define:

• What tables are required?

• What columns should each table contain?

• What type of data should each column store?

• What business rules should the database enforce?

This is where DDL comes into the picture.

Think of building a new house.

Before people move into the house, an architect prepares the blueprint.

Similarly,

Before inserting data, we prepare the database structure.

That structure is created using DDL.

What is DDL?

DDL stands for Data Definition Language.

DDL is used to create and modify database objects.

DDL changes the structure of the database, not the data.

Common DDL commands are:

• CREATE

• ALTER

• DROP

• TRUNCATE

• RENAME

DDL Commands
CREATE

Used to create database objects.

Examples:

• Database

• Table

• View

• Index

Example

CREATE TABLE employee(
    id INT,
    name VARCHAR(100)
);

This command creates the table but does not insert any data.

ALTER

Used to modify an existing object.

Example

ALTER TABLE employee
ADD COLUMN salary NUMERIC(10,2);

Use ALTER when business requirements change.

DROP

Used to permanently delete an object.

Example

DROP TABLE employee;

Warning:

DROP removes both the structure and all stored data.

TRUNCATE

Deletes all rows from a table.

Example

TRUNCATE TABLE employee;

Difference:

DROP deletes the table.

TRUNCATE deletes only the records.

The table structure remains.

Real-Time Banking Example

Suppose Deutsche Bank starts a new Trade Reconciliation project.

Initially, there are no tables.

The development team creates:

• trades

• counterparties

• instruments

• users

• audit_events

Each table is created using CREATE TABLE.

Later, a new business requirement arrives.

"We also need Trade Currency."

Instead of creating the table again,

we use ALTER TABLE.

Understanding Data Types

Every column stores a specific type of information.

Choosing the correct data type is extremely important.

Wrong data types lead to:

• Wasted storage

• Poor performance

• Incorrect calculations

• Data validation problems

Common PostgreSQL Data Types
BIGSERIAL

Purpose

Automatically generates unique numbers.

Used for

Primary Keys

Example

id BIGSERIAL PRIMARY KEY

No need to manually insert IDs.

PostgreSQL generates:

1

2

3

4

5

Automatically.

VARCHAR(n)

Stores variable-length text.

Example

trade_ref VARCHAR(30)

Examples:

TRD10001

TRD20260001

VARCHAR saves storage because it only stores the characters actually entered.

CHAR(n)

Stores fixed-length text.

Example

country CHAR(2)

Examples

IN

US

UK

JP

Because country codes always contain exactly two characters.

NUMERIC(18,4)

Most important data type for banking applications.

Example

price NUMERIC(18,4)

18 means

Maximum total digits.

4 means

Digits after decimal.

Examples

100.2500

99999999.9999

Always use NUMERIC for:

• Money

• Prices

• Interest Rates

• Tax

Never use FLOAT for financial calculations.

Reason:

FLOAT stores approximate values.

NUMERIC stores exact values.

TIMESTAMPTZ

Stores date and time with timezone.

Example

created_at TIMESTAMPTZ

Useful for:

• Audit logs

• Trade timestamps

• Application events

Understanding Constraints

A database should not trust users.

Users can make mistakes.

Constraints protect the database.

Think of constraints as security guards.

They stop invalid data from entering the system.

PRIMARY KEY

Purpose

Uniquely identifies every record.

Rules

• Cannot be NULL

• Cannot contain duplicates

Example

id BIGSERIAL PRIMARY KEY

Real Example

Trade IDs

1

2

3

4

Every trade has a unique ID.

NOT NULL

Purpose

Makes a column mandatory.

Example

trade_ref VARCHAR(30) NOT NULL

Now the user cannot insert a trade without Trade Reference.

UNIQUE

Purpose

Prevents duplicate values.

Example

lei_code VARCHAR(20) UNIQUE

LEI (Legal Entity Identifier)

must be unique for every counterparty.

Example

ABC123

cannot appear twice.

CHECK

Purpose

Validates business rules.

Example

quantity NUMERIC(18,4)
CHECK(quantity>0)

Allowed

100

250

1000

Rejected

0

-50

-100

The database itself blocks invalid values.

FOREIGN KEY

Purpose

Creates relationships between tables.

Example

counterparty_id
REFERENCES counterparties(id)

Suppose

Trade references

Counterparty ID 10

If Counterparty 10 does not exist,

PostgreSQL rejects the insert.

This maintains Referential Integrity.

DEFAULT

Assigns a value automatically.

Example

status VARCHAR(20)
DEFAULT 'PENDING'

If the user does not specify Status,

PostgreSQL automatically stores

PENDING.

Similarly,

created_at
DEFAULT now()

Automatically stores the current date and time.

Complete Table Example
CREATE TABLE trades(

id BIGSERIAL PRIMARY KEY,

trade_ref VARCHAR(30) NOT NULL UNIQUE,

counterparty_id BIGINT REFERENCES counterparties(id),

quantity NUMERIC(18,4)
CHECK(quantity>0),

price NUMERIC(18,4)
CHECK(price>0),

status VARCHAR(20)
DEFAULT 'PENDING',

created_at TIMESTAMPTZ
DEFAULT now()

);

Explain the Table Line by Line

id BIGSERIAL PRIMARY KEY

• Auto-generated unique identifier.

• No duplicate IDs.

• Used to identify every trade.

trade_ref VARCHAR(30) NOT NULL UNIQUE

• Stores business Trade Reference.

• Mandatory.

• Cannot repeat.

counterparty_id REFERENCES counterparties(id)

• Creates relationship with Counterparty table.

• Ensures valid Counterparty IDs.

quantity NUMERIC(18,4) CHECK(quantity>0)

• Stores trade quantity.

• Rejects negative quantities.

price NUMERIC(18,4) CHECK(price>0)

• Stores trade price.

• Rejects invalid prices.

status DEFAULT 'PENDING'

• Automatically assigns Pending status.

created_at DEFAULT now()

• Automatically records creation timestamp.

Real-Time Banking Example

Suppose a trader inserts a new trade.

Trade Reference

TRD20260001

Quantity

500

Price

185.50

Counterparty

JP Morgan

Status

Not provided

Created Date

Not provided

PostgreSQL automatically stores:

Status = PENDING

Created Date = Current Timestamp

This reduces application code and keeps data consistent.

Common Mistakes

• Using FLOAT for money.

• Forgetting PRIMARY KEY.

• Allowing NULL values for mandatory columns.

• Not defining FOREIGN KEY relationships.

• Storing duplicate business identifiers.

• Not validating quantities and prices.

Best Practices

• Always use BIGSERIAL or IDENTITY for primary keys.

• Use NUMERIC for financial values.

• Use CHECK constraints for business rules.

• Define FOREIGN KEY relationships.

• Use DEFAULT values wherever possible.

• Let the database enforce data integrity instead of relying only on application code.




One of the biggest mistakes beginners make is storing all information in a single table.

Initially, it looks simple.

However, as the application grows, duplicate data increases, updates become difficult, storage is wasted, and inconsistent data starts appearing.

Normalization is the process of organizing data so that every piece of information is stored only once.

The goal of normalization is not simply to create more tables.

The goal is to remove duplicate data while maintaining relationships between entities.

What is Normalization?

Normalization is a database design technique used to organize data into multiple related tables.

Its primary objectives are:

• Remove duplicate data.

• Reduce storage space.

• Improve data consistency.

• Simplify updates.

• Prevent data anomalies.

In enterprise applications, almost every relational database follows normalization principles.

Why Do We Need Normalization?

Consider a single table storing all trade information.

Trade Ref | Counterparty | Rating | Country | Instrument | Quantity | Price
--------------------------------------------------------------------------------
TRD001    | JP Morgan    | AAA    | US      | AAPL       | 100      | 180
TRD002    | JP Morgan    | AAA    | US      | TSLA       | 250      | 270
TRD003    | JP Morgan    | AAA    | US      | MSFT       | 150      | 310

Ask the participants:

"What information is being repeated?"

Expected Answer:

• Counterparty Name

• Credit Rating

• Country

Explain that every time JP Morgan performs a new trade, the same information is stored again.

This is called Data Redundancy.

Problems with Duplicate Data

Suppose JP Morgan changes its credit rating from AAA to AA.

The database contains 25,000 trade records.

Now we must update all 25,000 rows.

If one row is missed, the database becomes inconsistent.

This is called an Update Anomaly.

Similarly,

Suppose JP Morgan is deleted from one row but still exists in others.

The data becomes inconsistent.

Types of Data Anomalies

1. Insert Anomaly

Suppose we want to register a new Counterparty.

But no trade has been created yet.

If Counterparty information is stored only in the Trade table,

we cannot insert the Counterparty.

Reason:

Trade information is mandatory.

2. Update Anomaly

Counterparty changes its name.

Instead of updating one row,

we update thousands of rows.

If one row is missed,

the database becomes inconsistent.

3. Delete Anomaly

Suppose the only trade for a Counterparty is deleted.

The Counterparty information is also lost.

Business information disappears accidentally.

Solution – Normalize the Database

Instead of storing everything in one table,

split the information into logical entities.

Create separate tables.

Counterparties

ID

Name

Rating

Country

Instruments

ID

ISIN

Asset Class

Currency

Trades

Trade Ref

Counterparty ID

Instrument ID

Quantity

Price

Now,

Trade stores only references.

Actual information is stored only once.

First Normal Form (1NF)

Rule:

Each column should contain only one value.

Every row should be unique.

Incorrect Example

Trade Ref

Counterparties

TRD001

JP Morgan, Goldman Sachs

One column contains multiple values.

This violates 1NF.

Correct

Trade Ref

Counterparty

TRD001

JP Morgan

TRD002

Goldman Sachs

Every column contains only one value.

First Normal Form Rules

• No repeating groups.

• Atomic values only.

• One value per column.

• Every row uniquely identifiable.

Second Normal Form (2NF)

Rule

Table must already satisfy 1NF.

Every non-key column must depend on the entire Primary Key.

Example

Suppose

TradeID

InstrumentID

InstrumentName

Quantity

Primary Key

TradeID + InstrumentID

Question

Does InstrumentName depend on both columns?

No.

It depends only on InstrumentID.

Therefore,

move InstrumentName into Instrument Table.

Third Normal Form (3NF)

Rule

Table must already satisfy 2NF.

No non-key attribute should depend on another non-key attribute.

Example

Counterparty

Country

Country Currency

Country Currency depends on Country,

not on Counterparty.

Therefore,

Country Currency should move into a Country table.

This removes Transitive Dependency.

Understanding Relationships

After normalization,

tables become connected using Foreign Keys.

Example

Counterparties

ID

Name

Country

Trades

Trade Ref

Counterparty ID

Trade stores only

Counterparty ID.

Whenever we need Counterparty Name,

we retrieve it using JOIN.

ReconX Example

The ReconX application follows exactly the same design.

Counterparties Table

Stores

• Name

• LEI Code

• Country

• Credit Rating

Instruments Table

Stores

• ISIN

• Asset Class

• Currency

Trades Table

Stores

• Trade Reference

• Counterparty ID

• Instrument ID

• Quantity

• Price

Notice that Trade does not store Counterparty Name.

Instead,

it stores Counterparty ID.

This is proper normalization.

Advantages of Normalization

• Eliminates duplicate data.

• Reduces storage requirements.

• Simplifies updates.

• Improves consistency.

• Prevents insert, update and delete anomalies.

• Makes maintenance easier.

• Improves scalability.

Disadvantages of Normalization

Explain to students that normalization also has a trade-off.

Because data is divided into multiple tables,

retrieving complete information requires JOIN operations.

Example

Trade Report

↓

Trade Table

JOIN

Counterparty Table

JOIN

Instrument Table

More JOINs mean more complex queries.

However,

the benefits far outweigh the additional complexity.

Real-Time Banking Example

Suppose Deutsche Bank stores 100 million trades.

Without normalization,

Counterparty information is repeated 100 million times.

Storage increases.

Updates become slow.

Reports become inconsistent.

With normalization,

Counterparty information is stored once.

Every trade simply stores Counterparty ID.

This saves storage and guarantees consistency.

Common Mistakes

• Keeping all data in one table.

• Repeating Counterparty information in every Trade.

• Forgetting Foreign Keys.

• Confusing normalization with creating many unnecessary tables.

• Over-normalizing very small databases.

Best Practices

• Identify business entities first.

• Create one table for each entity.

• Store each fact only once.

• Use Primary Keys to identify records.

• Use Foreign Keys to build relationships.

• Normalize first, then optimize performance using indexes if required.

In the previous section, we created the database structure using DDL.

However, an empty database is of no use.



Once tables are created, we need to perform operations on the stored data.

This is where DML (Data Manipulation Language) comes into the picture.

Every banking application continuously performs DML operations.

Whenever a customer performs any action such as creating an account, making a payment, updating personal details, or checking transaction history, the application executes DML statements.

Unlike DDL, DML works only with the data stored inside tables.

What is DML?

DML stands for Data Manipulation Language.

It is used to insert, update, delete, and retrieve records from database tables.

Unlike DDL, DML does not change the table structure.

It only changes the data stored in the table.

The four primary DML commands are:

• INSERT

• UPDATE

• DELETE

• SELECT

These commands are used in almost every database-driven application.

INSERT Statement

The INSERT statement is used to add new records into a table.

Example:

INSERT INTO trades
(trade_ref, counterparty_id, instrument_id, quantity, price)
VALUES
('TRD-20260418-0001',12,88,5000,101.25);

Explain each part:

INSERT INTO trades

Specifies the table where data will be inserted.

Columns

trade_ref

counterparty_id

instrument_id

quantity

price

These are the columns receiving values.

VALUES

Contains the actual data to be stored.

INSERT with RETURNING

The slide introduces an important PostgreSQL feature.

INSERT INTO trades
(trade_ref,counterparty_id,instrument_id,quantity,price)
VALUES
('TRD-20260418-0001',12,88,5000,101.25)
RETURNING id,status;

Explain:

Normally after inserting a record, developers execute another SELECT query to retrieve the generated ID.

PostgreSQL allows us to retrieve generated values immediately using RETURNING.

Suppose the table contains:

id BIGSERIAL PRIMARY KEY

The application does not know what ID PostgreSQL generated.

RETURNING solves this problem.

Output may be:

id      status

1052    PENDING

This is frequently used in Spring Boot applications after inserting new records.

Real-Time Banking Example

A trader submits a new trade.

Trade Reference

TRD202600501

Counterparty

JP Morgan

Quantity

500

Price

185.50

Status is not provided.

Database automatically inserts:

Status = PENDING

ID = 1052

The application immediately receives:

Trade Created Successfully

Trade ID : 1052

Status : PENDING

No extra SELECT query is required.

UPDATE Statement

The UPDATE statement modifies existing records.

Example:

UPDATE trades
SET status='MATCHED'
WHERE trade_ref='TRD-20260418-0001';

Explain each part:

UPDATE trades

Specifies which table will be modified.

SET

Specifies the column to update.

WHERE

Specifies which rows should be modified.

Without WHERE,

every row in the table will be updated.

This is one of the most common production mistakes.

Importance of WHERE Clause

Suppose the Trades table contains:

100 million rows.

Query:

UPDATE trades
SET status='MATCHED';

What happens?

Every trade becomes MATCHED.

This could cause a major production issue.

Correct query:

UPDATE trades
SET status='MATCHED'
WHERE trade_ref='TRD202600501';

Only one trade is updated.

Always verify the WHERE clause before executing UPDATE or DELETE statements.

DELETE Statement

DELETE removes records from a table.

Example:

DELETE FROM trades
WHERE trade_ref='TRD202600501';

Without WHERE:

DELETE FROM trades;

All records are deleted.

The table remains, but it becomes empty.

Always use DELETE carefully in production systems.

SELECT Statement

SELECT retrieves data from the database.

Example:

SELECT trade_ref,
quantity,
price
FROM trades
WHERE status='UNMATCHED'
ORDER BY created_at DESC;

Explain each clause:

SELECT

Specifies which columns to retrieve.

FROM

Specifies the table.

WHERE

Filters records.

Only unmatched trades are returned.

ORDER BY

Sorts the result.

DESC

Newest records appear first.

ORDER BY

ORDER BY controls the sorting of query results.

Example:

ORDER BY created_at DESC;

Newest records appear first.

Example:

ORDER BY price ASC;

Lowest price appears first.

ASC means Ascending.

DESC means Descending.

WHERE Clause

WHERE filters records.

Example:

SELECT *
FROM trades
WHERE quantity>1000;

Returns only trades having quantity greater than 1000.

Examples:

WHERE status='MATCHED'
WHERE price>100
WHERE trade_date=CURRENT_DATE
Understanding the Complete Example from the PPT

The presentation demonstrates three operations.

Step 1

Insert a trade.

INSERT...
RETURNING...

Database creates a new trade.

Status automatically becomes PENDING.

ID is generated automatically.

Step 2

Update trade status.

UPDATE trades
SET status='MATCHED'
WHERE trade_ref='TRD20260418-0001';

Trade moves from Pending to Matched.

Step 3

Retrieve unmatched trades.

SELECT...
WHERE status='UNMATCHED'
ORDER BY created_at DESC;

Dashboard displays the newest unmatched trades first.

Real-Time ReconX Workflow

Morning

Trader submits trade.

↓

INSERT

↓

Trade stored as PENDING

↓

Matching engine processes trade

↓

UPDATE

↓

Status becomes MATCHED

↓

Operations dashboard

↓

SELECT

↓

Displays unmatched trades

This sequence happens continuously throughout the day.

Common Mistakes

• Forgetting the WHERE clause in UPDATE.

• Forgetting the WHERE clause in DELETE.

• Using SELECT * unnecessarily.

• Inserting duplicate business keys.

• Not checking INSERT failures.

• Updating Primary Keys.

Best Practices

• Always specify column names in INSERT statements.

• Verify the WHERE clause before UPDATE or DELETE.

• Use RETURNING when generated IDs are required.

• Retrieve only required columns instead of using SELECT *.

• Always test UPDATE statements with a SELECT query first.

Example:

Before running:

UPDATE trades
SET status='MATCHED'
WHERE trade_ref='TRD202600501';

Run:

SELECT *
FROM trades
WHERE trade_ref='TRD202600501';

Verify the correct row.

Then execute UPDATE.

In the previous section, we learned Normalization.

Normalization removes duplicate data by dividing information into multiple related tables.

However, a new problem arises.

Since the information is now spread across multiple tables, how do we display it together?

For example,

Trade information is stored in the Trades table.

Counterparty information is stored in the Counterparties table.

Instrument information is stored in the Instruments table.

If the business asks,

"Show me today's trade report with Counterparty Name and Instrument ISIN."

Can one table answer this?

No.

This is exactly why SQL provides JOIN.

JOIN combines data stored in multiple related tables.

Think of JOIN as assembling pieces of a puzzle into one complete picture.

Why Do We Need JOIN?

Suppose we have the following tables.

Trades

Trade ID

Trade Ref

Counterparty ID

Instrument ID

Quantity

Price

Counterparties

ID

Counterparty Name

Country

Instruments

ID

ISIN

Asset Class

Question:

Can the Trades table display Counterparty Name?

No.

It only contains Counterparty ID.

Similarly,

Can it display ISIN?

No.

It only contains Instrument ID.

To retrieve complete information,

we combine these tables using JOIN.

Understanding Primary Key and Foreign Key

JOIN works because tables are connected through keys.

Example

Counterparties

ID

1

2

3

Trades

Trade Ref

Counterparty ID

TRD001

1

TRD002

2

Trade references Counterparty using Counterparty ID.

This relationship allows PostgreSQL to combine rows correctly.

Remember:

Primary Key

↓

Unique Identifier

Foreign Key

↓

Reference to another table

JOIN

↓

Uses Primary Key and Foreign Key relationship.

Syntax of JOIN

General syntax

SELECT column_list
FROM table1
JOIN table2
ON table1.column = table2.column;

Always remember:

JOIN combines rows.

ON defines the relationship.

INNER JOIN

INNER JOIN returns only matching records.

If matching data exists in both tables,

the row is returned.

Otherwise,

it is ignored.

Example

Trades

Trade Ref

Counterparty ID

TRD001

1

TRD002

2

TRD003

5

Counterparties

ID

Name

1

JP Morgan

2

Goldman Sachs

INNER JOIN Result

TRD001

JP Morgan

TRD002

Goldman Sachs

Trade TRD003 is ignored because Counterparty 5 does not exist.

INNER JOIN Example from ReconX
SELECT
t.trade_ref,
c.name,
i.isin,
t.quantity,
t.price
FROM trades t
JOIN counterparties c
ON c.id=t.counterparty_id
JOIN instruments i
ON i.id=t.instrument_id
WHERE t.status='UNMATCHED';
Explain Line by Line
FROM trades t

Trade table is the starting point.

Alias

t

is used instead of repeatedly writing

trades.

JOIN counterparties c

Join Counterparty table.

Alias

c

represents Counterparties.

ON c.id=t.counterparty_id

Relationship between the tables.

Counterparty ID stored in Trades must match ID in Counterparties.

JOIN instruments i

Join Instrument table.

ON i.id=t.instrument_id

Relationship between Trades and Instruments.

WHERE t.status='UNMATCHED'

Retrieve only unmatched trades.

Result

Dashboard displays

Trade Reference

Counterparty Name

ISIN

Quantity

Price

LEFT JOIN

LEFT JOIN returns

All rows from the Left Table

Matching rows from Right Table.

If no matching row exists,

NULL is returned.

Example

Trades

TRD001

Counterparty 1

TRD002

Counterparty 2

TRD003

Counterparty 10

Counterparties

1

JP Morgan

2

Goldman Sachs

LEFT JOIN Result

TRD001

JP Morgan

TRD002

Goldman Sachs

TRD003

NULL

Trade still appears.

Counterparty is missing.

Why LEFT JOIN is Important

LEFT JOIN is mostly used for finding missing data.

Example

SELECT
t.trade_ref
FROM trades t
LEFT JOIN counterparties c
ON c.id=t.counterparty_id
WHERE c.id IS NULL;

Explanation

Join all trades.

If Counterparty does not exist,

Counterparty ID becomes NULL.

Return only those rows.

These trades are called

Orphan Records.

Real-Time Banking Example

Nightly validation process.

Suppose

10 million trades are received.

One file containing Counterparties fails.

Now,

Trades exist.

Counterparties do not.

LEFT JOIN immediately identifies all affected trades.

Operations team receives an alert.

RIGHT JOIN

RIGHT JOIN returns

All rows from Right Table

Matching rows from Left Table.

Example

Counterparties

JP Morgan

Goldman Sachs

Morgan Stanley

Trades

TRD001

JP Morgan

TRD002

Goldman Sachs

RIGHT JOIN Result

JP Morgan

Trade Found

Goldman Sachs

Trade Found

Morgan Stanley

NULL

Useful when every Counterparty should appear,

even if no trade exists.

FULL JOIN

FULL JOIN returns

Everything.

Matching rows

Unmatched Left rows

Unmatched Right rows.

Useful during data comparison and reconciliation.

JOIN Types Summary

INNER JOIN

Returns only matching rows.

LEFT JOIN

Returns all Left rows.

RIGHT JOIN

Returns all Right rows.

FULL JOIN

Returns everything.

Understanding the PPT Diagram

The slide shows

Trades

↓

Counterparties

↓

Matching

Think of it as overlapping circles.

INNER JOIN

Intersection only.

LEFT JOIN

Entire Left Circle.

RIGHT JOIN

Entire Right Circle.

FULL JOIN

Both circles combined.

Real-Time ReconX Example

Dashboard Screen

Shows

Trade Reference

Counterparty

Instrument

Price

Quantity

Status

Question

Does any single table contain all these columns?

No.

The dashboard is built using JOIN.

Without JOIN,

enterprise reporting would be impossible.

Common Mistakes

• Forgetting the ON clause.

• Joining unrelated columns.

• Using SELECT * unnecessarily.

• Joining too many tables without indexes.

• Confusing INNER JOIN and LEFT JOIN.

Best Practices

• Always join using Primary Key and Foreign Key.

• Select only required columns.

• Add indexes on JOIN columns.

• Use table aliases (t, c, i) for readability.

• Filter data using WHERE after JOIN.

• Verify JOIN logic with sample data before deploying.

Till now we have learned how to retrieve data using SELECT and JOIN.

But in real-world applications, business users rarely ask for all records.

Instead, they ask questions like:

• How many trades happened today?

• What is the total trade value?

• Which counterparty has the highest number of unmatched trades?

• Which counterparties have more than 100 trades?

• What is the average trade price?

These questions require summarizing data.

This is where Aggregate Functions are used.

What are Aggregate Functions?

Aggregate Functions perform calculations on multiple rows and return a single result.

Instead of returning every row,

they summarize the data.

Common Aggregate Functions are:

• COUNT()

• SUM()

• AVG()

• MIN()

• MAX()

COUNT()

COUNT returns the total number of rows.

Example

SELECT COUNT(*)
FROM trades;

Output

Total Trades

2500000

Real Example

Operations Team asks

"How many trades were received today?"

We use COUNT().

SUM()

SUM calculates the total of numeric values.

Example

SELECT SUM(quantity)
FROM trades;

Suppose

100

200

300

Output

600

Banking Example

Calculate Total Quantity Traded Today.

AVG()

AVG returns the average value.

Example

SELECT AVG(price)
FROM trades;

Suppose

100

200

300

Average

200

Useful for

Average Trade Price

Average Settlement Time

Average Processing Time

MIN()

Returns the smallest value.

Example

SELECT MIN(price)
FROM trades;

Output

Lowest Trade Price.

MAX()

Returns the largest value.

Example

SELECT MAX(price)
FROM trades;

Output

Highest Trade Price.

GROUP BY

GROUP BY divides rows into groups before applying Aggregate Functions.

Without GROUP BY

COUNT() gives one answer.

With GROUP BY

COUNT() gives one answer for each group.

Example

Trades

JP Morgan

JP Morgan

Goldman Sachs

Goldman Sachs

Goldman Sachs

Query

SELECT counterparty,
COUNT(*)
FROM trades
GROUP BY counterparty;

Output

JP Morgan        2

Goldman Sachs    3
Real Banking Example

Business asks:

"Show how many unmatched trades each counterparty has."

Query

SELECT c.name,
COUNT(*)
FROM trades t
JOIN counterparties c
ON c.id=t.counterparty_id
WHERE t.status='UNMATCHED'
GROUP BY c.name;

This report helps operations teams identify counterparties with the highest reconciliation issues.

HAVING Clause

HAVING filters grouped data.

Remember

WHERE filters rows.

HAVING filters groups.

Example

SELECT counterparty,
COUNT(*)
FROM trades
GROUP BY counterparty
HAVING COUNT(*)>5;

Output

Only counterparties having more than five trades.

Difference Between WHERE and HAVING

WHERE

• Filters rows.

• Applied before GROUP BY.

Example

WHERE status='UNMATCHED'

HAVING

• Filters groups.

• Applied after GROUP BY.

Example

HAVING COUNT(*)>10

Easy way to remember:

WHERE → Before Grouping

HAVING → After Grouping

Understanding the PPT Example

The presentation uses

SELECT
c.name,
COUNT(*) AS breaks
FROM trades t
JOIN counterparties c
ON c.id=t.counterparty_id
WHERE t.status='UNMATCHED'
GROUP BY c.name
HAVING COUNT(*)>5
ORDER BY breaks DESC;

Explain line by line.

FROM trades

Start with Trade table.

JOIN counterparties

Retrieve Counterparty Name.

WHERE

Select only unmatched trades.

GROUP BY

Create one group for each Counterparty.

COUNT(*)

Count number of trades.

HAVING

Show only counterparties having more than five unmatched trades.

ORDER BY

Display highest count first.

Subqueries

A Subquery is simply a query inside another query.

Example

Find trades whose price is greater than average price.

SELECT *
FROM trades
WHERE price >
(
SELECT AVG(price)
FROM trades
);

Step 1

Inner Query executes.

Average Price

Suppose

185

Step 2

Outer Query executes.

Returns all trades having price greater than 185.

Views

A View is a stored SQL query.

Instead of writing the same query repeatedly,

we save it once.

Example

CREATE VIEW open_breaks AS

SELECT *

FROM trades

WHERE status='UNMATCHED';

Now

Instead of

SELECT *

FROM trades

WHERE status='UNMATCHED';

Simply write

SELECT *

FROM open_breaks;

Advantages

• Cleaner SQL

• Reusable

• Easier maintenance

• Better security

Real-Time Example

Dashboard

Always shows

Unmatched Trades.

Instead of writing the query in every application,

Create

View

↓

Dashboard

↓

Reports

↓

REST API

↓

Everyone uses the same View.

Transactions

This is the most important concept for Banking Applications.

A Transaction is a group of SQL statements executed as one unit.

Either

Everything succeeds

OR

Everything fails.

There is no partial execution.

Banking Example

Suppose

Transfer ₹10,000

From

Account A

To

Account B

Step 1

Deduct ₹10,000

Step 2

Credit ₹10,000

Suppose

Server crashes after Step 1.

Money deducted.

Not credited.

Customer loses money.

This should never happen.

Transactions solve this problem.

Transaction Example from PPT
BEGIN;

UPDATE trades

SET status='SETTLED'

WHERE id=42;

INSERT INTO audit_events

(entity_id,event_type)

VALUES

(42,'SETTLE');

COMMIT;
Explain Line by Line

BEGIN

Start Transaction.

UPDATE

Update Trade Status.

INSERT

Record Audit Event.

COMMIT

Save both changes permanently.

Suppose

INSERT fails.

Database executes

ROLLBACK.

Result

Trade Status

Returns to previous value.

Audit Record

Not inserted.

Database remains consistent.

COMMIT

COMMIT permanently saves all changes.

Once COMMIT executes,

changes cannot be rolled back.

ROLLBACK

ROLLBACK cancels all changes made during the transaction.

Database returns to its previous consistent state.

ACID Properties

Every Transaction follows ACID.

Atomicity

Either everything succeeds or everything fails.

Consistency

Database remains valid.

Isolation

Multiple users do not interfere.

Durability

Committed data survives crashes.

Explain that PostgreSQL is fully ACID compliant, making it suitable for financial systems.

Real-Time ReconX Example

Trade Matching Process

Update Trade Status

↓

Insert Audit Record

↓

Send Kafka Event

↓

Commit Transaction

If any step fails,

Everything is rolled back.

No half-completed reconciliation exists.

Common Mistakes

• Forgetting GROUP BY.

• Using HAVING instead of WHERE.

• Creating unnecessary Views.

• Leaving Transactions open.

• Forgetting COMMIT.

• Performing financial operations without Transactions.

Best Practices

• Use Aggregate Functions only when summarizing data.

• Use WHERE before GROUP BY.

• Use HAVING after GROUP BY.

• Create Views for frequently used queries.

• Keep Transactions short.

• Always handle Transaction failures properly.

Until now, our database contains properly designed tables and data.

Now imagine that the Trades table contains 100 million records.

Suppose a business user searches for a single trade.

Without indexes, PostgreSQL may need to scan millions of rows.

This increases response time.

Indexes are created to solve this problem.

Indexes improve read performance by allowing PostgreSQL to quickly locate required records instead of scanning the entire table.

What is an Index?

An Index is a special database object that improves the speed of data retrieval.

It works similarly to the index section of a book.

Suppose you want to find the chapter "Normalization" in a 500-page book.

Without an index, you start from page 1 and continue until you find it.

With the book index, you directly jump to the required page.

A database index works in exactly the same way.

Instead of scanning every row, PostgreSQL directly jumps to the matching records.

Real-Life Example

Suppose you have a contact list containing 10 lakh phone numbers.

How do you search for one person?

Option 1

Read every contact one by one.

Very slow.

Option 2

Search by name.

The phone immediately jumps to the correct record.

Database indexes perform the same task.

Why Do We Need Indexes?

Suppose the Trades table contains

100 Million Rows.

Query

SELECT *
FROM trades
WHERE trade_ref='TRD202600100';

Without Index

PostgreSQL performs

Sequential Scan

Meaning

Read

Row 1

↓

Row 2

↓

Row 3

↓

...

↓

Row 100,000,000

Very slow.

With Index

PostgreSQL immediately locates

TRD202600100

Only a few index pages are accessed.

Result

Milliseconds instead of minutes.

What is a B-Tree Index?

The default index type in PostgreSQL is the B-Tree Index.

B-Tree stands for Balanced Tree.

It stores data in a balanced tree structure.

The root node points to branch nodes.

Branch nodes point to leaf nodes.

Leaf nodes contain references to the actual table rows.

Because the tree remains balanced, searching takes very few steps even if the table contains millions of records.

Why is it called Balanced?

Imagine a family tree.

Parent

↓

Children

↓

Grandchildren

Every branch has nearly the same depth.

This balance allows PostgreSQL to locate data quickly.

The search does not depend on the total number of rows.

Whether the table contains

1 Thousand rows

or

100 Million rows,

the number of levels in the B-Tree remains relatively small.

Where are B-Tree Indexes Used?

B-Tree indexes are ideal for:

• Equality searches (=)

Example

WHERE trade_ref='TRD1001'

• Range searches

WHERE price BETWEEN 100 AND 200

• Sorting

ORDER BY trade_date

• Primary Keys

• Foreign Keys

Most enterprise applications use B-Tree indexes extensively.

Creating an Index

Example

CREATE INDEX idx_trades_status
ON trades(status);

Explain

CREATE INDEX

Creates a new index.

idx_trades_status

Name of the index.

ON trades

Table name.

(status)

Column to be indexed.

After creation,

queries filtering by Status become much faster.

Index on Foreign Key

Example

CREATE INDEX idx_trades_counterparty
ON trades(counterparty_id);

This index improves

JOIN performance.

Since JOIN operations frequently use Foreign Keys,

indexing Foreign Key columns is considered a best practice.

Partial Index

The PPT introduces another important concept.

Partial Index.

Instead of indexing every row,

only selected rows are indexed.

Example

CREATE INDEX idx_trades_open

ON trades(status)

WHERE status='UNMATCHED';

Explain

Only unmatched trades are stored inside this index.

Matched and Settled trades are ignored.

The index becomes

Smaller

Faster

Consumes less disk space.

Why Partial Index?

Suppose the Trades table contains

50 Million Rows.

Status Distribution

Matched

48 Million

Pending

1 Million

Unmatched

1 Million

Dashboard always displays

Only Unmatched Trades.

Should we index all 50 Million rows?

No.

Create a Partial Index.

Only 1 Million rows are indexed.

Much faster.

Understanding EXPLAIN

Creating an index does not guarantee PostgreSQL will use it.

How do we verify?

Using

EXPLAIN

Example

EXPLAIN

SELECT *

FROM trades

WHERE status='UNMATCHED';

EXPLAIN does not execute the query.

It only displays the execution plan.

EXPLAIN ANALYZE

Example

EXPLAIN ANALYZE

SELECT *

FROM trades

WHERE status='UNMATCHED';

Difference

EXPLAIN

Only shows the execution plan.

EXPLAIN ANALYZE

Actually executes the query and reports

• Execution Time

• Actual Rows

• Actual Cost

• Scan Type

This is the preferred tool for SQL performance tuning.

Reading Execution Plans

Suppose EXPLAIN ANALYZE displays

Seq Scan on trades

Meaning

PostgreSQL scanned the entire table.

Usually indicates

No suitable index

or

Planner decided the index was not beneficial.

Suppose EXPLAIN ANALYZE displays

Index Scan using idx_trades_open

Meaning

PostgreSQL used the index.

Performance is significantly better.

Example from PPT
CREATE INDEX idx_trades_open

ON trades(status)

WHERE status='UNMATCHED';

EXPLAIN ANALYZE

SELECT *

FROM trades

WHERE status='UNMATCHED';

Step 1

Create Partial Index.

Step 2

Run EXPLAIN ANALYZE.

Step 3

Verify

Index Scan

instead of

Sequential Scan.

Real-Time Banking Example

The Operations Dashboard refreshes every 5 seconds.

It always displays

Open Breaks

(Open means Unmatched Trades.)

Suppose there are

200 Million Trades.

Only

5 Lakh

are unmatched.

Without Partial Index,

every refresh scans all trades.

Dashboard becomes slow.

With Partial Index,

only unmatched trades are searched.

Dashboard responds almost instantly.

Common Mistakes

• Creating indexes on every column.

• Forgetting indexes on Foreign Keys.

• Never checking execution plans.

• Assuming indexes always improve performance.

• Ignoring write overhead caused by excessive indexes.

Best Practices

• Create indexes only on frequently searched columns.

• Index columns used in WHERE clauses.

• Index Foreign Keys used in JOINs.

• Use Partial Indexes for frequently filtered subsets.

• Always verify with EXPLAIN ANALYZE.

• Remove unused indexes.

Remember

Indexes improve reads,

but every INSERT, UPDATE, and DELETE must also update the indexes.

Too many indexes can slow down write operations.

Until now, we have used Aggregate Functions like COUNT(), SUM() and AVG().

One important limitation of Aggregate Functions is that they reduce multiple rows into a single summary.

Suppose we have the following trade data:

Trade Ref     Instrument     Price

TRD001        AAPL           180

TRD002        AAPL           185

TRD003        AAPL           190

If we calculate AVG(price),

we get

185

But what happens to individual trade rows?

They disappear.

Sometimes this is exactly what we want.

But many business reports require both:

• Individual Trade Details

• Group Summary

at the same time.

This is why PostgreSQL provides Window Functions.

What is a Window Function?

A Window Function performs calculations across a group of rows without reducing the number of rows returned.

Unlike GROUP BY,

Window Functions preserve every original row.

Think of a classroom.

Suppose a teacher calculates

Average Marks

Traditional GROUP BY approach

Teacher only announces

Average = 75

Students disappear from report.

Window Function approach

Teacher announces

Student

Marks

Class Average

Rahul

70

75

Anita

80

75

Rohit

75

75

Every student remains visible,

while the average is shown beside each student.

This is exactly how Window Functions work.

Why Do We Need Window Functions?

Business users often ask questions such as:

• Show every trade along with the average price of its instrument.

• Show employee salary with department average.

• Rank customers based on purchase amount.

• Display running account balance.

These reports cannot be produced efficiently using only GROUP BY.

Window Functions solve these problems.

Syntax of Window Function

General Syntax

SELECT
column_name,

window_function()

OVER(

PARTITION BY column

ORDER BY column

)

FROM table;

Remember

The keyword

OVER()

converts a normal aggregate function into a Window Function.

Understanding OVER()

Suppose

Trade

Price

TRD001

100

TRD002

200

TRD003

300

Query

SELECT
price,

AVG(price)
OVER()
FROM trades;

Output

Trade

Price

Average

TRD001

100

200

TRD002

200

200

TRD003

300

200

Notice

Every trade remains.

The average appears beside every row.

Understanding PARTITION BY

PARTITION BY divides data into logical groups.

Suppose

Instrument

Price

AAPL

100

AAPL

200

TSLA

300

TSLA

500

Query

AVG(price)

OVER(

PARTITION BY instrument
)

Result

Instrument

Price

Average

AAPL

100

150

AAPL

200

150

TSLA

300

400

TSLA

500

400

Each instrument gets its own average.

Real-Time Banking Example

ReconX stores trades for multiple instruments.

Business Requirement

Display every trade along with today's average price of that instrument.

Result

Trade

Instrument

Price

Average Price

TRD001

AAPL

180

185

TRD002

AAPL

190

185

TRD003

AAPL

185

185

Operations team can immediately identify trades priced above or below average.

Understanding VWAP

The PPT introduces VWAP.

VWAP means

Volume Weighted Average Price.

Formula

Total(Price × Quantity)

----------------------------

Total Quantity

Unlike a normal average,

VWAP considers trade quantity.

Example

Trade

Price

Qty

TRD1

100

10

TRD2

110

100

Simple Average

105

VWAP

(100×10)+(110×100)

-------------------------

110

≈109.09

The larger trade influences the average more.

This is how financial markets calculate average execution price.

Understanding the PPT Query
SELECT

trade_ref,

SUM(price * quantity)

OVER(

PARTITION BY instrument_id,
trade_date

)

/

NULLIF(

SUM(quantity)

OVER(

PARTITION BY instrument_id,
trade_date

),

0

)

AS vwap

FROM trades;

Explain each step.

SUM(price × quantity)

Calculates total traded value.

SUM(quantity)

Calculates total traded quantity.

PARTITION BY

Creates one calculation per

Instrument

and

Trade Date.

NULLIF()

Prevents division by zero.

Final Result

VWAP appears beside every trade.

Why NULLIF()?

Suppose

Quantity

0

Division

1000 / 0

Database throws

Division by Zero Error.

Instead

NULLIF(quantity,0)

If quantity is zero,

NULL is returned,

preventing runtime errors.

Always use NULLIF() when division may involve zero.

GROUP BY vs Window Function

This is one of the most common interview questions.

GROUP BY

• Combines rows.

• Returns one row per group.

• Individual rows disappear.

Window Function

• Keeps every row.

• Adds calculated value beside each row.

• Used for analytics.

Easy way to remember

GROUP BY

↓

Summary Report

Window Function

↓

Detailed Analytical Report

Common Table Expression (CTE)

CTE stands for

Common Table Expression.

It is a temporary named result set created using

WITH.

Think of it as a temporary table that exists only during query execution.

Why Use CTE?

Suppose a query is very long.

Instead of writing one huge query,

break it into smaller readable steps.

Example

WITH

high_value_trades

AS

(

SELECT *

FROM trades

WHERE price>500

)

SELECT *

FROM high_value_trades;

This improves readability.

Recursive CTE

Recursive CTE allows a query to call itself.

Used for

• Employee Hierarchy

• Organization Structure

• Folder Structure

• Category Tree

• Trade Lifecycle

Example from PPT

Trade Lifecycle

Execution

↓

Confirmation

↓

Matching

↓

Settlement

Instead of writing four separate queries,

Recursive CTE walks through every stage automatically.

Real-Time Banking Example

Suppose a Trade passes through

Execution

↓

Validation

↓

Matching

↓

Settlement

↓

Completed

Recursive CTE generates the complete lifecycle report.

Common Mistakes

• Using GROUP BY when Window Function is required.

• Forgetting OVER().

• Forgetting PARTITION BY.

• Writing unreadable SQL instead of using CTE.

• Using Recursive CTE unnecessarily.

Best Practices

• Use Window Functions for analytical reports.

• Use GROUP BY only for summary reports.

• Always use PARTITION BY carefully.

• Use NULLIF() to avoid division-by-zero errors.

• Break large SQL statements into CTEs.

• Use Recursive CTE only for hierarchical data.

So far, every table we've designed has fixed columns.

For example, the instruments table might contain:

• ISIN

• Asset Class

• Currency

• Price

This works well when every record has the same structure.

But real-world applications are different.

Different asset classes require different attributes.

Examples:

Equity

• Sector

• Exchange

Bond

• Coupon Rate

• Maturity Date

Option

• Strike Price

• Expiry Date

Question:

Should we create separate columns for every possible attribute?

If we do, most columns will remain NULL.

Adding every new asset type will require a database migration.

JSONB solves this problem by allowing flexible attributes without changing the table structure.

What is JSON?

JSON stands for JavaScript Object Notation.

It is a lightweight format used to exchange structured data.

Example

{
  "sector":"Banking",
  "exchange":"NASDAQ"
}

JSON stores data in key-value pairs.

It is widely used in:

• REST APIs

• Configuration Files

• Microservices

• Cloud Applications

What is JSONB?

JSONB means Binary JSON.

PostgreSQL stores JSON in an optimized binary format.

Advantages of JSONB

• Faster searching

• Smaller storage

• Supports indexing

• Supports JSON operators

• Better query performance

In PostgreSQL,

Always prefer JSONB over JSON unless you specifically need to preserve the original text formatting.

Why Not Use Only Fixed Columns?

Suppose we create the following table.

Instrument

ISIN

Asset Class

Sector

Coupon

Strike Price

Expiry

Rating

Now insert an Equity.

Sector = Banking

Coupon = NULL

Strike = NULL

Expiry = NULL

Insert a Bond.

Sector = NULL

Coupon = 7.5

Strike = NULL

Expiry = NULL

Insert an Option.

Sector = NULL

Coupon = NULL

Strike = 250

Expiry = 30-Jun-2027

Most columns remain NULL.

This wastes storage and makes the schema harder to maintain.

JSONB Solution

Instead of creating many optional columns,

store common attributes as normal columns.

ISIN

Asset Class

Currency

Store variable attributes inside one JSONB column.

{
   "sector":"Banking"
}

or

{
   "coupon":7.5,
   "maturity":"2030-12-31"
}

or

{
   "strike":250,
   "expiry":"2027-06-30"
}

No schema change is required when a new asset class is introduced.

Real-Time Banking Example

ReconX stores multiple asset classes.

Equity

{
  "sector":"IT",
  "exchange":"NASDAQ"
}

Bond

{
  "coupon":8.25,
  "issuer":"Government"
}

Option

{
  "strike":210,
  "expiry":"2027-03-31"
}

All of these are stored in the same metadata column.

This gives the application flexibility without changing the database schema.

Creating JSONB Column

Example

ALTER TABLE instruments

ADD COLUMN metadata JSONB

NOT NULL

DEFAULT '{}';

Explain line by line.

ALTER TABLE

Modify existing table.

ADD COLUMN

Create a new column.

metadata

Stores flexible information.

JSONB

Binary JSON format.

DEFAULT '{}'

Every existing row gets an empty JSON object.

This avoids NULL values.

JSON Operators

PostgreSQL provides several operators to read JSON values.

Operator ->

Returns a JSON object.

Example

SELECT metadata -> 'sector'

FROM instruments;

Output

"Banking"

Result is still JSON.

Operator ->>

Returns plain text.

Example

SELECT metadata ->> 'sector'

FROM instruments;

Output

Banking

Use ->> when comparing or displaying values.

Operator @>

Checks whether JSON contains another JSON fragment.

Example

SELECT *

FROM instruments

WHERE metadata @>

'{"sector":"Banking"}';

Explanation

Return all instruments whose metadata contains

{
 "sector":"Banking"
}

This operator is widely used in enterprise applications.

Creating GIN Index

Searching JSON without an index is slow.

Therefore PostgreSQL provides

GIN Index.

Example

CREATE INDEX idx_instruments_metadata

ON instruments

USING GIN(metadata jsonb_path_ops);

Explanation

USING GIN

Creates a Generalized Inverted Index.

Optimized for

• JSONB

• Arrays

• Full-text search

The GIN index allows PostgreSQL to search inside JSON documents efficiently.

Why GIN Instead of B-Tree?

B-Tree works well for:

• Numbers

• Dates

• Equality

• Range Queries

JSON contains nested keys and values.

B-Tree cannot efficiently index document structures.

GIN is designed specifically for "contains" searches such as

WHERE metadata @> '{"sector":"Banking"}'
Complete Example from the PPT
ALTER TABLE instruments

ADD COLUMN metadata JSONB

NOT NULL

DEFAULT '{}';

CREATE INDEX idx_instruments_metadata_gin

ON instruments

USING GIN(metadata jsonb_path_ops);

SELECT

isin,

metadata ->> 'sector'

AS sector

FROM instruments

WHERE metadata @>

'{"sector":"Banking"}';

Flow

Step 1

Add JSONB column.

↓

Step 2

Create GIN Index.

↓

Step 3

Insert JSON values.

↓

Step 4

Search JSON using @>.

↓

GIN Index accelerates the search.

Real-Time ReconX Example

Business Requirement

Support new asset class

"Crypto"

Attributes

{
  "blockchain":"Ethereum",
  "wallet":"Hot Wallet",
  "network":"ERC20"
}

Without JSONB

Developer must

• Add three columns

• Update application

• Execute database migration

With JSONB

Simply store

{
  "blockchain":"Ethereum",
  "wallet":"Hot Wallet",
  "network":"ERC20"
}

No schema modification is required.

This is exactly why ReconX uses a JSONB metadata column for flexible instrument attributes.

Common Mistakes

• Using JSONB for every column.

• Storing relational data inside JSONB.

• Forgetting GIN indexes.

• Using -> instead of ->> when text comparison is required.

• Assuming JSONB replaces normalization.

Best Practices

• Keep fixed business attributes as normal relational columns.

• Use JSONB only for optional or variable attributes.

• Create GIN indexes for frequently searched JSONB columns.

• Validate JSON structure in the application layer because PostgreSQL accepts any valid JSON document by default.

• Continue to use normalization for core business entities.

Until now we have learned:

• B-Tree Index

• GIN Index

These indexes solve many performance problems.

But PostgreSQL supports several specialized indexes.

Different problems require different index types.

Similarly,

Performance alone is not enough.

A Banking Database must also ensure that users can access only the data they are authorized to see.

That is where Roles, Privileges and Row-Level Security (RLS) come into the picture.

Why Different Index Types?

Suppose we have different kinds of data.

Trade Date

↓

Date Range Search

JSON Document

↓

Contains Search

GPS Coordinates

↓

Nearest Location

Huge Time-Series Table

↓

Millions of Daily Records

Can one index solve all these problems?

No.

PostgreSQL provides specialized indexes for specialized use cases.

Types of PostgreSQL Indexes

The four commonly used index types are:

• B-Tree

• GIN

• GiST

• BRIN

Each index is optimized for different query patterns.

B-Tree Index

Already covered earlier.

Best suited for:

• Equality Search

WHERE trade_ref='TRD1001'

• Range Queries

WHERE price BETWEEN 100 AND 200

• Sorting

ORDER BY trade_date

Default PostgreSQL Index.

Used in nearly every application.

GIN Index

GIN means

Generalized Inverted Index.

Best suited for

• JSONB

• Arrays

• Full Text Search

Example

WHERE metadata @>

'{"sector":"Banking"}'

GIN indexes individual keys and values inside documents.

Without GIN,

JSON searches become slow.

GiST Index

GiST means

Generalized Search Tree.

Used for

• Geographical Data

• Spatial Search

• Polygon Search

• Distance Calculations

Example

Find the nearest ATM.

Find customers within

5 KM.

Find delivery vehicles inside a city.

GiST is rarely used in traditional business applications,

but is very common in GIS systems.

BRIN Index

BRIN means

Block Range Index.

It is designed for

Very Large

Naturally Ordered Tables.

Examples

Trade Date

Log Timestamp

Audit Events

Sensor Data

Instead of indexing every row,

BRIN stores information about blocks of rows.

Result

Very Small Index

Very Low Storage

Very Fast Date Range Searches.

Real-Time Banking Example

Trades Table

500 Million Records.

Every report searches

Trade Date

Example

SELECT *

FROM trades

WHERE trade_date

BETWEEN

'2026-04-01'

AND

'2026-04-30';

Instead of creating a huge B-Tree,

BRIN creates a very small index,

making date-range queries efficient for append-only tables.

BRIN Example from PPT
CREATE INDEX idx_trades_date_brin

ON trades

USING BRIN(trade_date);

Explanation

USING BRIN

Creates a Block Range Index.

trade_date

Rows are naturally ordered by date.

Perfect candidate for BRIN.

EXPLAIN ANALYZE

Performance tuning should never rely on assumptions.

Always measure.

Example

EXPLAIN ANALYZE

SELECT COUNT(*)

FROM trades

WHERE trade_date

BETWEEN

'2026-04-01'

AND

'2026-04-30';

EXPLAIN ANALYZE executes the query and reports:

• Execution Time

• Actual Rows

• Cost

• Scan Type

Reading the Execution Plan

Before Index

Seq Scan on trades

Meaning

Entire table scanned.

After BRIN Index

Bitmap Index Scan

on idx_trades_date_brin

Meaning

PostgreSQL used the BRIN index.

Query executes much faster.

Choosing the Correct Index
Requirement	Recommended Index
Equality Search	B-Tree
Range Search	B-Tree
JSON Search	GIN
Full Text Search	GIN
Geographic Search	GiST
Huge Date Table	BRIN

Easy Interview Tip

Choose the index based on

Data Type

Query Pattern

Not based on table size alone.

Database Security

Performance is important.

Security is equally important.

Suppose

Operations Team

should only view trades.

They should not delete them.

Developers

can modify development databases,

but not production.

DBA

can manage everything.

This is achieved using

Roles

and

Privileges.

What is a Role?

A Role is a database identity.

A role may represent

• User

• Team

• Application

• Service Account

Example

CREATE ROLE operations;

The role currently has no permissions.

Permissions are granted separately.

Granting Permissions

Example

GRANT SELECT

ON trades

TO operations;

Operations Team

Can

Read Trades

Cannot

Insert

Update

Delete

Application Role

Spring Boot application usually connects using a dedicated database role.

Example

CREATE ROLE reconx_app

LOGIN

PASSWORD 'secure_password';

Grant required permissions only.

Avoid using PostgreSQL superuser accounts in applications.

Principle of Least Privilege

One of the most important enterprise security principles.

Every user receives

Only the permissions required

Nothing more.

Example

Operations Team

Needs

SELECT

No UPDATE.

Developers

Need

Development Access

No Production Access.

Least Privilege reduces security risks.

Row-Level Security (RLS)

Now suppose

Operations Team

can access trades.

Question

Should they see

Every Trade?

Maybe not.

Suppose

London Team

Should only see

London Trades.

New York Team

Should only see

New York Trades.

This cannot be enforced using GRANT alone.

We need

Row-Level Security.

What is Row-Level Security?

RLS filters rows automatically.

Different users receive different results,

even when executing the same query.

Example

User

London Team

Runs

SELECT *

FROM trades;

Database automatically returns

Only London Trades.

User

New York Team

Runs exactly the same query.

Database returns

Only New York Trades.

Application code remains unchanged.

Security is enforced by PostgreSQL itself.

Why RLS?

Without RLS

Application must manually filter data.

Risk

Developer forgets filter.

Sensitive data becomes visible.

With RLS

Filtering occurs inside PostgreSQL.

Application cannot bypass it.

This makes RLS ideal for financial systems and multi-tenant applications.

Real-Time ReconX Example

ReconX supports multiple business regions.

Users

London

↓

See only London Trades.

Singapore

↓

See only Singapore Trades.

Tokyo

↓

See only Tokyo Trades.

The same SQL query

SELECT *

FROM trades;

Returns different results depending on the logged-in role.

This is exactly the purpose of Row-Level Security.

Common Mistakes

• Choosing the wrong index type.

• Creating unnecessary indexes.

• Ignoring EXPLAIN ANALYZE.

• Granting excessive privileges.

• Running applications using the PostgreSQL superuser.

• Implementing security only in application code.

Best Practices

• Select the correct index based on query type.

• Verify improvements with EXPLAIN ANALYZE.

• Follow the Principle of Least Privilege.

• Create separate roles for Applications, Developers, and DBAs.

• Use Row-Level Security for multi-user enterprise systems.

• Audit role permissions regularly.

So far we have learned how to create tables manually using SQL.

Suppose your project has:

• 20 Developers

• 5 Testers

• 3 Environments (Development, UAT, Production)

Question:

How will everyone keep the database schema exactly the same?

Suppose one developer creates a new column but forgets to inform the team.

Development database becomes different from UAT.

UAT becomes different from Production.

This problem is called Schema Drift.

Liquibase solves this problem by treating database changes exactly like source code.

Every database change becomes version-controlled, reviewable, repeatable and reversible.

What is Liquibase?

Liquibase is an Open Source Database Migration Tool.

Instead of manually executing SQL scripts,

developers write database changes inside version-controlled files.

Liquibase automatically applies those changes in the correct order.

It also records every executed change inside a special table called

DATABASECHANGELOG.

Because of this,

every environment always remains synchronized.

Why Do We Need Liquibase?

Suppose a team has three environments.

Development

↓

Testing

↓

Production

Without Liquibase

Developer manually runs SQL.

Tester manually runs SQL.

DBA manually runs SQL.

Eventually,

all databases become different.

This causes deployment failures.

With Liquibase

Developer commits migration files to Git.

↓

Application starts.

↓

Liquibase automatically checks pending migrations.

↓

Only new migrations are executed.

↓

Every environment stays synchronized.

Real-Time Banking Example

ReconX Version 1.0

Contains

Trades Table

Counterparties Table

Later,

Business asks

"Store JSON metadata for Instruments."

Without Liquibase

DBA manually executes

ALTER TABLE instruments
ADD COLUMN metadata JSONB;

Suppose one Production server misses this change.

Application starts failing.

With Liquibase

Developer creates a new Changeset.

Application automatically applies it everywhere.

Every database stays identical.

Important Liquibase Terms

Understanding these terms is very important.

Changeset

A Changeset represents one database change.

Examples

• Create Table

• Add Column

• Create Index

• Insert Reference Data

Every Changeset must contain

• Unique ID

• Author

Example

<changeSet id="003-jsonb"

author="trainer">

Think of one Changeset as one Git Commit for the database.

Changelog

A Changelog is an ordered collection of Changesets.

Instead of placing every change in one file,

enterprise projects create

001-schema.xml

002-index.xml

003-jsonb.xml

004-security.xml

The Master Changelog includes all of them in sequence.

Master Changelog

Example

<databaseChangeLog>

<include file="changes/001-schema.xml"/>

<include file="changes/002-index.xml"/>

<include file="changes/003-jsonb.xml"/>

</databaseChangeLog>

Explain

Liquibase executes files

Top

↓

Bottom

Never change the order of applied migrations.

Always add a new file for the next release.

DATABASECHANGELOG Table

This is the most important table created by Liquibase.

Question

How does Liquibase know which migrations already ran?

Answer

DATABASECHANGELOG

It stores

• Changeset ID

• Author

• File Name

• Execution Time

• Checksum

Whenever the application starts,

Liquibase checks this table.

If a Changeset already exists,

it is skipped.

If it is new,

it is executed.

Migration Flow

The migration process works as follows.

Application Starts

↓

Liquibase Reads Master Changelog

↓

Reads DATABASECHANGELOG Table

↓

Compares Executed Changesets

↓

Executes Only New Changes

↓

Records Them

↓

Application Starts Successfully

This ensures the same migration never runs twice.

Rollback

Every enterprise migration should have a rollback strategy.

Suppose a release fails.

We must return the database to its previous state.

Liquibase supports this using

Rollback.

Example

<rollback>

ALTER TABLE instruments

DROP COLUMN metadata;

</rollback>

If Version 1.1 fails,

Rollback removes the column.

Database returns to Version 1.0.

Rollback Tags

Suppose

Version 1.0

↓

Version 1.1

↓

Version 1.2

You can create a tag.

Example

release-1.1

If Version 1.2 fails,

Liquibase can rollback

to

release-1.1

instead of undoing changes manually.

Preconditions

Sometimes a migration should execute only under specific conditions.

Example

Run only on PostgreSQL.

<preConditions

onFail="MARK_RAN">

<dbms type="postgresql"/>

</preConditions>

Explanation

If the database is PostgreSQL,

execute the migration.

Otherwise,

mark it as executed and continue.

This avoids failures in H2 or other development databases.

Integrating Liquibase with Spring Boot

Liquibase integrates directly with Spring Boot.

Example

spring:

  liquibase:

    change-log:

      classpath:/db/changelog/db.changelog-master.xml

Flow

Spring Boot Starts

↓

Liquibase Starts

↓

Checks DATABASECHANGELOG

↓

Applies Pending Changes

↓

Spring Boot Finishes Startup

No manual SQL execution is required.

Complete Example

Master Changelog

↓

Includes

001-schema.xml

↓

002-index.xml

↓

003-jsonb.xml

↓

Application Starts

↓

Liquibase checks DATABASECHANGELOG

↓

Applies only missing Changesets

↓

Records execution

↓

Application becomes ready.

Why Should You Never Edit an Applied Changeset?

This is one of the most important interview questions.

Suppose

Changeset 003

already executed.

Developer edits it.

Liquibase calculates a new checksum.

Checksum no longer matches

DATABASECHANGELOG.

Application startup fails with a checksum validation error.

Correct approach

Never edit an applied Changeset.

Instead,

Create

004-fix-column.xml

Every change must be introduced as a new Changeset.

Real-Time Banking Example

ReconX Release

Version 1.0

Creates Trades Table.

Version 1.1

Adds JSONB Metadata.

Version 1.2

Creates GIN Index.

Version 1.3

Adds RLS Policy.

Each release adds new Changesets.

No previous Changeset is modified.

This creates a complete deployment history.

Common Mistakes

• Editing an already executed Changeset.

• Forgetting Rollback.

• Forgetting Preconditions.

• Executing SQL manually outside Liquibase.

• Incorrect include order in the Master Changelog.

• Forgetting to commit migration files to version control.

Best Practices

• One Changeset should represent one logical database change.

• Every Changeset must have a unique ID and Author.

• Always provide Rollback instructions.

• Store all migrations in Git.

• Never edit an executed Changeset.

• Let Spring Boot execute Liquibase automatically during application startup.

In the previous section we learned the theory of Liquibase.

Now let's see how a real enterprise project is organized.

Imagine you join a company.

The project has

• 300 Database Changes

• 150 Developers

• Production running for 5 years

Question

How can a new developer create exactly the same database?

Answer

Simply start the application.

Liquibase automatically builds the database from Version 1 to the latest version.

No manual SQL execution is required.

This is exactly how enterprise projects work.

Project Structure

A typical Spring Boot project contains the following structure.

src/main/resources

└── db

    └── changelog

        ├── db.changelog-master.xml

        └── changes

            ├──001-schema.xml

            ├──002-index.xml

            ├──003-jsonb.xml

            ├──004-security.xml

            ├──005-view.xml

            └──006-data.xml

Explain

Master Changelog

↓

Includes every release file.

Each release file contains one or more related Changesets.

Master Changelog

Example

<databaseChangeLog>

<include file="changes/001-schema.xml"/>

<include file="changes/002-schema.xml"/>

<include file="changes/003-jsonb.xml"/>

</databaseChangeLog>

Explain

Liquibase starts with

001

↓

Then

002

↓

Then

003

The order is extremely important.

Always append new migration files.

Never rearrange old files.

Individual Changeset

Example

<changeSet

id="003-jsonb"

author="trainer">

<sql>

ALTER TABLE instruments

ADD COLUMN metadata JSONB;

</sql>

</changeSet>

Explain line by line.

changeSet

One logical database change.

id

Unique identifier.

author

Developer name.

sql

Actual SQL statement.

Every Changeset must have a unique combination of ID and Author.

Adding Preconditions

Suppose your project supports

PostgreSQL

and

H2 Database.

JSONB exists only in PostgreSQL.

Without Preconditions,

H2 will fail.

Example

<preConditions

onFail="MARK_RAN">

<dbms

type="postgresql"/>

</preConditions>

Meaning

If PostgreSQL

↓

Execute SQL.

Otherwise

↓

Skip execution and mark the Changeset as completed.

This allows one project to support multiple databases.

Adding Rollback

Every enterprise migration should know how to undo itself.

Example

<rollback>

ALTER TABLE instruments

DROP COLUMN metadata;

</rollback>

Meaning

Forward Migration

↓

Add Column

Rollback

↓

Remove Column

Always think

"What if deployment fails?"

before writing a migration.

Spring Boot Configuration

Liquibase is enabled inside

application.yml

Example

spring:

  liquibase:

    enabled: true

    change-log:

      classpath:/db/changelog/db.changelog-master.xml

Explain

enabled

Starts Liquibase automatically.

change-log

Points to the Master Changelog.

When Spring Boot starts,

Liquibase starts automatically.

No developer action is required.

Application Startup Flow

Explain this step slowly.

Spring Boot Starts

↓

Reads application.yml

↓

Liquibase Starts

↓

Loads Master Changelog

↓

Reads DATABASECHANGELOG

↓

Compares executed Changesets

↓

Finds pending Changesets

↓

Executes them

↓

Stores execution record

↓

Spring Boot completes startup

This entire process usually takes only a few seconds.

First Application Startup

Suppose

DATABASECHANGELOG

does not exist.

Liquibase automatically creates it.

Then

001

↓

002

↓

003

↓

004

↓

005

↓

Executed sequentially.

Finally,

DATABASECHANGELOG stores every executed Changeset.

Second Application Startup

Suppose

Application restarts.

Liquibase checks

DATABASECHANGELOG

It finds

001

Already Executed

002

Already Executed

003

Already Executed

Nothing remains.

Result

No SQL executes.

Application starts immediately.

This makes Liquibase safe for repeated application startups.

Adding a New Release

Suppose next sprint introduces

Trade Audit Table.

Developer creates

007-audit.xml

Master Changelog

Now becomes

<include file="changes/007-audit.xml"/>

Application Starts

↓

Liquibase detects

007

is missing.

↓

Executes only

007

↓

Stores record.

Old Changesets are never executed again.

Understanding Rollback Tags

Suppose

Version 1.0

↓

001

002

003

↓

Tag

release-1.0

Later

Version 1.1

↓

004

005

↓

Tag

release-1.1

Suppose deployment fails.

Execute

liquibase rollback release-1.0

Liquibase automatically removes

005

↓

004

Database returns safely to Version 1.0.

No manual SQL is required.

Checksum Validation

One of Liquibase's biggest strengths.

Suppose

Changeset

003

already executed.

Developer edits it.

Liquibase calculates

New Checksum.

DATABASECHANGELOG contains

Old Checksum.

Checksums differ.

Liquibase immediately throws

Checksum Validation Failed.

Application startup stops.

Reason

Executed migrations must remain immutable.

Why Not Edit an Applied Changeset?

Suppose

Developer A

Applied

003

Yesterday.

Developer B

Pulls latest code.

But

003

was modified.

Developer B's database

No longer matches Production.

Liquibase prevents this situation using checksum validation.

Correct Solution

Create

004-fix-jsonb.xml

Never edit

003-jsonb.xml.

Real-Time Banking Example

ReconX Release History

Version 1.0

↓

Create Tables

Version 1.1

↓

Add JSONB Metadata

Version 1.2

↓

Create GIN Index

Version 1.3

↓

Add Row-Level Security

Version 1.4

↓

Create Audit Table

Every release is stored permanently.

At any point,

the DBA can determine exactly which version exists in every environment.

Activity from the PPT

Instructor Activity

Participants should build a Liquibase project.

Tasks

• Create Master Changelog.

• Create Version 1.0 Changesets.

• Create Version 1.1 Changesets.

• Add Preconditions.

• Add Rollback.

• Execute Liquibase Update.

• Perform Rollback.

Expected Result

• DATABASECHANGELOG populated.

• Rollback succeeds.

• No checksum errors.

• Spring Boot starts successfully.

Common Mistakes

• Editing an executed Changeset.

• Forgetting Rollback.

• Incorrect include order.

• Forgetting unique Changeset IDs.

• Executing production SQL manually.

• Ignoring checksum validation errors.

Best Practices

• One Changeset = One logical database change.

• Use sequential file names (001, 002, 003...).

• Always define Rollback.

• Keep Master Changelog clean and ordered.

• Let Spring Boot execute Liquibase automatically.

• Never modify executed Changesets.

• Store all migration files in Git.

Today's software engineers use AI tools such as:

• GitHub Copilot

• Claude

• Gemini

These tools increase productivity.

However,

they do not replace software engineers.

Think of AI as a junior assistant.

It can:

• Generate code

• Write documentation

• Explain concepts

• Create API specifications

• Draft Architecture Decision Records

But it cannot fully understand your project's business requirements without proper context.

The engineer remains responsible for reviewing, correcting, and approving the final output.

What is AI-Assisted Development?

AI-Assisted Development means using AI tools to accelerate software engineering tasks.

Examples include:

• Generating boilerplate code.

• Writing unit tests.

• Creating SQL queries.

• Explaining error messages.

• Drafting documentation.

• Generating OpenAPI specifications.

• Creating Architecture Decision Records (ADRs).

AI helps reduce repetitive work, allowing engineers to focus on design and business logic.

Real-Time Banking Example

Suppose the ReconX team decides to partition the Trades table.

Instead of writing the ADR from scratch,

the engineer provides the following prompt to an AI assistant:

"Write an ADR for partitioning the Trades table by trade_date. The table stores approximately 91 million rows over five years. Most queries filter by trade_date."

The AI generates the first draft.

The engineer then:

• Reviews technical accuracy.

• Adds missing trade-offs.

• Corrects assumptions.

• Commits the final document.

The engineer owns the final decision—not the AI.

What is an ADR?

ADR stands for Architecture Decision Record.

It is a short document that records:

• Why a technical decision was required.

• Which option was selected.

• What the consequences are.

An ADR allows future developers to understand not only what was built but also why it was built.

Why Do We Need ADRs?

Imagine joining a project that has been running for five years.

You discover that:

• The Trades table is partitioned.

• Kafka is used instead of REST.

• PostgreSQL is used instead of Oracle.

Question:

Why were these decisions made?

Without documentation,

new developers waste time asking team members.

With ADRs,

every important architectural decision is documented and easily understood.

Structure of an ADR

Every ADR should contain three main sections.

1. Context

Describe the problem.

Explain:

• Current situation.

• Constraints.

• Business requirements.

Example

"The Trades table stores approximately 91 million rows. Most business queries filter by trade_date."

2. Decision

Describe the selected solution.

Example

"Partition the Trades table by RANGE on trade_date, with one partition per month."

Explain why this option was chosen.

3. Consequences

Describe both advantages and disadvantages.

Advantages

• Faster date-range queries.

• Easier archival.

Disadvantages

• Primary Key must include trade_date.

• Monthly partitions must be created.

An ADR should honestly document trade-offs, not only benefits.

Understanding the ADR Example from the PPT

The presentation includes an ADR for partitioning the Trades table.

Context

• Approximately 50,000 inserts per day.

• Five-year retention.

• About 91 million rows.

Decision

• Partition by trade_date.

• One partition per month.

Consequences

Advantages

• Faster queries using partition pruning.

• Easier archival.

Trade-offs

• Primary Key includes trade_date.

• Monthly partitions must be maintained.

This example demonstrates the expected structure of an ADR.

AI Workflow for Writing an ADR

Follow this workflow.

Step 1

Understand the business problem.

↓

Step 2

Provide complete context to the AI.

↓

Step 3

Ask the AI to generate an ADR.

↓

Step 4

Review every section.

↓

Step 5

Correct technical mistakes.

↓

Step 6

Commit the reviewed ADR to the repository.

This review step is the most important part of the process.

Example AI Prompt

Prompt

Write an Architecture Decision Record.

Project:
ReconX

Problem:
The Trades table stores around 91 million rows over five years.

Business Requirement:
Improve date-range query performance.

Options:
Monthly partitioning.
Quarterly partitioning.

Provide:

Context

Decision

Consequences

A clear prompt results in a better first draft.

AI Can Help With

AI is useful for generating:

• SQL Queries

• Java Classes

• Spring Boot Controllers

• REST APIs

• OpenAPI Specifications

• Unit Tests

• Documentation

• Database Design Suggestions

• ADR Drafts

AI accelerates work but should not replace engineering review.

AI Cannot Replace Engineering Judgment

AI does not understand:

• Organization-specific standards.

• Confidential business rules.

• Internal architecture.

• Production constraints.

For example,

AI may recommend partitioning by customer ID.

However,

the engineering team knows that almost every business query filters by trade_date.

Therefore,

partitioning by trade_date is the correct choice.

The engineer must validate AI recommendations before using them.

Real-Time ReconX Example

During Sprint 0,

the team creates three ADRs.

ADR-0001

Partition Trades by Date.

ADR-0002

Choose PostgreSQL as the Database.

ADR-0003

Use Kafka for Event Streaming.

AI generates the first draft.

The engineering team reviews, edits, and approves each ADR before committing it to the repository.

Activity from the PPT

Instructor Activity

Students should:

• Generate three ADRs using an AI assistant.

Suggested topics:

• Database Choice.

• Kafka vs REST.

• Authentication Strategy.

Tasks:

• Generate the first draft.

• Make at least one meaningful technical correction.

• Commit all ADRs to /docs/adr/.

• Explain both the prompt and the changes made.

The objective is to demonstrate engineering ownership rather than simply using AI.

Knowledge Check

Question

Your team uses an AI assistant to draft an ADR.

What is the correct engineering practice?

A

Commit the AI output without reading it.

B

Review the draft, make corrections, and take responsibility for the final document.

C

Assume AI is always technically correct.

D

Skip code reviews because AI generated the document.

Correct Answer

B

Reason

AI speeds up drafting, but the engineer owns the technical decision and must review the output before committing it.

Common Mistakes

• Copying AI output directly into production.

• Providing vague prompts without project context.

• Ignoring technical inaccuracies.

• Assuming AI understands organization-specific requirements.

• Using AI without reviewing generated documentation.

Best Practices

• Give AI complete and accurate context.

• Ask AI to generate the first draft only.

• Review technical details carefully.

• Correct trade-offs and assumptions.

• Keep ADRs under version control.

• Treat AI as a productivity tool, not as the decision maker.


• Design a normalized PostgreSQL database.

• Create and modify database objects using DDL.

• Manipulate data using DML.

• Write JOINs, aggregate queries, subqueries, views, and transactions.

• Use Window Functions, CTEs, and JSONB.

• Optimize queries using B-Tree, GIN, GiST, and BRIN indexes with EXPLAIN ANALYZE.

• Secure PostgreSQL using roles, privileges, and Row-Level Security.

• Manage database schema changes using Liquibase.

• Use AI responsibly to draft ADRs and other engineering documentation while retaining ownership of the final technical decisions.