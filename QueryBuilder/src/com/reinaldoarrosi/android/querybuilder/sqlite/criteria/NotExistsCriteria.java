package com.reinaldoarrosi.android.querybuilder.sqlite.criteria;

import com.reinaldoarrosi.android.querybuilder.sqlite.QueryBuilder;

public class NotExistsCriteria extends ExistsCriteria {
	protected NotExistsCriteria(QueryBuilder subQuery) {
		super(subQuery);
	}
	
	@Override
	public String build() {
		return "NOT " + super.build();
	}
}
