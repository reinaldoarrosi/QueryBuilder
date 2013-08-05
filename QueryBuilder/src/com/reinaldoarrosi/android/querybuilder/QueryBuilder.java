package com.reinaldoarrosi.android.querybuilder;

import java.util.ArrayList;
import java.util.List;

import android.database.DatabaseUtils;

public class QueryBuilder {
	private ArrayList<String> projections;
	private ArrayList<String> tables;
	private Criteria criterias;
	private ArrayList<String> groupBy;
	private ArrayList<String> orderBy;
	private int limit = -1;
	private int offset = -1;
	private boolean distinct = false;
	private ArrayList<String> joinParameters = null;
	private ArrayList<String> selectParameters = null;
	private ArrayList<QueryBuilder> unions;
	private boolean isUnionAll = false;

	public QueryBuilder() {
		projections = new ArrayList<String>();
		tables = new ArrayList<String>();
		groupBy = new ArrayList<String>();
		orderBy = new ArrayList<String>();
		unions = new ArrayList<QueryBuilder>();
		joinParameters = new ArrayList<String>();
		selectParameters = new ArrayList<String>();

		criterias = null;
	}

	public QueryBuilder select(QueryBuilder subQuery, String alias) {
		projections.add("(" + subQuery.buildQuery() + ") AS " + alias);

		String[] parameters = subQuery.buildParameters();
		for (int i = 0; i < parameters.length; i++) {
			selectParameters.add(parameters[i]);
		}

		return this;
	}

	public QueryBuilder select(String projection) {
		projections.add(projection);
		return this;
	}

	public QueryBuilder select(String projection, String alias) {
		projections.add(projection + " AS " + alias);
		return this;
	}

	public QueryBuilder select(String projection, String alias, String tableAlias) {
		if (alias == null || alias.length() == 0)
			projections.add(tableAlias + "." + projection);
		else
			projections.add(tableAlias + "." + projection + " AS " + alias);

		return this;
	}

	public QueryBuilder selectDistinct(String projection) {
		distinct = true;
		return select(projection);
	}

	public QueryBuilder selectDistinct(String projection, String alias) {
		distinct = true;
		return select(projection, alias);
	}

	public QueryBuilder selectDistinct(String projection, String alias, String tableAlias) {
		distinct = true;
		return select(projection, alias, tableAlias);
	}

	public QueryBuilder from(String table) {
		tables.add(table);
		return this;
	}

	public QueryBuilder from(String table, String alias) {
		tables.add(table + " AS " + alias);
		return this;
	}

	public QueryBuilder from(Join join) {
		tables.add(join.build());

		String[] parameters = join.getParameters();

		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				joinParameters.add(parameters[i]);
			}
		}

		return this;
	}

	public QueryBuilder whereAnd(String criteria, String... parameters) {
		if (criterias == null)
			criterias = Criteria.create(criteria, parameters);
		else
			criterias.and(criteria, parameters);

		return this;
	}

	public QueryBuilder whereOr(String criteria, String... parameters) {
		if (criterias == null)
			criterias = Criteria.create(criteria, parameters);
		else
			criterias.or(criteria, parameters);

		return this;
	}

	public QueryBuilder whereAnd(SimpleCriteria criteria) {
		if (criteria != null) {
			if (criterias == null)
				criterias = Criteria.create(criteria);
			else
				criterias.and(criteria);
		}

		return this;
	}

	public QueryBuilder whereOr(SimpleCriteria criteria) {
		if (criteria != null) {
			if (criterias == null)
				criterias = Criteria.create(criteria);
			else
				criterias.or(criteria);
		}

		return this;
	}

	public QueryBuilder union(QueryBuilder unionQuery) {
		unionQuery.isUnionAll = false;
		unions.add(unionQuery);

		return this;
	}

	public QueryBuilder unionAll(QueryBuilder unionQuery) {
		unionQuery.isUnionAll = true;
		unions.add(unionQuery);

		return this;
	}

	public QueryBuilder groupBy(String group) {
		groupBy.add(group);
		return this;
	}

	public QueryBuilder orderByAscending(String order) {
		orderBy.add(order + " ASC");
		return this;
	}
	
	public QueryBuilder orderByAscendingIgnoreCase(String order) {
		orderBy.add(order + " COLLATE NOCASE ASC");
		return this;
	}

	public QueryBuilder orderByDescending(String order) {
		orderBy.add(order + " DESC");
		return this;
	}
	
	public QueryBuilder orderByDescendingIgnoreCase(String order) {
		orderBy.add(order + " COLLATE NOCASE DESC");
		return this;
	}

	public QueryBuilder offset(int offset) {
		this.offset = offset;
		return this;
	}

	public QueryBuilder limit(int limit) {
		this.limit = limit;
		return this;
	}

	public String buildQuery() {
		boolean firstItem;

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");

		if (distinct)
			sb.append("DISTINCT ");

		if (projections.size() > 0) {
			firstItem = true;
			for (String projection : projections) {
				if (firstItem)
					firstItem = false;
				else
					sb.append(", ");

				sb.append(projection);
			}
		} else {
			sb.append("*");
		}

		if(tables.size() > 0) {
			sb.append(" FROM ");
		
			firstItem = true;
			for (String table : tables) {
				if (firstItem)
					firstItem = false;
				else
					sb.append(", ");
		
				sb.append(table);
			}
		}

		if (criterias != null) {
			sb.append(" WHERE ");
			sb.append(criterias.build());
		}

		if (groupBy.size() > 0) {
			sb.append(" GROUP BY ");

			firstItem = true;
			for (String group : groupBy) {
				if (firstItem)
					firstItem = false;
				else
					sb.append(", ");

				sb.append(group);
			}
		}

		if (orderBy.size() > 0) {
			sb.append(" ORDER BY ");

			firstItem = true;
			for (String order : orderBy) {
				if (firstItem)
					firstItem = false;
				else
					sb.append(", ");

				sb.append(order);
			}
		}

		if (limit > 0) {
			sb.append(" LIMIT ");
			sb.append(limit);
		}

		if (offset > 0) {
			sb.append(" OFFSET ");
			sb.append(offset);
		}

		for (QueryBuilder union : unions) {
			if (union.isUnionAll)
				sb.append(" UNION ALL ");
			else
				sb.append(" UNION ");

			sb.append(union.buildQuery());
		}

		// SQLITE IN ANDROID ALLOWS ONLY 999 PARAMETER IN A QUERY.
		// THIS PIECE OF CODE REMOVES PARAMETERS THAT EXCEED THIS LIMIT
		// AND REPLACES THEM FOR THE VALUE IN THE GENERATED QUERY STRING
		List<String> parameters = getParametersCollection();
		int parametersSize = parameters.size();
		int index = 0;
		int charIndex;

		while (parametersSize > 999) {
			charIndex = sb.lastIndexOf("?");
			sb.replace(charIndex, charIndex + 1, DatabaseUtils.sqlEscapeString(parameters.get(index)));

			parametersSize--;
			index++;
		}

		return sb.toString();
	}

	public String[] buildParameters() {
		List<String> parameters = getParametersCollection();

		if (parameters.size() > 0) {

			// Removes some parameters to keep the 
			// collection within the 999 parameters limit
			while (parameters.size() > 999)
				parameters.remove(parameters.size() - 1);

			return Utils.toArray(String.class, parameters);
		} else
			return null;
	}
	
	public String[] buildParametersDefaultEmpty() {
		String[] parameters = buildParameters();
		parameters = (parameters != null ? parameters : new String[0]);
		
		return parameters;
	}

	public Criteria getCriteria() {
		return criterias;
	}

	private List<String> getParametersCollection() {
		// Join every parameter needed for the query in a single collection
		// taking into account the order of the parameters
		
		ArrayList<String> parameters = new ArrayList<String>();

		if (selectParameters != null)
			parameters.addAll(selectParameters);

		if (joinParameters != null)
			parameters.addAll(joinParameters);

		String[] criteriasParams = (criterias == null ? new String[0] : criterias.getParameters());
		criteriasParams = (criteriasParams == null ? new String[0] : criteriasParams);
		for (int i = 0; i < criteriasParams.length; i++) {
			parameters.add(criteriasParams[i]);
		}

		String[] unionParams;
		for (QueryBuilder union : unions) {
			unionParams = union.buildParameters();

			if (unionParams != null) {
				for (int i = 0; i < unionParams.length; i++) {
					parameters.add(unionParams[i]);
				}
			}
		}

		return parameters;
	}

	public String toDebugSqlString() {
		String[] parameters = buildParameters();
		String sqlString = buildQuery();
		
		if (parameters != null) {
			for (String par : parameters) {
				sqlString = sqlString.replaceFirst("\\?", DatabaseUtils.sqlEscapeString(par));
			}
		}
		
		return sqlString;
	}
}
