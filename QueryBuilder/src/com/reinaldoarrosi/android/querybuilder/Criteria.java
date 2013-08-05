package com.reinaldoarrosi.android.querybuilder;

import java.util.ArrayList;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class Criteria extends SimpleCriteria {
	private ArrayList<SimpleCriteria> _criterias;

	private Criteria() {
		super();
		_criterias = new ArrayList<SimpleCriteria>();
	}
	
	public static Criteria create(String criteria, String[] parameters) {
		SimpleCriteria criteriaObj = new SimpleCriteria(criteria, Type.AND, parameters);
		return create(criteriaObj);
	}
	
	public static Criteria create(SimpleCriteria criteria) {
		Criteria cg = new Criteria();
		cg._criterias.add(criteria);

		return cg;
	}
	
	
	// isNull
	public static Criteria isNull(String column) {
		return create(column + " IS NULL", null);
	}

	public static Criteria notIsNull(String column) {
		return create(column + " IS NOT NULL", null);
	}
	
	public static Criteria isNull(QueryBuilder subQuery) {		
		return create("(" + subQuery.buildQuery() + ") IS NULL", subQuery.buildParameters());
	}

	public static Criteria notIsNull(QueryBuilder subQuery) {	
		return create("(" + subQuery.buildQuery() + ") IS NOT NULL", subQuery.buildParameters());
	}

	
	// String
	public static Criteria equals(String column, String value) {
		return create(column + " = ?", new String[] { value });
	}

	public static Criteria notEquals(String column, String value) {
		return create(column + " <> ?", new String[] { value });
	}

	public static Criteria greaterThan(String column, String value) {
		return create(column + " > ?", new String[] { value });
	}

	public static Criteria lesserThan(String column, String value) {
		return create(column + " < ?", new String[] { value });
	}

	public static Criteria greaterThanOrEqual(String column, String value) {
		return create(column + " >= ?", new String[] { value });
	}

	public static Criteria lesserThanOrEqual(String column, String value) {
		return create(column + " <= ?", new String[] { value });
	}

	public static Criteria startsWith(String column, String value) {
		return create(column + " LIKE ?", new String[] { value + "%" });
	}
	
	public static Criteria notStartsWith(String column, String value) {
		return create(column + " NOT LIKE ?", new String[] { value + "%" });
	}

	public static Criteria endsWith(String column, String value) {
		return create(column + " LIKE ?", new String[] { "%" + value });
	}
	
	public static Criteria notEndsWith(String column, String value) {
		return create(column + " NOT LIKE ?", new String[] { "%" + value });
	}

	public static Criteria contains(String column, String value) {
		return create(column + " LIKE ?", new String[] { "%" + value + "%" });
	}

	public static Criteria notContains(String column, String value) {
		return create(column + " NOT LIKE ?", new String[] { "%" + value + "%" });
	}

	public static Criteria between(String column, String valueMin, String valueMax) {
		return create(column + " BETWEEN ? AND ?", new String[] { valueMin, valueMax });
	}

	public static Criteria valueBetween(String value, String columnMin, String columnMax) {
		return create(" ? BETWEEN " + columnMin + " AND " + columnMax, new String[] { value });
	}
	
	
	// SubQuery + String
	public static Criteria equals(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value;
		
		return create("(" + subQuery.buildQuery() + ") = ?", params);
	}

	public static Criteria notEquals(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value;
		
		return create("(" + subQuery.buildQuery() + ") <> ?", params);
	}

	public static Criteria greaterThan(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value;
		
		return create("(" + subQuery.buildQuery() + ") > ?", params);
	}

	public static Criteria lesserThan(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value;
		
		return create("(" + subQuery.buildQuery() + ") < ?", params);
	}

	public static Criteria greaterThanOrEqual(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value;
		
		return create("(" + subQuery.buildQuery() + ") >= ?", params);
	}

	public static Criteria lesserThanOrEqual(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value;
		
		return create("(" + subQuery.buildQuery() + ") <= ?", params);
	}

	public static Criteria startsWith(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value + "%";
		
		return create("(" + subQuery.buildQuery() + ") LIKE ?", params);
	}
	
	public static Criteria notStartsWith(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value + "%";
		
		return create("(" + subQuery.buildQuery() + ") NOT LIKE ?", params);
	}

	public static Criteria endsWith(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = "%" + value;
		
		return create("(" + subQuery.buildQuery() + ") LIKE ?", params);
	}
	
	public static Criteria notEndsWith(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = "%" + value;
		
		return create("(" + subQuery.buildQuery() + ") NOT LIKE ?", params);
	}

	public static Criteria contains(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParametersDefaultEmpty();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = "%" + value + "%";
		
		return create("(" + subQuery.buildQuery() + ") LIKE ?", params);
	}

	public static Criteria notContains(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParametersDefaultEmpty();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = "%" + value + "%";
		
		return create("(" + subQuery.buildQuery() + ") NOT LIKE ?", params);
	}

	public static Criteria between(QueryBuilder subQuery, String valueMin, String valueMax) {
		String[] subQueryParams = subQuery.buildParametersDefaultEmpty();
		String[] params = new String[subQueryParams.length + 2];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 2] = valueMin;
		params[params.length - 1] = valueMax;
		
		return create("(" + subQuery.buildQuery() + ") BETWEEN ? AND ?", params);
	}

	
	// Integer
	public static Criteria equals(String column, int value) {
		return create(column + " = ?", new String[] { String.valueOf(value) });
	}

	public static Criteria notEquals(String column, int value) {
		return create(column + " <> ?", new String[] { String.valueOf(value) });
	}

	public static Criteria greaterThan(String column, int value) {
		return create("CAST(" + column + " AS INTEGER) > ?", new String[] { String.valueOf(value) });
	}

	public static Criteria lesserThan(String column, int value) {
		return create("CAST(" + column + " AS INTEGER) < ?", new String[] { String.valueOf(value) });
	}

	public static Criteria greaterThanOrEqual(String column, int value) {
		return create("CAST(" + column + " AS INTEGER) >= ?", new String[] { String.valueOf(value) });
	}

	public static Criteria lesserThanOrEqual(String column, int value) {
		return create("CAST(" + column + " AS INTEGER) <= ?", new String[] { String.valueOf(value) });
	}

	public static Criteria between(String column, int valueMin, int valueMax) {
		return create(column + " BETWEEN ? AND ?", new String[] { String.valueOf(valueMin), String.valueOf(valueMax) });
	}

	public static Criteria valueBetween(int value, String columnMin, String columnMax) {
		return create(" ? BETWEEN " + columnMin + " AND " + columnMax, new String[] { String.valueOf(value) });
	}

	
	// SubQuery + Integer
	public static Criteria equals(QueryBuilder subQuery, int value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = String.valueOf(value);
		
		return create("(" + subQuery.buildQuery() + ") = ?", params);
	}

	public static Criteria notEquals(QueryBuilder subQuery, int value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = String.valueOf(value);
		
		return create("(" + subQuery.buildQuery() + ") <> ?", params);
	}

	public static Criteria greaterThan(QueryBuilder subQuery, int value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = String.valueOf(value);
		
		return create("(" + subQuery.buildQuery() + ") > ?", params);
	}

	public static Criteria lesserThan(QueryBuilder subQuery, int value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = String.valueOf(value);
		
		return create("(" + subQuery.buildQuery() + ") < ?", params);
	}

	public static Criteria greaterThanOrEqual(QueryBuilder subQuery, int value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = String.valueOf(value);
		
		return create("(" + subQuery.buildQuery() + ") >= ?", params);
	}

	public static Criteria lesserThanOrEqual(QueryBuilder subQuery, int value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = String.valueOf(value);
		
		return create("(" + subQuery.buildQuery() + ") <= ?", params);
	}

	public static Criteria between(QueryBuilder subQuery, int valueMin, int valueMax) {
		String[] subQueryParams = subQuery.buildParametersDefaultEmpty();
		String[] params = new String[subQueryParams.length + 2];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 2] = String.valueOf(valueMin);
		params[params.length - 1] = String.valueOf(valueMax);
		
		return create("(" + subQuery.buildQuery() + ") BETWEEN ? AND ?", params);
	}
	
	
	// Float
	public static Criteria equals(String column, float value) {
		return create(column + " = ?", new String[] { Utils.toString(value) });
	}

	public static Criteria notEquals(String column, float value) {
		return create(column + " <> ?", new String[] { Utils.toString(value) });
	}

	public static Criteria greaterThan(String column, float value) {
		return create("CAST(" + column + " AS REAL) > ?", new String[] { Utils.toString(value) });
	}

	public static Criteria lesserThan(String column, float value) {
		return create("CAST(" + column + " AS REAL) < ?", new String[] { Utils.toString(value) });
	}

	public static Criteria greaterThanOrEqual(String column, float value) {
		return create("CAST(" + column + " AS REAL) >= ?", new String[] { Utils.toString(value) });
	}

	public static Criteria lesserThanOrEqual(String column, float value) {
		return create("CAST(" + column + " AS REAL) <= ?", new String[] { Utils.toString(value) });
	}

	public static Criteria between(String column, float valueMin, float valueMax) {
		return create(column + " BETWEEN ? AND ?", new String[] { Utils.toString(valueMin), Utils.toString(valueMax) });
	}

	public static Criteria valueBetween(float value, String columnMin, String columnMax) {
		return create(" ? BETWEEN " + columnMin + " AND " + columnMax, new String[] { Utils.toString(value) });
	}

	
	// SubQuery + Float
	public static Criteria equals(QueryBuilder subQuery, float value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.toString(value);
		
		return create("(" + subQuery.buildQuery() + ") = ?", params);
	}

	public static Criteria notEquals(QueryBuilder subQuery, float value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.toString(value);
		
		return create("(" + subQuery.buildQuery() + ") <> ?", params);
	}

	public static Criteria greaterThan(QueryBuilder subQuery, float value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.toString(value);
		
		return create("(" + subQuery.buildQuery() + ") > ?", params);
	}

	public static Criteria lesserThan(QueryBuilder subQuery, float value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.toString(value);
		
		return create("(" + subQuery.buildQuery() + ") < ?", params);
	}

	public static Criteria greaterThanOrEqual(QueryBuilder subQuery, float value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.toString(value);
		
		return create("(" + subQuery.buildQuery() + ") >= ?", params);
	}

	public static Criteria lesserThanOrEqual(QueryBuilder subQuery, float value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.toString(value);
		
		return create("(" + subQuery.buildQuery() + ") <= ?", params);
	}

	public static Criteria between(QueryBuilder subQuery, float valueMin, float valueMax) {
		String[] subQueryParams = subQuery.buildParametersDefaultEmpty();
		String[] params = new String[subQueryParams.length + 2];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 2] = Utils.toString(valueMin);
		params[params.length - 1] = Utils.toString(valueMax);
		
		return create("(" + subQuery.buildQuery() + ") BETWEEN ? AND ?", params);
	}
	
	
	// Double
	public static Criteria equals(String column, double value) {
		return create(column + " = ?", new String[] { Utils.toString(value) });
	}

	public static Criteria notEquals(String column, double value) {
		return create(column + " <> ?", new String[] { Utils.toString(value) });
	}

	public static Criteria greaterThan(String column, double value) {
		return create("CAST(" + column + " AS REAL) > ?", new String[] { Utils.toString(value) });
	}

	public static Criteria lesserThan(String column, double value) {
		return create("CAST(" + column + " AS REAL) < ?", new String[] { Utils.toString(value) });
	}

	public static Criteria greaterThanOrEqual(String column, double value) {
		return create("CAST(" + column + " AS REAL) >= ?", new String[] { Utils.toString(value) });
	}

	public static Criteria lesserThanOrEqual(String column, double value) {
		return create("CAST(" + column + " AS REAL) <= ?", new String[] { Utils.toString(value) });
	}

	public static Criteria between(String column, double valueMin, double valueMax) {
		return create(column + " BETWEEN ? AND ?", new String[] { Utils.toString(valueMin), Utils.toString(valueMax) });
	}

	public static Criteria valueBetween(double value, String columnMin, String columnMax) {
		return create(" ? BETWEEN " + columnMin + " AND " + columnMax, new String[] { Utils.toString(value) });
	}
	
	
	// SubQuery + Double
	public static Criteria equals(QueryBuilder subQuery, double value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.toString(value);
		
		return create("(" + subQuery.buildQuery() + ") = ?", params);
	}

	public static Criteria notEquals(QueryBuilder subQuery, double value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.toString(value);
		
		return create("(" + subQuery.buildQuery() + ") <> ?", params);
	}

	public static Criteria greaterThan(QueryBuilder subQuery, double value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.toString(value);
		
		return create("(" + subQuery.buildQuery() + ") > ?", params);
	}

	public static Criteria lesserThan(QueryBuilder subQuery, double value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.toString(value);
		
		return create("(" + subQuery.buildQuery() + ") < ?", params);
	}

	public static Criteria greaterThanOrEqual(QueryBuilder subQuery, double value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.toString(value);
		
		return create("(" + subQuery.buildQuery() + ") >= ?", params);
	}

	public static Criteria lesserThanOrEqual(QueryBuilder subQuery, double value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.toString(value);
		
		return create("(" + subQuery.buildQuery() + ") <= ?", params);
	}

	public static Criteria between(QueryBuilder subQuery, double valueMin, double valueMax) {
		String[] subQueryParams = subQuery.buildParametersDefaultEmpty();
		String[] params = new String[subQueryParams.length + 2];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 2] = Utils.toString(valueMin);
		params[params.length - 1] = Utils.toString(valueMax);
		
		return create("(" + subQuery.buildQuery() + ") BETWEEN ? AND ?", params);
	}

	
	// LocalDate
	public static Criteria equals(String column, LocalDate value) {
		return create("DATE(" + column + ") = ?", new String[] { Utils.dateToString(value) });
	}

	public static Criteria notEquals(String column, LocalDate value) {
		return create("DATE(" + column + ") <> ?", new String[] { Utils.dateToString(value) });
	}

	public static Criteria greaterThan(String column, LocalDate value) {
		return create("DATE(" + column + ") > ?", new String[] { Utils.dateToString(value) });
	}

	public static Criteria lesserThan(String column, LocalDate value) {
		return create("DATE(" + column + ") < ?", new String[] { Utils.dateToString(value) });
	}

	public static Criteria greaterThanOrEqual(String column, LocalDate value) {
		return create("DATE(" + column + ") >= ?", new String[] { Utils.dateToString(value) });
	}

	public static Criteria lesserThanOrEqual(String column, LocalDate value) {
		return create("DATE(" + column + ") <= ?", new String[] { Utils.dateToString(value) });
	}

	public static Criteria between(String column, LocalDate valueMin, LocalDate valueMax) {
		return create("DATE(" + column + ") BETWEEN ? AND ?", new String[] { Utils.dateToString(valueMin), Utils.dateToString(valueMax) });
	}

	public static Criteria valueBetween(LocalDate value, String columnMin, String columnMax) {
		return create(" ? BETWEEN DATE(" + columnMin + ") AND DATE(" + columnMax + ")", new String[] { Utils.dateToString(value) });
	}
	
	
	// SubQuery + LocalDate
	public static Criteria equals(QueryBuilder subQuery, LocalDate value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.dateToString(value);
		
		return create("(" + subQuery.buildQuery() + ") = ?", params);
	}

	public static Criteria notEquals(QueryBuilder subQuery, LocalDate value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.dateToString(value);
		
		return create("(" + subQuery.buildQuery() + ") <> ?", params);
	}

	public static Criteria greaterThan(QueryBuilder subQuery, LocalDate value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.dateToString(value);
		
		return create("(" + subQuery.buildQuery() + ") > ?", params);
	}

	public static Criteria lesserThan(QueryBuilder subQuery, LocalDate value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.dateToString(value);
		
		return create("(" + subQuery.buildQuery() + ") < ?", params);
	}

	public static Criteria greaterThanOrEqual(QueryBuilder subQuery, LocalDate value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.dateToString(value);
		
		return create("(" + subQuery.buildQuery() + ") >= ?", params);
	}

	public static Criteria lesserThanOrEqual(QueryBuilder subQuery, LocalDate value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.dateToString(value);
		
		return create("(" + subQuery.buildQuery() + ") <= ?", params);
	}

	public static Criteria between(QueryBuilder subQuery, LocalDate valueMin, LocalDate valueMax) {
		String[] subQueryParams = subQuery.buildParametersDefaultEmpty();
		String[] params = new String[subQueryParams.length + 2];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 2] = Utils.dateToString(valueMin);
		params[params.length - 1] = Utils.dateToString(valueMax);
		
		return create("(" + subQuery.buildQuery() + ") BETWEEN ? AND ?", params);
	}

	
	// LocalDateTime
	public static Criteria equals(String column, LocalDateTime value) {
		return create("DATETIME(" + column + ") = ?", new String[] { Utils.dateToString(value) });
	}

	public static Criteria notEquals(String column, LocalDateTime value) {
		return create("DATETIME(" + column + ") <> ?", new String[] { Utils.dateToString(value) });
	}

	public static Criteria greaterThan(String column, LocalDateTime value) {
		return create("DATETIME(" + column + ") > ?", new String[] { Utils.dateToString(value) });
	}

	public static Criteria lesserThan(String column, LocalDateTime value) {
		return create("DATETIME(" + column + ") < ?", new String[] { Utils.dateToString(value) });
	}

	public static Criteria greaterThanOrEqual(String column, LocalDateTime value) {
		return create("DATETIME(" + column + ") >= ?", new String[] { Utils.dateToString(value) });
	}

	public static Criteria lesserThanOrEqual(String column, LocalDateTime value) {
		return create("DATETIME(" + column + ") <= ?", new String[] { Utils.dateToString(value) });
	}

	public static Criteria between(String column, LocalDateTime valueMin, LocalDateTime valueMax) {
		return create("DATETIME(" + column + ") BETWEEN ? AND ?", new String[] { Utils.dateToString(valueMin), Utils.dateToString(valueMax) });
	}

	public static Criteria valueBetween(LocalDateTime value, String columnMin, String columnMax) {
		return create(" ? BETWEEN " + columnMin + " AND " + columnMax, new String[] { Utils.dateToString(value) });
	}
	
	
	// SubQuery + LocalDateTime
	public static Criteria equals(QueryBuilder subQuery, LocalDateTime value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.dateToString(value);
		
		return create("(" + subQuery.buildQuery() + ") = ?", params);
	}

	public static Criteria notEquals(QueryBuilder subQuery, LocalDateTime value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.dateToString(value);
		
		return create("(" + subQuery.buildQuery() + ") <> ?", params);
	}

	public static Criteria greaterThan(QueryBuilder subQuery, LocalDateTime value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.dateToString(value);
		
		return create("(" + subQuery.buildQuery() + ") > ?", params);
	}

	public static Criteria lesserThan(QueryBuilder subQuery, LocalDateTime value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.dateToString(value);
		
		return create("(" + subQuery.buildQuery() + ") < ?", params);
	}

	public static Criteria greaterThanOrEqual(QueryBuilder subQuery, LocalDateTime value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.dateToString(value);
		
		return create("(" + subQuery.buildQuery() + ") >= ?", params);
	}

	public static Criteria lesserThanOrEqual(QueryBuilder subQuery, LocalDateTime value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = Utils.dateToString(value);
		
		return create("(" + subQuery.buildQuery() + ") <= ?", params);
	}

	public static Criteria between(QueryBuilder subQuery, LocalDateTime valueMin, LocalDateTime valueMax) {
		String[] subQueryParams = subQuery.buildParametersDefaultEmpty();
		String[] params = new String[subQueryParams.length + 2];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 2] = Utils.dateToString(valueMin);
		params[params.length - 1] = Utils.dateToString(valueMax);
		
		return create("(" + subQuery.buildQuery() + ") BETWEEN ? AND ?", params);
	}

	
	// DATE CAST
	public static Criteria equalsAsDate(String column, String value) {
		return create("DATE(" + column + ") = ?", new String[] { value });
	}

	public static Criteria notEqualsAsDate(String column, String value) {
		return create("DATE(" + column + ") <> ?", new String[] { value });
	}

	public static Criteria greaterThanAsDate(String column, String value) {
		return create("DATE(" + column + ") > ?", new String[] { value });
	}

	public static Criteria lesserThanAsDate(String column, String value) {
		return create("DATE(" + column + ") < ?", new String[] { value });
	}

	public static Criteria greaterThanOrEqualAsDate(String column, String value) {
		return create("DATE(" + column + ") >= ?", new String[] { value });
	}

	public static Criteria lesserThanOrEqualAsDate(String column, String value) {
		return create("DATE(" + column + ") <= ?", new String[] { value });
	}

	public static Criteria betweenAsDate(String column, String valueMin, String valueMax) {
		return create("DATE(" + column + ") BETWEEN ? AND ?", new String[] { valueMin, valueMax });
	}

	public static Criteria valueBetweenAsDate(String value, String columnMin, String columnMax) {
		return create(" ? BETWEEN DATE(" + columnMin + ") AND DATE(" + columnMax + ")", new String[] { value });
	}
	
	
	// SubQuery + DATE CAST
	public static Criteria equalsAsDate(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value;
		
		return create("DATE((" + subQuery.buildQuery() + ")) = ?", params);
	}

	public static Criteria notEqualsAsDate(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value;
		
		return create("DATE((" + subQuery.buildQuery() + ")) <> ?", params);
	}

	public static Criteria greaterThanAsDate(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value;
		
		return create("DATE((" + subQuery.buildQuery() + ")) > ?", params);
	}

	public static Criteria lesserThanAsDate(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value;
		
		return create("DATE((" + subQuery.buildQuery() + ")) < ?", params);		
	}

	public static Criteria greaterThanOrEqualAsDate(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value;
		
		return create("DATE((" + subQuery.buildQuery() + ")) >= ?", params);		
	}

	public static Criteria lesserThanOrEqualAsDate(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value;
		
		return create("DATE((" + subQuery.buildQuery() + ")) <= ?", params);		
	}

	public static Criteria betweenAsDate(QueryBuilder subQuery, String valueMin, String valueMax) {
		String[] subQueryParams = subQuery.buildParametersDefaultEmpty();
		String[] params = new String[subQueryParams.length + 2];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 2] = valueMin;
		params[params.length - 1] = valueMax;

		return create("DATE((" + subQuery.buildQuery() + ")) BETWEEN ? AND ?", params);
	}
	
	
	// DATETIME CAST
	public static Criteria equalsAsDateTime(String column, String value) {
		return create("DATETIME(" + column + ") = ?", new String[] { value });
	}

	public static Criteria notEqualsAsDateTime(String column, String value) {
		return create("DATETIME(" + column + ") <> ?", new String[] { value });
	}

	public static Criteria greaterThanAsDateTime(String column, String value) {
		return create("DATETIME(" + column + ") > ?", new String[] { value });
	}

	public static Criteria lesserThanAsDateTime(String column, String value) {
		return create("DATETIME(" + column + ") < ?", new String[] { value });
	}

	public static Criteria greaterThanOrEqualAsDateTime(String column, String value) {
		return create("DATETIME(" + column + ") >= ?", new String[] { value });
	}

	public static Criteria lesserThanOrEqualAsDateTime(String column, String value) {
		return create("DATETIME(" + column + ") <= ?", new String[] { value });
	}

	public static Criteria betweenAsDateTime(String column, String valueMin, String valueMax) {
		return create("DATETIME(" + column + ") BETWEEN ? AND ?", new String[] { valueMin, valueMax });
	}

	public static Criteria valueBetweenAsDateTime(String value, String columnMin, String columnMax) {
		return create(" ? BETWEEN DATETIME(" + columnMin + ") AND DATETIME(" + columnMax + ")", new String[] { value });
	}
	
	
	// SubQuery + DATETIME CAST
	public static Criteria equalsAsDateTime(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value;
		
		return create("DATETIME((" + subQuery.buildQuery() + ")) = ?", params);
	}

	public static Criteria notEqualsAsDateTime(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value;
		
		return create("DATETIME((" + subQuery.buildQuery() + ")) <> ?", params);
	}

	public static Criteria greaterThanAsDateTime(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value;
		
		return create("DATETIME((" + subQuery.buildQuery() + ")) > ?", params);
	}

	public static Criteria lesserThanAsDateTime(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value;
		
		return create("DATETIME((" + subQuery.buildQuery() + ")) < ?", params);		
	}

	public static Criteria greaterThanOrEqualAsDateTime(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value;
		
		return create("DATETIME((" + subQuery.buildQuery() + ")) >= ?", params);		
	}

	public static Criteria lesserThanOrEqualAsDateTime(QueryBuilder subQuery, String value) {
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length + 1];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 1] = value;
		
		return create("DATETIME((" + subQuery.buildQuery() + ")) <= ?", params);		
	}

	public static Criteria betweenAsDateTime(QueryBuilder subQuery, String valueMin, String valueMax) {
		String[] subQueryParams = subQuery.buildParametersDefaultEmpty();
		String[] params = new String[subQueryParams.length + 2];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		params[params.length - 2] = valueMin;
		params[params.length - 1] = valueMax;

		return create("DATETIME((" + subQuery.buildQuery() + ")) BETWEEN ? AND ?", params);
	}
	
	
	// EQUALS between column
	public static Criteria equalsColumn(String leftColumn, String rightColumn) {
		return create(leftColumn + " = " + rightColumn, null);
	}
	
	public static Criteria equalsColumn(QueryBuilder subQuery, String rightColumn) {
		return create("(" + subQuery.buildQuery() + ") = " + rightColumn, subQuery.buildParameters());
	}
	
	public static Criteria equalsColumn(String leftColumn, QueryBuilder subQuery) {
		return create(leftColumn + " = (" + subQuery.buildQuery() + ")", subQuery.buildParameters());
	}
	
	public static Criteria equalsColumn(QueryBuilder leftSubQuery, QueryBuilder rightSubQuery) {
		String[] leftSubQueryParams = leftSubQuery.buildParameters();
		String[] rightSubQueryParams = rightSubQuery.buildParameters();
		String[] params = new String[leftSubQueryParams.length + rightSubQueryParams.length];
		
		System.arraycopy(leftSubQueryParams, 0, params, 0, leftSubQueryParams.length);
		System.arraycopy(rightSubQueryParams, 0, params, leftSubQueryParams.length, rightSubQueryParams.length);
		
		return create("(" + leftSubQuery.buildQuery() + ") = (" + rightSubQuery.buildQuery() + ")", params);
	}

	
	// NOT EQUALS between column
	public static Criteria notEqualsColumn(String leftColumn, String rightColumn) {
		return create(leftColumn + " <> " + rightColumn, null);
	}
	
	public static Criteria notEqualsColumn(QueryBuilder subQuery, String rightColumn) {
		return create("(" + subQuery.buildQuery() + ") <> " + rightColumn, subQuery.buildParameters());
	}
	
	public static Criteria notEqualsColumn(String leftColumn, QueryBuilder subQuery) {
		return create(leftColumn + " <> (" + subQuery.buildQuery() + ")", subQuery.buildParameters());
	}
	
	public static Criteria notEqualsColumn(QueryBuilder leftSubQuery, QueryBuilder rightSubQuery) {
		String[] leftSubQueryParams = leftSubQuery.buildParameters();
		String[] rightSubQueryParams = rightSubQuery.buildParameters();
		String[] params = new String[leftSubQueryParams.length + rightSubQueryParams.length];
		
		System.arraycopy(leftSubQueryParams, 0, params, 0, leftSubQueryParams.length);
		System.arraycopy(rightSubQueryParams, 0, params, leftSubQueryParams.length, rightSubQueryParams.length);
		
		return create("(" + leftSubQuery.buildQuery() + ") <> (" + rightSubQuery.buildQuery() + ")", params);
	}
	
	
	// LESSER THAN between column
	public static Criteria lesserThanColumn(String leftColumn, String rightColumn) {
		return create(leftColumn + " < " + rightColumn, null);
	}
	
	public static Criteria lesserThanColumn(QueryBuilder subQuery, String rightColumn) {
		return create("(" + subQuery.buildQuery() + ") < " + rightColumn, subQuery.buildParameters());
	}
	
	public static Criteria lesserThanColumn(String leftColumn, QueryBuilder subQuery) {
		return create(leftColumn + " < (" + subQuery.buildQuery() + ")", subQuery.buildParameters());
	}
	
	public static Criteria lesserThanColumn(QueryBuilder leftSubQuery, QueryBuilder rightSubQuery) {
		String[] leftSubQueryParams = leftSubQuery.buildParameters();
		String[] rightSubQueryParams = rightSubQuery.buildParameters();
		String[] params = new String[leftSubQueryParams.length + rightSubQueryParams.length];
		
		System.arraycopy(leftSubQueryParams, 0, params, 0, leftSubQueryParams.length);
		System.arraycopy(rightSubQueryParams, 0, params, leftSubQueryParams.length, rightSubQueryParams.length);
		
		return create("(" + leftSubQuery.buildQuery() + ") < (" + rightSubQuery.buildQuery() + ")", params);
	}
	
	
	// LESSER THAN OR EQUALS between column
	public static Criteria lesserThanOrEqualsColumn(String leftColumn, String rightColumn) {
		return create(leftColumn + " <= " + rightColumn, null);
	}
	
	public static Criteria lesserThanOrEqualsColumn(QueryBuilder subQuery, String rightColumn) {
		return create("(" + subQuery.buildQuery() + ") <= " + rightColumn, subQuery.buildParameters());
	}
	
	public static Criteria lesserThanOrEqualsColumn(String leftColumn, QueryBuilder subQuery) {
		return create(leftColumn + " <= (" + subQuery.buildQuery() + ")", subQuery.buildParameters());
	}
	
	public static Criteria lesserThanOrEqualsColumn(QueryBuilder leftSubQuery, QueryBuilder rightSubQuery) {
		String[] leftSubQueryParams = leftSubQuery.buildParameters();
		String[] rightSubQueryParams = rightSubQuery.buildParameters();
		String[] params = new String[leftSubQueryParams.length + rightSubQueryParams.length];
		
		System.arraycopy(leftSubQueryParams, 0, params, 0, leftSubQueryParams.length);
		System.arraycopy(rightSubQueryParams, 0, params, leftSubQueryParams.length, rightSubQueryParams.length);
		
		return create("(" + leftSubQuery.buildQuery() + ") <= (" + rightSubQuery.buildQuery() + ")", params);
	}
	
	
	// GREATER THAN between column
	public static Criteria greaterThanColumn(String leftColumn, String rightColumn) {
		return create(leftColumn + " > " + rightColumn, null);
	}
	
	public static Criteria greaterThanColumn(QueryBuilder subQuery, String rightColumn) {
		return create("(" + subQuery.buildQuery() + ") > " + rightColumn, subQuery.buildParameters());
	}
	
	public static Criteria greaterThanColumn(String leftColumn, QueryBuilder subQuery) {
		return create(leftColumn + " > (" + subQuery.buildQuery() + ")", subQuery.buildParameters());
	}
	
	public static Criteria greaterThanColumn(QueryBuilder leftSubQuery, QueryBuilder rightSubQuery) {
		String[] leftSubQueryParams = leftSubQuery.buildParameters();
		String[] rightSubQueryParams = rightSubQuery.buildParameters();
		String[] params = new String[leftSubQueryParams.length + rightSubQueryParams.length];
		
		System.arraycopy(leftSubQueryParams, 0, params, 0, leftSubQueryParams.length);
		System.arraycopy(rightSubQueryParams, 0, params, leftSubQueryParams.length, rightSubQueryParams.length);
		
		return create("(" + leftSubQuery.buildQuery() + ") > (" + rightSubQuery.buildQuery() + ")", params);
	}
	
	
	// GREATER THAN OR EQUALS between column
	public static Criteria greaterThanOrEqualsColumn(String leftColumn, String rightColumn) {
		return create(leftColumn + " >= " + rightColumn, null);
	}
	
	public static Criteria greaterThanOrEqualsColumn(QueryBuilder subQuery, String rightColumn) {
		return create("(" + subQuery.buildQuery() + ") >= " + rightColumn, subQuery.buildParameters());
	}
	
	public static Criteria greaterThanOrEqualsColumn(String leftColumn, QueryBuilder subQuery) {
		return create(leftColumn + " >= (" + subQuery.buildQuery() + ")", subQuery.buildParameters());
	}
	
	public static Criteria greaterThanOrEqualsColumn(QueryBuilder leftSubQuery, QueryBuilder rightSubQuery) {
		String[] leftSubQueryParams = leftSubQuery.buildParameters();
		String[] rightSubQueryParams = rightSubQuery.buildParameters();
		String[] params = new String[leftSubQueryParams.length + rightSubQueryParams.length];
		
		System.arraycopy(leftSubQueryParams, 0, params, 0, leftSubQueryParams.length);
		System.arraycopy(rightSubQueryParams, 0, params, leftSubQueryParams.length, rightSubQueryParams.length);
		
		return create("(" + leftSubQuery.buildQuery() + ") >= (" + rightSubQuery.buildQuery() + ")", params);
	}
	
	
	// EQUALS between column + DATE CAST
	public static Criteria equalsColumnAsDate(String leftColumn, String rightColumn) {
		return create("DATE(" + leftColumn + ") = DATE(" + rightColumn + ")", null);
	}
	
	public static Criteria equalsColumnAsDate(QueryBuilder subQuery, String rightColumn) {
		return create("(" + subQuery.buildQuery() + ") = DATE(" + rightColumn + ")", subQuery.buildParameters());
	}
	
	public static Criteria equalsColumnAsDate(String leftColumn, QueryBuilder subQuery) {
		return create("DATE(" + leftColumn + ") = (" + subQuery.buildQuery() + ")", subQuery.buildParameters());
	}


	// NOT EQUALS between column + DATE CAST
	public static Criteria notEqualsColumnAsDate(String leftColumn, String rightColumn) {
		return create("DATE(" + leftColumn + ") <> DATE(" + rightColumn + ")", null);
	}
	
	public static Criteria notEqualsColumnAsDate(QueryBuilder subQuery, String rightColumn) {
		return create("(" + subQuery.buildQuery() + ") <> DATE(" + rightColumn + ")", subQuery.buildParameters());
	}
	
	public static Criteria notEqualsColumnAsDate(String leftColumn, QueryBuilder subQuery) {
		return create("DATE(" + leftColumn + ") <> (" + subQuery.buildQuery() + ")", subQuery.buildParameters());
	}
	
	
	// LESSER THAN between column + DATE CAST
	public static Criteria lesserThanColumnAsDate(String leftColumn, String rightColumn) {
		return create("DATE(" + leftColumn + ") < DATE(" + rightColumn + ")", null);
	}
	
	public static Criteria lesserThanColumnAsDate(QueryBuilder subQuery, String rightColumn) {
		return create("(" + subQuery.buildQuery() + ") < DATE(" + rightColumn + ")", subQuery.buildParameters());
	}
	
	public static Criteria lesserThanColumnAsDate(String leftColumn, QueryBuilder subQuery) {
		return create("DATE(" + leftColumn + ") < (" + subQuery.buildQuery() + ")", subQuery.buildParameters());
	}
	
	
	// LESSER THAN OR EQUALS between column + DATE CAST
	public static Criteria lesserThanOrEqualsColumnAsDate(String leftColumn, String rightColumn) {
		return create("DATE(" + leftColumn + ") <= DATE(" + rightColumn + ")", null);
	}
	
	public static Criteria lesserThanOrEqualsColumnAsDate(QueryBuilder subQuery, String rightColumn) {
		return create("(" + subQuery.buildQuery() + ") <= DATE(" + rightColumn + ")", subQuery.buildParameters());
	}
	
	public static Criteria lesserThanOrEqualsColumnAsDate(String leftColumn, QueryBuilder subQuery) {
		return create("DATE(" + leftColumn + ") <= (" + subQuery.buildQuery() + ")", subQuery.buildParameters());
	}
	
	
	// GREATER THAN  + DATE CAST
	public static Criteria greaterThanColumnAsDate(String leftColumn, String rightColumn) {
		return create("DATE(" + leftColumn + ") > DATE(" + rightColumn + ")", null);
	}
	
	public static Criteria greaterThanColumnAsDate(QueryBuilder subQuery, String rightColumn) {
		return create("(" + subQuery.buildQuery() + ") > DATE(" + rightColumn + ")", subQuery.buildParameters());
	}
	
	public static Criteria greaterThanColumnAsDate(String leftColumn, QueryBuilder subQuery) {
		return create("DATE(" + leftColumn + ") > (" + subQuery.buildQuery() + ")", subQuery.buildParameters());
	}
	
	
	// GREATER THAN OR EQUALS + DATE CAST
	public static Criteria greaterThanOrEqualsColumnAsDate(String leftColumn, String rightColumn) {
		return create("DATE(" + leftColumn + ") >= DATE(" + rightColumn + ")", null);
	}
	
	public static Criteria greaterThanOrEqualsColumnAsDate(QueryBuilder subQuery, String rightColumn) {
		return create("(" + subQuery.buildQuery() + ") >= DATE(" + rightColumn + ")", subQuery.buildParameters());
	}
	
	public static Criteria greaterThanOrEqualsColumnAsDate(String leftColumn, QueryBuilder subQuery) {
		return create("DATE(" + leftColumn + ") >= (" + subQuery.buildQuery() + ")", subQuery.buildParameters());
	}
	
	
	// EQUALS between column + DATETIME CAST
	public static Criteria equalsColumnAsDateTime(String leftColumn, String rightColumn) {
		return create("DATETIME(" + leftColumn + ") = DATETIME(" + rightColumn + ")", null);
	}

	public static Criteria equalsColumnAsDateTime(QueryBuilder subQuery, String rightColumn) {
		return create("(" + subQuery.buildQuery() + ") = DATETIME(" + rightColumn + ")", subQuery.buildParameters());
	}

	public static Criteria equalsColumnAsDateTime(String leftColumn, QueryBuilder subQuery) {
		return create("DATETIME(" + leftColumn + ") = (" + subQuery.buildQuery() + ")", subQuery.buildParameters());
	}


	// NOT EQUALS between column + DATETIME CAST
	public static Criteria notEqualsColumnAsDateTime(String leftColumn, String rightColumn) {
		return create("DATETIME(" + leftColumn + ") <> DATETIME(" + rightColumn + ")", null);
	}

	public static Criteria notEqualsColumnAsDateTime(QueryBuilder subQuery, String rightColumn) {
		return create("(" + subQuery.buildQuery() + ") <> DATETIME(" + rightColumn + ")", subQuery.buildParameters());
	}

	public static Criteria notEqualsColumnAsDateTime(String leftColumn, QueryBuilder subQuery) {
		return create("DATETIME(" + leftColumn + ") <> (" + subQuery.buildQuery() + ")", subQuery.buildParameters());
	}


	// LESSER THAN between column + DATETIME CAST
	public static Criteria lesserThanColumnAsDateTime(String leftColumn, String rightColumn) {
		return create("DATETIME(" + leftColumn + ") < DATETIME(" + rightColumn + ")", null);
	}

	public static Criteria lesserThanColumnAsDateTime(QueryBuilder subQuery, String rightColumn) {
		return create("(" + subQuery.buildQuery() + ") < DATETIME(" + rightColumn + ")", subQuery.buildParameters());
	}

	public static Criteria lesserThanColumnAsDateTime(String leftColumn, QueryBuilder subQuery) {
		return create("DATETIME(" + leftColumn + ") < (" + subQuery.buildQuery() + ")", subQuery.buildParameters());
	}


	// LESSER THAN OR EQUALS between column + DATETIME CAST
	public static Criteria lesserThanOrEqualsColumnAsDateTime(String leftColumn, String rightColumn) {
		return create("DATETIME(" + leftColumn + ") <= DATETIME(" + rightColumn + ")", null);
	}

	public static Criteria lesserThanOrEqualsColumnAsDateTime(QueryBuilder subQuery, String rightColumn) {
		return create("(" + subQuery.buildQuery() + ") <= DATETIME(" + rightColumn + ")", subQuery.buildParameters());
	}

	public static Criteria lesserThanOrEqualsColumnAsDateTime(String leftColumn, QueryBuilder subQuery) {
		return create("DATETIME(" + leftColumn + ") <= (" + subQuery.buildQuery() + ")", subQuery.buildParameters());
	}


	// GREATER THAN between column + DATETIME CAST
	public static Criteria greaterThanColumnAsDateTime(String leftColumn, String rightColumn) {
		return create("DATETIME(" + leftColumn + ") > DATETIME(" + rightColumn + ")", null);
	}

	public static Criteria greaterThanColumnAsDateTime(QueryBuilder subQuery, String rightColumn) {
		return create("(" + subQuery.buildQuery() + ") > DATETIME(" + rightColumn + ")", subQuery.buildParameters());
	}

	public static Criteria greaterThanColumnAsDateTime(String leftColumn, QueryBuilder subQuery) {
		return create("DATETIME(" + leftColumn + ") > (" + subQuery.buildQuery() + ")", subQuery.buildParameters());
	}


	// GREATER THAN OR EQUALS between column + DATETIME CAST
	public static Criteria greaterThanOrEqualsColumnAsDateTime(String leftColumn, String rightColumn) {
		return create("DATETIME(" + leftColumn + ") >= DATETIME(" + rightColumn + ")", null);
	}

	public static Criteria greaterThanOrEqualsColumnAsDateTime(QueryBuilder subQuery, String rightColumn) {
		return create("(" + subQuery.buildQuery() + ") >= DATETIME(" + rightColumn + ")", subQuery.buildParameters());
	}

	public static Criteria greaterThanOrEqualsColumnAsDateTime(String leftColumn, QueryBuilder subQuery) {
		return create("DATETIME(" + leftColumn + ") >= (" + subQuery.buildQuery() + ")", subQuery.buildParameters());
	}
	
	
	// IN
	public static Criteria in(String column, Object[] parameters) {
		return in(column, parameters, false);
	}

	public static Criteria notIn(String column, Object[] parameters) {
		return in(column, parameters, true);
	}
	
	public static Criteria in(String column, Object[] parameters, boolean not) {
		if (parameters == null || parameters.length <= 0)
			return null;

		StringBuilder sb = new StringBuilder();
		sb.append(column);

		if (not)
			sb.append(" NOT");

		sb.append(" IN");
		sb.append("(");

		String[] stringParameters = new String[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			if (i > 0)
				sb.append(", ");

			sb.append("?");
			stringParameters[i] = Utils.toString(parameters[i]);
		}

		sb.append(")");

		SimpleCriteria criteria = new SimpleCriteria(sb.toString(), Type.AND, stringParameters);
		Criteria cg = new Criteria();
		cg._criterias.add(criteria);

		return cg;
	}
	
	
	// IN + SubQuery
	public static Criteria in(QueryBuilder subQuery, Object[] parameters) {
		return in(subQuery, parameters, false);
	}

	public static Criteria notIn(QueryBuilder subQuery, Object[] parameters) {
		return in(subQuery, parameters, true);
	}
	
	public static Criteria in(QueryBuilder subQuery, Object[] parameters, boolean not) {
		if (parameters == null || parameters.length <= 0)
			return null;

		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(subQuery.buildQuery());
		sb.append(")");

		if (not)
			sb.append(" NOT");

		sb.append(" IN");
		sb.append("(");

		String[] stringParameters = new String[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			if (i > 0)
				sb.append(", ");

			sb.append("?");
			stringParameters[i] = Utils.toString(parameters[i]);
		}

		sb.append(")");
		
		String[] subQueryParams = subQuery.buildParameters();
		String[] params = new String[subQueryParams.length];
		
		System.arraycopy(subQueryParams, 0, params, 0, subQueryParams.length);
		System.arraycopy(stringParameters, 0, params, subQueryParams.length, stringParameters.length);

		SimpleCriteria criteria = new SimpleCriteria(sb.toString(), Type.AND, params);
		Criteria cg = new Criteria();
		cg._criterias.add(criteria);

		return cg;
	}

	
	// EXISTS
	public static Criteria exists(QueryBuilder query) {
		return create("EXISTS (" + query.buildQuery() + ")", query.buildParameters());
	}

	public static Criteria notExists(QueryBuilder query) {
		return create("NOT EXISTS (" + query.buildQuery() + ")", query.buildParameters());
	}

	
	// Logical operators
	public static Criteria or(Criteria left, Criteria right) {
		return or(left.build(), right.build(), left.getParameters(), right.getParameters());
	}
	
	public static Criteria and(Criteria left, Criteria right) {
		return and(left.build(), right.build(), left.getParameters(), right.getParameters());
	}
	
	public static Criteria and(String left, String right, String[] parametersLeft, String[] parametersRight) {
		SimpleCriteria leftCriteria = new SimpleCriteria(left, Type.AND, parametersLeft);
		SimpleCriteria rightCriteria = new SimpleCriteria(right, Type.AND, parametersRight);

		Criteria cg = new Criteria();
		cg._criterias.add(leftCriteria);
		cg._criterias.add(rightCriteria);

		return cg;
	}
	
	public static Criteria or(String left, String right, String[] parametersLeft, String[] parametersRight) {
		SimpleCriteria leftCriteria = new SimpleCriteria(left, Type.AND, parametersLeft);
		SimpleCriteria rightCriteria = new SimpleCriteria(right, Type.OR, parametersRight);

		Criteria cg = new Criteria();
		cg._criterias.add(leftCriteria);
		cg._criterias.add(rightCriteria);

		return cg;
	}
	
	
	@Override
	public String build() {
		StringBuilder sb = new StringBuilder();
		boolean firstItem = true;

		sb.append("(");

		for (SimpleCriteria c : _criterias) {
			if (firstItem) {
				firstItem = false;
			} else if (c.getType() == Type.AND) {
				sb.append(" AND ");
			} else if (c.getType() == Type.OR) {
				sb.append(" OR ");
			}

			sb.append(c.build());
		}

		sb.append(")");
		return sb.toString();
	}
	
	@Override
	public String[] getParameters() {
		ArrayList<String> parameters = new ArrayList<String>();
		String[] params;

		for (SimpleCriteria criteria : _criterias) {
			params = criteria.getParameters();

			if (params == null)
				continue;

			for (int i = 0; i < params.length; i++) {
				parameters.add(params[i]);
			}
		}

		String[] ret = new String[parameters.size()];
		parameters.toArray(ret);

		return ret;
	}
	
	public Criteria and(String criteria, String[] parameters) {
		SimpleCriteria c = new SimpleCriteria(criteria, Type.AND, parameters);
		return and(c);
	}
	
	public Criteria or(String criteria, String[] parameters) {
		SimpleCriteria c = new SimpleCriteria(criteria, Type.OR, parameters);
		return or(c);
	}
	
	public Criteria and(SimpleCriteria criteria) {
		criteria.setType(Type.AND);
		_criterias.add(criteria);

		return this;
	}

	public Criteria or(SimpleCriteria criteria) {
		criteria.setType(Type.OR);
		_criterias.add(criteria);

		return this;
	}
}
