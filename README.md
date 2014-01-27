#QueryBuilder
QueryBuilder is a fluent library for building SELECT statements on Android. It creates parameterized queries by default, but you can also get the query string without parameters by using the **toDebugSqlString** method.

Android has a limit of 999 parameters in a query. QueryBuilder will use parameters up to this limit, and for those that go beyond it will use the parameters values directly into the query string. QueryBuilder does use the *DatabaseUtils.sqlEscapeString* method to try and keep the generated query string free from SQL injections attacks.

QueryBuilder also uses the excelent JODA-Time library (http://joda-time.sourceforge.net/) to handle dates. **I strongly advise you to use this library instead of using the built-in Date and Time classes from Java.** Version 2.1 is included in the libs folder, but feel free to use the latest version available.

QueryBuilder assumes that dates are stored in the **yyyy-MM-dd** format, and that datetimes are stored in the **yyyy-MM-dd HH:mm:ss** format. If you need to change this behavior you can modify the *DATE_FORMAT_DB*, *TIME_FORMAT_DB* and *DATETIME_FORMAT_DB* constants in the *Utils.java* file to meet your needs.

##Installing

- Clone this repository
- Import project into Eclipse
- Export as .JAR
- Add a reference to the exported jar and to joda-time.2.1.jar located in the libs folder
- Have fun!

##Usage
###Basic statement
    
    QueryBuilder builder = new QueryBuilder();
    builder.select("MyColumnName1")
        .select("MyColumnName2")
        .select("MyColumnName3")
        .from("MyTableName");
        
    SQLiteDatabase db = getDB();
    db.rawQuery(builder.buildQuery(), builder.buildParameters());

###Aliases

    // Column alias
    .select("MyColumnName1", "MyColumnAlias")

    // Table alias
    .from("MyTable1", "MyTableAlias)
    
    // Column with table alias
    .select("MyColumnName1", "", "MyTableAlias")
    
    // Column with column and table alias
    .select("MyColumnName1", "MyColumnAlias", "MyTableAlias")
    
    // You can always pass the alias directly into the SELECT
    .select("MyTableAlias.MyColumnName1 AS MyColumnAlias")
    
    // or FROM clause
    .from("MyTable1 AS MyTableAlias")
###SubQuery
    
    QueryBuilder subQuery = new QueryBuilder();
    subQuery.select("MAX(SubColumn)").from("SubTable");

    QueryBuilder builder = new QueryBuilder();
    builder.select(subQuery, "MySubQueryAlias")
        .from("MyTable");

###The *where* clause
    
    QueryBuilder builder = new QueryBuilder();
    builder.select("MyColumnName1")
        .select("MyColumnName2")
        .select("MyColumnName3")
        .from("MyTableName")
        .whereAnd("MyColumnName1 = ?", "a")
        .whereOr("MyColumnName2 IN (?, ?)", "b", "c");
        
    SQLiteDatabase db = getDB();
    db.rawQuery(builder.buildQuery(), builder.buildParameters());
    
###Using the Criteria class (more on this later)
    
    Object[] values = new Object[] { "a", "b" }
    
    QueryBuilder builder = new QueryBuilder();
    builder.select("MyColumnName1")
        .select("MyColumnName2")
        .select("MyColumnName3")
        .from("MyTableName")
        .whereAnd(Criteria.equals("MyColumnName1", "a"))
        .whereOr(Criteria.in("MyColumnName2", values));
        
    Criteria c = Criteria.greaterThan("MyColumn3", 10)
        .or(Criteria.isNull("MyColumn3"));
    
    builder.whereAnd(c);
    
    SQLiteDatabase db = getDB();
    db.rawQuery(builder.buildQuery(), builder.buildParameters());
    
### Exists

    QueryBuilder subQuery = new QueryBuilder();
    subQuery.select("1").from("SubTable");

    QueryBuilder builder = new QueryBuilder();
    builder.select("MyColumn1")
        .from("MyTable")
        .whereAnd(Criteria.exists(subQuery));
        
    // Not Exists
    // builder.whereAnd(Criteria.notExists(subQuery));
    
### Inner & Left Joins
    
    QueryBuilder builder = new QueryBuilder();
    builder.select("MyColumn1")
        .from(Join.innerJoin("MyTable1", "MyTable2")
            .on("MyColumn1", "MyColumn2")
            .leftJoin("MyTable3")
            .on("MyColumn3", "MyColumn4")
            .onAnd(Criteria.equal("MyColumn5", 0));
        
    SQLiteDatabase db = getDB();
    db.rawQuery(builder.buildQuery(), builder.buildParameters());
    
### Sub-queries in FROM clause
	QueryBuilder subQuery = new QueryBuilder();
    subQuery.from("SubTable");
	
    QueryBuilder builder = new QueryBuilder();
    builder.from(subQuery);
	
	// you can also JOIN sub-queries: 
	// builder.from(Join.innerJoin(subQuery, "Table").on("COL_FROM_SUBQUERY", "COL_FROM_TABLE")
	// builder.from(Join.innerJoin("Table", subQuery).on("COL_FROM_TABLE", "COL_FROM_SUBQUERY")
	// builder.from(Join.innerJoin(subQuery1, subQuery2).on("COL_FROM_SUBQUERY1", "COL_FROM_SUBQUERY2")
        
    SQLiteDatabase db = getDB();
    db.rawQuery(builder.buildQuery(), builder.buildParameters());
	
### Union
    
    QueryBuilder builder1 = new QueryBuilder();
    builder1.select("MyColumn1")
        .from("MyTable1");
        
    QueryBuilder builder2 = new QueryBuilder();
    builder2.select("MyColumn2")
        .from("MyTable2");
        
    builder1.union(builder2);
    // you can also do a UNION ALL: builder1.unionAll(builder2)
        
    SQLiteDatabase db = getDB();
    db.rawQuery(builder.buildQuery(), builder.buildParameters());

### Group By and Order By

    // Group by a column
    .groupBy("MyColumn1")
    
    //Order by ascending
    .orderByAscending("MyColumn1")
    
    //Order by descending
    .orderByDescending("MyColumn1")
    
    //Order by ignoring case
    .orderByAscendingIgnoreCase("MyColumn1")
    .orderByDescendingIgnoreCase("MyColumn1")
    
### Limit and offset (paged queries)
    
    QueryBuilder builder = new QueryBuilder();
    builder.select("MyColumn1")
        .from("MyTable1")
        .offset(10)
        .limit(100);
    
### The Criteria class (complex criterias)
There is a lot of static methods in the Criteria class. Each of these methods represents an operator that can be used to form a criteria. Below is a list of these methods. Most of them have a self-explanatory name, those that are a bit unclear are further explained.

####NULL Operators
These operators also accept sub-queries

- isNull
- notIsNull

####Basic operators
Basic operator for strings, numbers and dates. Some of these operators also accept sub-queries in them. When passing a LocalDate or LocalDateTime as parameter, these operators will cast the database column to DATE or DATETIME accordingly.

- equals
- notEquals
- greaterThan
- lesserThan
- greaterThanOrEqual
- lesserThanOrEqual
- startsWith
- notStartsWith
- endsWith
- notEndsWith
- contains
- notContains
- between
- valueBetween

####Forced Date operators
These operators act the same as their *basic operators* equivalents where the parameter is of type LocalDate, except that these ones accept a string as parameter, so the date formatting is up to the caller.

- equalsAsDate
- notEqualsAsDate
- greaterThanAsDate
- lesserThanAsDate
- greaterThanOrEqualAsDate
- lesserThanOrEqualAsDate
- betweenAsDate
- valueBetweenAsDate

####Forced DateTime operators
These operators act the same as their *basic operators* equivalents where the parameter is of type LocalDateTime, except that these ones accept a string as parameter, so the date formatting is up to the caller.

- equalsAsDateTime
- notEqualsAsDateTime
- greaterThanAsDateTime
- lesserThanAsDateTime
- greaterThanOrEqualAsDateTime
- lesserThanOrEqualAsDateTime
- betweenAsDateTime
- valueBetweenAsDateTime

####Operators between two columns
These operators are to be used when comparing two columns in a WHERE clause e.g: *Criteria.equalsColumns("MyColumn1", "MyColumn2")*

- equalsColumn
- notEqualsColumn
- lesserThanColumn
- lesserThanOrEqualsColumn
- greaterThanColumn
- greaterThanOrEqualsColumn

The following operator cast both columns to DATE

- equalsColumnAsDate
- notEqualsColumnAsDate
- lesserThanColumnAsDate
- lesserThanOrEqualsColumnAsDate
- greaterThanColumnAsDate
- greaterThanOrEqualsColumnAsDate

The following operators cast both columns to DATETIME

- equalsColumnAsDateTime
- notEqualsColumnAsDateTime
- lesserThanColumnAsDateTime
- lesserThanOrEqualsColumnAsDateTime
- greaterThanColumnAsDateTime
- greaterThanOrEqualsColumnAsDateTime

####IN operators
These operators also accept sub-queries

- in
- notIn

####EXISTS operators (sub-queries only)
- exists
- notExists

####Logical operators

- or
- and

Used to join two Criteria e.g. 

    Criteria.and
    (
        Criteria.equals("MyColumn1", 1), 
        Criteria.equals("MyColumn2", 2)
    )

You can also use a more *fluent* approach
    
    Criteria.equals("MyColumn1", 1)
        .and(Criteria.equals("MyColumn2", 2))
    