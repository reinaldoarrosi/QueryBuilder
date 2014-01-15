package com.reinaldoarrosi.android.querybuilder;

import java.util.ArrayList;
import java.util.List;

public class Join {
	private String joinType;
	private String leftTable;
	private String rightTable;
	private Criteria joinCriteria;
	private boolean isSubJoin;
	private List<Join> subJoins;
	
	public static PartialJoin innerJoin(String leftTable, String rightTable) {
		return new RootPartialJoin(leftTable, rightTable, "INNER JOIN");
	}
	
	public static PartialJoin leftJoin(String leftTable, String rightTable) {
		return new RootPartialJoin(leftTable, rightTable, "LEFT JOIN");
	}
	
	private Join(String joinType, String leftTable, String rightTable, Criteria joinCriteria) {
		this.joinType = joinType;
		this.leftTable = leftTable;
		this.rightTable = rightTable;
		this.joinCriteria = joinCriteria;
		this.isSubJoin = false;
		this.subJoins = new ArrayList<Join>();
	}
	
	private Join(String joinType, String table, Criteria joinCriteria) {
		this(joinType, null, table, joinCriteria);
		this.isSubJoin = true;
	}
	
	public PartialJoin innerJoin(String table) {
		PartialJoin partialJoin = new SubPartialJoin(table, "INNER JOIN");
		return partialJoin;
	}
	
	public PartialJoin leftJoin(String table) {
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
		String[] params = joinCriteria.getParameters();
		
		for (int i = 0; i < params.length; i++)
			parameters.add(params[i]);
		
		for (Join subJoin : subJoins) {
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
		
		private RootPartialJoin(String leftTable, String rightTable, String joinType) {
			super(joinType);
			this.leftTable = leftTable;
			this.rightTable = rightTable;
		}
		
		public Join on(String leftColumn, String rightColumn) {
			Join join = new Join(joinType, leftTable, rightTable, Criteria.equalsColumn(leftColumn, rightColumn));		
			return join;
		}
		
		public Join on(Criteria joinClause) {
			Join join = new Join(joinType, leftTable, rightTable, joinClause);		
			return join;
		}
	}
	
	private class SubPartialJoin extends PartialJoin {
		private String table;

		private SubPartialJoin(String table, String joinType) {
			super(joinType);
			this.table = table;
		}
		
		@Override
		public Join on(String leftColumn, String rightColumn) {
			Join.this.subJoins.add(new Join(joinType, table, Criteria.equalsColumn(leftColumn, rightColumn)));
			return Join.this;
		}

		@Override
		public Join on(Criteria joinCriteria) {
			Join.this.subJoins.add(new Join(joinType, table, joinCriteria));
			return Join.this;
		}
	}
}
