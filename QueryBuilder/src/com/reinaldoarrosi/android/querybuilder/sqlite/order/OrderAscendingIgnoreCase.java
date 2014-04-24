package com.reinaldoarrosi.android.querybuilder.sqlite.order;

import java.util.List;

import com.reinaldoarrosi.android.querybuilder.Utils;
import com.reinaldoarrosi.android.querybuilder.sqlite.projection.Projection;

public class OrderAscendingIgnoreCase extends Order {

	public OrderAscendingIgnoreCase(Projection projection) {
		super(projection);
	}

	@Override
	public String build() {
		String ret = " COLLATE NOCASE ASC";
		
		if(projection != null)
			ret = projection.build() + ret;
		
		return ret;
	}

	@Override
	public List<Object> buildParameters() {
		if(projection != null)
			return projection.buildParameters();
		else
			return Utils.EMPTY_LIST;
	}
}
