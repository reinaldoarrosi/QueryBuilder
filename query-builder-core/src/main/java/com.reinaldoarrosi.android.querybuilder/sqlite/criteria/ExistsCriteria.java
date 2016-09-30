package com.reinaldoarrosi.android.querybuilder.sqlite.criteria;

import java.util.List;

import com.reinaldoarrosi.android.querybuilder.Utils;
import com.reinaldoarrosi.android.querybuilder.sqlite.QueryBuilder;

public class ExistsCriteria extends Criteria {
	private QueryBuilder subQuery;
	
	public ExistsCriteria(QueryBuilder subQuery) {
		this.subQuery = subQuery;
	}

	@Override
	public String build() {
		String ret = "EXISTS(";
		
		if(subQuery != null)
			ret = ret + subQuery.build();
		
		ret = ret + ")";
		return ret;
	}

	@Override
	public List<Object> buildParameters() {
		if(subQuery != null)
			return subQuery.buildParameters();
		else
			return Utils.EMPTY_LIST;
	}
}
