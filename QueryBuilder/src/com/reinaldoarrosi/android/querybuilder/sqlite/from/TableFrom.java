package com.reinaldoarrosi.android.querybuilder.sqlite.from;

import java.util.List;

import com.reinaldoarrosi.android.querybuilder.Utils;


public class TableFrom extends AliasableFrom<TableFrom> {
	private String table;
	
	protected TableFrom(String table) {
		this.table = table;
	}

	@Override
	public String build() {
		String ret = (!Utils.isNullOrWhiteSpace(table) ? table : "");

		if(!Utils.isNullOrWhiteSpace(alias))
			ret = ret + " AS " + alias;
		
		return ret;
	}

	@Override
	public List<Object> buildParameters() {
		return Utils.EMPTY_LIST;
	}
}
