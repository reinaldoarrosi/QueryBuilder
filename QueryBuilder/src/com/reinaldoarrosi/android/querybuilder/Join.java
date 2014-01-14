package com.reinaldoarrosi.android.querybuilder;


public class Join {
	public static PartialJoin innerJoin(String leftTable, String rightTable) {
		PartialJoin join = new PartialJoin(leftTable, rightTable, "INNER JOIN");		
		return join;
	}
	
	public static PartialJoin leftJoin(String leftTable, String rightTable) {
		PartialJoin join = new PartialJoin(leftTable, rightTable, "LEFT JOIN");
		return join;
	}
	
	private String leftTable;
	private String rightTable;
	private String join;
	private Criteria joinClause;
	
	Join(String leftTable, String rightTable, String join, Criteria joinClause) {
		this.leftTable = leftTable;
		this.rightTable = rightTable;
		this.join = join;
		this.joinClause = joinClause;
	}
	
	public PartialJoin innerJoin(String table) {
		String actualJoin = this.build();
		PartialJoin partialJoin = new PartialJoin(actualJoin, table, "INNER JOIN");
		
		return partialJoin;
	}
	
	public PartialJoin leftJoin(String table) {
		String actualJoin = this.build();
		PartialJoin partialJoin = new PartialJoin(actualJoin, table, "LEFT JOIN");
		
		return partialJoin;
	}
	
	public Join onOr(String leftColumn, String rightColumn) {
		joinClause = joinClause.or(Criteria.equalsColumn(leftColumn, rightColumn));
		return this;
	}
	
	public Join onAnd(String leftColumn, String rightColumn) {
		joinClause = joinClause.and(Criteria.equalsColumn(leftColumn, rightColumn));
		return this;
	}
	
	public Join onOr(Criteria criteria) {
		joinClause = joinClause.and(criteria);
		return this;
	}
	
	public Join onAnd(Criteria criteria) {
		joinClause = joinClause.and(criteria);
		return this;
	}
	
	public String[] getParameters() {
		return joinClause.getParameters();
	}
	
	public String build() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(leftTable);
		sb.append(" ");
		sb.append(join);
		sb.append(" ");
		sb.append(rightTable);
		sb.append(" ON ");
		sb.append(joinClause.build());
		sb.append(")");

		return sb.toString();
	}
}
