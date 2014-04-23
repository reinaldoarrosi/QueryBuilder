package com.reinaldoarrosi.android.querybuilder.core.criteria;

import com.reinaldoarrosi.android.querybuilder.core.QueryBuilder;

public class NotExistsCriteria extends ExistsCriteria {
	protected NotExistsCriteria(QueryBuilder subQuery) {
		super(subQuery);
	}
	
	@Override
	public String build() {
		return "NOT " + super.build();
	}
}
