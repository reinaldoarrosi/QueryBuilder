package com.reinaldoarrosi.android.querybuilder;

public class PartialJoin {
	private String leftTable;
	private String rightTable;
	private String join;
	
	PartialJoin(String leftTable, String rightTable, String join) {
		this.leftTable = leftTable;
		this.rightTable = rightTable;
		this.join = join;
	}
	
	public Join on(String leftColumn, String rightColumn) {
		Join join = new Join(leftTable, rightTable, this.join, Criteria.equalsColumn(leftColumn, rightColumn));		
		return join;
	}
	
	public Join on(Criteria joinClause) {
		Join join = new Join(leftTable, rightTable, this.join, joinClause);		
		return join;
	}
}
