package com.reinaldoarrosi.android.querybuilder;


public class Join {
	public static PartialJoin innerJoin(String leftTable, String rightTable) {
		PartialJoin join = new PartialJoin(leftTable, rightTable, "INNER JOIN", null);		
		return join;
	}
	
	public static PartialJoin leftJoin(String leftTable, String rightTable) {
		PartialJoin join = new PartialJoin(leftTable, rightTable, "LEFT JOIN", null);
		return join;
	}
	
	private String leftTable;
	private String rightTable;
	private String join;
	private Criteria joinClause;
	private String[] parameters;
	
	Join(String leftTable, String rightTable, String join, Criteria joinClause, String[] parameters) {
		this.leftTable = leftTable;
		this.rightTable = rightTable;
		this.join = join;
		this.joinClause = joinClause;
		
		parameters = (parameters == null ? new String[0] : parameters);
		
		String[] joinParams = joinClause.getParameters();
		joinParams = (joinParams == null ? new String[0] : joinParams);
		
		String[] newParams = new String[parameters.length + joinParams.length];
		
		for (int i = 0; i < parameters.length; i++) {
			newParams[i] = parameters[i];
		}
		
		for (int i = 0; i < joinParams.length; i++) {
			newParams[i + parameters.length] = joinParams[i];
		}
		
		if(newParams.length == 0)
			this.parameters = null;
		else
			this.parameters = newParams;
	}
	
	public PartialJoin innerJoin(String table) {
		String actualJoin = this.build();
		PartialJoin partialJoin = new PartialJoin(actualJoin, table, "INNER JOIN", parameters);
		
		return partialJoin;
	}
	
	public PartialJoin leftJoin(String table) {
		String actualJoin = this.build();
		PartialJoin partialJoin = new PartialJoin(actualJoin, table, "LEFT JOIN", parameters);
		
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
	
	public Join onAnd(Criteria criteria) {
		joinClause = joinClause.and(criteria);
		return this;
	}
	
	public String[] getParameters() {
		return parameters;
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
