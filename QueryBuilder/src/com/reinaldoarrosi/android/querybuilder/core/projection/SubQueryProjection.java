package com.reinaldoarrosi.android.querybuilder.core.projection;

import java.util.List;

import com.reinaldoarrosi.android.querybuilder.Utils;
import com.reinaldoarrosi.android.querybuilder.core.QueryBuilder;

public class SubQueryProjection extends Projection {
	private QueryBuilder subQuery;
	
	protected SubQueryProjection(QueryBuilder subQuery) {
		this.subQuery = subQuery;
	}

	@Override
	public String build() {
		if(subQuery != null)
			return "(" + subQuery.build() + ")";
		else
			return "";
	}

	@Override
	public List<Object> buildParameters() {
		if(subQuery != null)
			return subQuery.buildParameters();
		else
			return Utils.EMPTY_LIST;
	}
}
