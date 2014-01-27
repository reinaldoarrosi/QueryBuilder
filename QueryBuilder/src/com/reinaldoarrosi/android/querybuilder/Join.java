package com.reinaldoarrosi.android.querybuilder;

import java.util.ArrayList;
import java.util.List;

public class Join {
	private String joinType;
	private String leftTable;
	private String[] leftTableParameters;
	private String rightTable;
	private String[] rightTableParameters;
	private Criteria joinCriteria;
	private boolean isSubJoin;
	private List<Join> subJoins;
	
	public static PartialJoin innerJoin(String leftTable, String rightTable) {
		return new RootPartialJoin(leftTable, rightTable, "INNER JOIN");
	}
	
	public static PartialJoin innerJoin(QueryBuilder leftTable, String rightTable) {
		return new RootPartialJoin(leftTable, rightTable, "INNER JOIN");
	}
	
	public static PartialJoin innerJoin(String leftTable, QueryBuilder rightTable) {
		return new RootPartialJoin(leftTable, rightTable, "INNER JOIN");
	}
	
	public static PartialJoin innerJoin(QueryBuilder leftTable, QueryBuilder rightTable) {
		return new RootPartialJoin(leftTable, rightTable, "INNER JOIN");
	}
	
	public static PartialJoin leftJoin(String leftTable, String rightTable) {
		return new RootPartialJoin(leftTable, rightTable, "LEFT JOIN");
	}
	
	public static PartialJoin leftJoin(QueryBuilder leftTable, String rightTable) {
		return new RootPartialJoin(leftTable, rightTable, "LEFT JOIN");
	}
	
	public static PartialJoin leftJoin(String leftTable, QueryBuilder rightTable) {
		return new RootPartialJoin(leftTable, rightTable, "LEFT JOIN");
	}
	
	public static PartialJoin leftJoin(QueryBuilder leftTable, QueryBuilder rightTable) {
		return new RootPartialJoin(leftTable, rightTable, "LEFT JOIN");
	}
	
	private Join(String joinType, String leftTable, String[] leftTableParameters, String rightTable, String[] rightTableParameters, Criteria joinCriteria) {
		this.joinType = joinType;
		this.leftTable = leftTable;
		this.leftTableParameters = leftTableParameters;
		this.rightTable = rightTable;
		this.rightTableParameters = rightTableParameters;
		this.joinCriteria = joinCriteria;
		this.isSubJoin = false;
		this.subJoins = new ArrayList<Join>();
	}
	
	private Join(String joinType, String table, String[] tableParameters, Criteria joinCriteria) {
		this(joinType, null, null, table, tableParameters, joinCriteria);
		this.isSubJoin = true;
	}
	
	public PartialJoin innerJoin(String table) {
		PartialJoin partialJoin = new SubPartialJoin(table, "INNER JOIN");
		return partialJoin;
	}
	
	public PartialJoin innerJoin(QueryBuilder table) {
		PartialJoin partialJoin = new SubPartialJoin(table, "INNER JOIN");
		return partialJoin;
	}
	
	public PartialJoin leftJoin(String table) {
		PartialJoin partialJoin = new SubPartialJoin(table, "LEFT JOIN");
		return partialJoin;
	}
	
	public PartialJoin leftJoin(QueryBuilder table) {
		PartialJoin partialJoin = new SubPartialJoin(table, "LEFT JOIN");
		return partialJoin;
	}
	
	public Join onOr(String leftColumn, String rightColumn) {
		joinCriteria = joinCriteria.or(Criteria.equalsColumn(leftColumn, rightColumn));
		return this;
	}
	
	public Join onAnd(String leftColumn, String rightColumn) {
		joinCriteria = joinCriteria.and(Criteria.equalsColumn(leftColumn, rightColumn));
		return this;
	}
	
	public Join onAnd(Criteria criteria) {
		joinCriteria = joinCriteria.and(criteria);
		return this;
	}
	
	public Join onOr(Criteria criteria) {
		joinCriteria = joinCriteria.or(criteria);
		return this;
	}
	
	public String build() {
		if(isSubJoin)
			return "";
		
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(leftTable);
		sb.append(" ");
		sb.append(joinType);
		sb.append(" ");
		sb.append(rightTable);
		sb.append(" ON ");
		sb.append(joinCriteria.build());
		sb.append(")");
		
		for (Join subJoin : subJoins) {
			sb.insert(0, "(");
			sb.append(" ");
			sb.append(subJoin.joinType);
			sb.append(" ");
			sb.append(subJoin.rightTable);
			sb.append(" ON ");
			sb.append(subJoin.joinCriteria.build());
			sb.append(")");
		}

		return sb.toString();
	}
	
	public String[] getParameters() {
		List<String> parameters = new ArrayList<String>();

		if(leftTableParameters != null) {
			for (int i = 0; i < leftTableParameters.length; i++)
				parameters.add(leftTableParameters[i]);
		}
		
		if(rightTableParameters != null) {
			for (int i = 0; i < rightTableParameters.length; i++)
				parameters.add(rightTableParameters[i]);
		}
		
		String[] params = joinCriteria.getParameters();
		
		for (int i = 0; i < params.length; i++)
			parameters.add(params[i]);
		
		for (Join subJoin : subJoins) {
			if(subJoin.leftTableParameters != null) {
				for (int i = 0; i < subJoin.leftTableParameters.length; i++)
					parameters.add(subJoin.leftTableParameters[i]);
			}
			
			if(subJoin.rightTableParameters != null) {
				for (int i = 0; i < subJoin.rightTableParameters.length; i++)
					parameters.add(subJoin.rightTableParameters[i]);
			}
			
			params = subJoin.joinCriteria.getParameters();
			
			for (int i = 0; i < params.length; i++)
				parameters.add(params[i]);
		}
		
		String[] ret = new String[parameters.size()];
		parameters.toArray(ret);
		
		return ret;
	}
	
	public static abstract class PartialJoin {
		protected String joinType;
		
		private PartialJoin(String joinType) {
			this.joinType = joinType;
		}
		
		public abstract Join on(String leftColumn, String rightColumn);
		public abstract Join on(Criteria joinCriteria);
	}
	
	private static class RootPartialJoin extends PartialJoin {
		private String leftTable;
		private String rightTable;
		private String[] leftTableParameters;
		private String[] rightTableParameters;
		
		private RootPartialJoin(String leftTable, String rightTable, String joinType) {
			super(joinType);
			this.leftTable = leftTable;
			this.leftTableParameters = null;
			this.rightTable = rightTable;
			this.rightTableParameters = null;
		}
		
		private RootPartialJoin(QueryBuilder leftTable, String rightTable, String joinType) {
			super(joinType);
			this.leftTable = "(" + leftTable.buildQuery() + ")";
			this.leftTableParameters = leftTable.buildParameters();
			this.rightTable = rightTable;
			this.rightTableParameters = null;
		}
		
		private RootPartialJoin(String leftTable, QueryBuilder rightTable, String joinType) {
			super(joinType);
			this.leftTable = leftTable;
			this.leftTableParameters = null;
			this.rightTable = "(" + rightTable.buildQuery() + ")";
			this.rightTableParameters = rightTable.buildParameters();
		}
		
		private RootPartialJoin(QueryBuilder leftTable, QueryBuilder rightTable, String joinType) {
			super(joinType);
			this.leftTable = "(" + leftTable.buildQuery() + ")";
			this.leftTableParameters = leftTable.buildParameters();
			this.rightTable = "(" + rightTable.buildQuery() + ")";
			this.rightTableParameters = rightTable.buildParameters();
		}
		
		public Join on(String leftColumn, String rightColumn) {
			Join join = new Join(joinType, leftTable ,leftTableParameters, rightTable, rightTableParameters, Criteria.equalsColumn(leftColumn, rightColumn));		
			return join;
		}
		
		public Join on(Criteria joinClause) {
			Join join = new Join(joinType, leftTable, leftTableParameters, rightTable, rightTableParameters, joinClause);
			return join;
		}
	}
	
	private class SubPartialJoin extends PartialJoin {
		private String table;
		private String[] tableParameters;

		private SubPartialJoin(String table, String joinType) {
			super(joinType);
			this.table = table;
			this.tableParameters = null;
		}
		
		private SubPartialJoin(QueryBuilder table, String joinType) {
			super(joinType);
			this.table = table.buildQuery();
			this.tableParameters = table.buildParameters();
		}
		
		@Override
		public Join on(String leftColumn, String rightColumn) {
			Join.this.subJoins.add(new Join(joinType, table, tableParameters, Criteria.equalsColumn(leftColumn, rightColumn)));
			return Join.this;
		}

		@Override
		public Join on(Criteria joinCriteria) {
			Join.this.subJoins.add(new Join(joinType, table, tableParameters, joinCriteria));
			return Join.this;
		}
	}
}