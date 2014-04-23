package com.reinaldoarrosi.android.querybuilder.sqlite.projection;

import java.util.List;

import com.reinaldoarrosi.android.querybuilder.sqlite.QueryBuilder;
import com.reinaldoarrosi.android.querybuilder.sqlite.projection.AggregateProjection.Type;

public abstract class Projection {
	// Simple column
	public static ColumnProjection column(String column) {
		return new ColumnProjection(null, column);
	}
	
	public static ColumnProjection column(String table, String column) {
		return new ColumnProjection(table, column);
	}
	

	// Constant
	public static ConstantProjection constant(Object constant) {
		return new ConstantProjection(constant);
	}
	
	
	// Aggregate functions
	public static AggregateProjection min(String column) {
		return min(column(column));
	}
	
	public static AggregateProjection max(String column) {
		return max(column(column));
	}
	
	public static AggregateProjection sum(String column) {
		return sum(column(column));
	}
	
	public static AggregateProjection avg(String column) {
		return avg(column(column));
	}
	
	public static AggregateProjection count(String column) {
		return count(column(column));
	}
	
	public static AggregateProjection countRows() {
		return count(column("*"));
	}
	
	public static AggregateProjection min(Projection projection) {
		return new AggregateProjection(projection, Type.MIN);
	}
	
	public static AggregateProjection max(Projection projection) {
		return new AggregateProjection(projection, Type.MAX);
	}
	
	public static AggregateProjection sum(Projection projection) {
		return new AggregateProjection(projection, Type.SUM);
	}
	
	public static AggregateProjection avg(Projection projection) {
		return new AggregateProjection(projection, Type.AVG);
	}
	
	public static AggregateProjection count(Projection projection) {
		return new AggregateProjection(projection, Type.COUNT);
	}
	
	
	// SubQuery
	public static SubQueryProjection subQuery(QueryBuilder subQuery) {
		return new SubQueryProjection(subQuery);
	}
	
	public Projection as(String alias) {
		return new AliasedProjection(this, alias);
	}
	
	public Projection castAsDate() {
		return new CastDateProjection(this);
	}
	
	public Projection castAsDateTime() {
		return new CastDateTimeProjection(this);
	}
	
	public Projection castAsReal() {
		return new CastRealProjection(this);
	}
	
	public Projection castAsInt() {
		return new CastIntProjection(this);
	}
	
	public Projection castAsString() {
		return new CastStringProjection(this);
	}
	
	public abstract String build();
	
	public abstract List<Object> buildParameters();
}
