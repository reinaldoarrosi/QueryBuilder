package com.reinaldoarrosi.android.querybuilder;

public class PartialJoin {
	private String leftTable;
	private String rightTable;
	private String join;
	private String[] parameters;
	
	PartialJoin(String leftTable, String rightTable, String join, String[] parameters) {
		this.leftTable = leftTable;
		this.rightTable = rightTable;
		this.join = join;
		this.parameters = parameters;
	}
	
	public Join on(String leftColumn, String rightColumn) {
		Join join = new Join(leftTable, rightTable, this.join, Criteria.equalsColumn(leftColumn, rightColumn), parameters);		
		return join;
	}
	
	public Join on(Criteria joinClause) {
		Join join = new Join(leftTable, rightTable, this.join, joinClause, parameters);		
		return join;
	}
}
