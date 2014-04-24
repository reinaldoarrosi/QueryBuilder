# QueryBuilder
**THIS PROJECT SUFFERED A MAJOR REDESIGN FROM V1 to V2. IF YOU NEED THE OLD VERSION CHECK THE LABELS AND GRAB WHAT YOU NEED**

QueryBuilder is a fluent library for building SELECT statements for SQLite. It creates parameterized queries by default, but you can also get the query string without parameters by using the **toDebugSqlString** method.

QueryBuilder also uses the excelent JODA-Time library (http://joda-time.sourceforge.net/) to handle dates. **I strongly advise you to use this library instead of using the built-in Date and Time classes from Java.** Version 2.1 is included in the libs folder, but feel free to use the latest version available.

By default, QueryBuilder assumes that dates are stored in the **yyyy-MM-dd** format, and that datetimes are stored in the **yyyy-MM-dd HH:mm:ss** format. You can change the default formats by using the QueryBuilderConfiguration class. You can also change the format for a specific QueryBuilder instance, overriding the defaults.

    // Set new date/datetime formats as the default
    QueryBuilderConfiguration.current().setDateFormat("MM-dd-yyyy").setDateTimeFormat("MM-dd-yyyy HH:mm:ss");
    
    // Change the date/datetime formats only for this QueryBuilder instance
    QueryBuilder builder = new QueryBuilder();
    builder.setDateFormat("MM-dd-yyyy").setDateTimeFormat("MM-dd-yyyy HH:mm:ss");

## Installing

- Clone this repository
- Import project into Eclipse
- Export as .JAR
- Add a reference to the exported jar and to joda-time.2.1.jar located in the libs folder
- Have fun!

## Usage
### Basic statement
    
    QueryBuilder builder = new QueryBuilder();
    builder.select("MyColumnName1", "MyColumnName2", "MyColumnName3").from("MyTableName");
    
    SQLiteDatabase db = getDB();
    db.rawQuery(builder.build(), builder.buildParameters());
    
You can also call *select()* separatelly for each column (or mix both approaches)

    QueryBuilder builder = new QueryBuilder();
    builder.select("MyColumnName1", "MyColumnName2")
        .select("MyColumnName3")
        .select("MyColumnName4")
        .from("MyTableName");
    
    SQLiteDatabase db = getDB();
    db.rawQuery(builder.build(), builder.buildParameters());

### Complex SELECT clause
Projections are used to create complex SELECT columns or expressions
    
    // Column with explicitly declared table (table name or table alias)
    .select(Projection.column("MyTableName", "MyColumnName1"))
    
    // Constants (e.g. SELECT "Teste", 5 FROM MyTable)
    .select(Projection.constant("Teste"), Projection.constant(5))

    // Aggregate functions
    .select(Projection.min("MyColumnName"))
    .select(Projection.max("MyColumnName"))
    .select(Projection.sum("MyColumnName"))
    .select(Projection.avg("MyColumnName"))
    .select(Projection.count("MyColumnName"))
    .select(Projection.countRows()) // e.g. SELECT COUNT(*) FROM MyTable
    
    // Aliases. ANY Projection can be aliased by calling "as"
    .select(Projection.column("MyColumnName").as("MyAlias"))
    .select(Projection.constant(5).as("MyAlias"))
    .select(Projection.min("MyColumnName").as("MyAlias"))

    // Casts. You can cast ANY projection to date, datetime, real, integer or string
    .select(Projection.column("MyColumnName").castAsDate())
    .select(Projection.column("MyColumnName").castAsDateTime())
    .select(Projection.column("MyColumnName").castAsReal())
    .select(Projection.column("MyColumnName").castAsInt())
    .select(Projection.column("MyColumnName").castAsString())
    
### Sub-queries projections
    
    QueryBuilder subQuery = new QueryBuilder();
    subQuery.select("MAX(SubColumn)").from("SubTable");

    QueryBuilder builder = new QueryBuilder();
    builder.select(Projection.subQuery(subQuery).as("MyAlias"))
        .from("MyTable");
    
### FROM clause
For simple FROM clauses the from() method is enough. For more complex FROM clauses you can use the From class

    // Simple from
    .from("MyTable")

    // Aliased table
    .from(From.table("MyTable1").as("MyTableAlias"))
    
### Inner & Left Joins
Note that in this example *MyTable3* is aliased so the *leftJoin()* is performed by using Projection so that the *MyColumn4* can be fully qualified with the table alias
    
    QueryBuilder builder = new QueryBuilder();
    builder.select("MyColumn1")
        .select(Projection.column("AliasedTable", "MyColumn4"))
        .from(From.table("MyTable1").innerJoin("MyTable2")
            .on("MyColumn1", "MyColumn2")
            .leftJoin(From.table("MyTable3").as("AliasedTable"))
            .on(Projection.column("MyColumn3"), Projection.column("AliasedTable", "MyColumn4"))
            .onAnd(Criteria.equal("MyColumn5", 0));
    
### Sub-queries in FROM clause

    QueryBuilder subQuery = new QueryBuilder();
    subQuery.from("SubTable");
    
    QueryBuilder builder = new QueryBuilder();
    builder.from(subQuery);
	
	// you can also JOIN sub-queries: 
	.from(From.subQuery(subQuery).innerJoin("Table").on("COL_FROM_SUBQUERY", "COL_FROM_TABLE")
	.from(From.table("Table").innerJoin(subQuery).on("COL_FROM_TABLE", "COL_FROM_SUBQUERY")
	.from(From.subQuery(subQuery1).innerJoin(subQuery2).on("COL_FROM_SUBQUERY1", "COL_FROM_SUBQUERY2")

### WHERE clause
The WHERE clause is built by using the Criteria class. It allows to write simple and complex criterias and chain them together with AND/OR. The Criteria class works together with the Projection class, so you can create criterias that compare sub-queries, constants and anything that a Projection is able to create

Here is an example that tries to show a broad range of usages of the Criteria class
    
    QueryBuilder subQuery1 = new QueryBuilder();
    subQuery1.select("1").from("SubTable");
    
    QueryBuilder subQuery2 = new QueryBuilder();
    subQuery2.select(Projection.max("MySubQueryColumn1")).from("SubTable");

    QueryBuilder builder = new QueryBuilder();
    builder.select("MyColumn1")
        .from("MyTable")
        .whereAnd(
            Criteria.equals("MyColumn1", 1)
            .and(Criteria.in("MyColumn2", new Object[] { 1, 2, 3 }))
            .or(Criteria.greaterThan(Projection.subQuery(subQuery2), 5))
            .and(Criteria.exists(subQuery))
        );

These are all the methods provided by the Criteria class:

#### NULL Operators

- isNull
- notIsNull

#### Basic operators
Basic operator for strings, numbers and dates. 

When passing a LocalDate or LocalDateTime as parameter, these operators will cast the database column to DATE or DATETIME accordingly. 

When passing a Projection as a parameter these operators will not parameterize this value and use the Projection directly (this is useful for creating a criteria that compares two columns, e.g. SELECT MyColumn3 FROM MyTable WHERE *MyColumn1 = MyColumn2* )

- equals
- notEquals
- greaterThan
- lesserThan
- greaterThanOrEqual
- lesserThanOrEqual

#### Between operators

- between (e.g. MyColumn1 BETWEEN 5 AND 7)
- valueBetween (e.g. 5 BETWEEN MyColumn1 AND MyColumn2)

#### String operators
These are used to create LIKE criterias.

- startsWith (e.g. LIKE 'test%')
- notStartsWith (e.g. NOT LIKE 'test%')
- endsWith (e.g. LIKE '%test')
- notEndsWith (e.g. NOT LIKE '%test')
- contains (e.g. LIKE '%test%')
- notContains (e.g. NOT LIKE '%test%')

#### IN operators

- in
- notIn

#### EXISTS operators (sub-queries only)

- exists
- notExists

#### Logical operators

- or
- and

### Group By and Order By

    // Group by a column
    .groupBy("MyColumn1", "MyColumn2")
    
    // Group by - projections
    .groupBy(Projection.column("MyColumn1"), Projection.column("MyColumn2"))
    
    //Order by ascending
    .orderByAscending("MyColumn1", "MyColumn2")
    
    //Order by ascending - projections
    .orderByAscending(Projection.column("MyColumn1"), Projection.column("MyColumn2"))
    
    //Order by descending
    .orderByDescending("MyColumn1", "MyColumn2");
    
    //Order by descending - projections
    .orderByDescending(Projection.column("MyColumn1"), Projection.column("MyColumn2"))
    
    //Order by ignoring case
    .orderByAscendingIgnoreCase("MyColumn1")
    .orderByDescendingIgnoreCase("MyColumn1")
    
    //Order by ignoring case - projections
    .orderByAscendingIgnoreCase(Projection.column("MyColumn1"))
    .orderByDescendingIgnoreCase(Projection.column("MyColumn1"))
    
    // Remove all group by
    .clearGroupBy()
    
    // Remove all order by
    .clearOrderBy()
    
### Limit and offset AKA Take and Skip AKA paged queries
    
    QueryBuilder builder = new QueryBuilder();
    builder.select("MyColumn1")
        .from("MyTable1")
        .skip(10)
        .take(100);
    
### Union
When using UNION the ORDER BY clause, limit and offset of the union queries will be ignored. Only the ORDER BY clause, limit and offset of the root query will be considered (see example below)
    
    QueryBuilder builder1 = new QueryBuilder();
    builder1.select("MyColumn1")
        .from("MyTable1")
        .orderByAscending("MyColumn1") // this WILL be used
        .skip(10) // this WILL be used
        .take(20); // this WILL be used
        
    QueryBuilder builder2 = new QueryBuilder();
    builder2.select("MyColumn2")
        .from("MyTable2")
        .orderByAscending("MyColumn2") // this WILL NOT be used
        .skip(10) // this WILL NOT be used
        .take(20); // this WILL NOT be used;
        
    QueryBuilder builder3 = new QueryBuilder();
    builder3.select("MyColumn3")
        .from("MyTable3")
        .orderByAscending("MyColumn3") // this WILL NOT be used
        .skip(10) // this WILL NOT be used
        .take(20); // this WILL NOT be used;
        
    builder1.union(builder2).unionAll(builder3);
        
    SQLiteDatabase db = getDB();
    db.rawQuery(builder.build(), builder.buildParameters());
    
