package com.reinaldoarrosi.android.querybuilder.sqlite.projection;

import java.util.List;

import com.reinaldoarrosi.android.querybuilder.Utils;

public class CastDateProjection extends Projection {
	private Projection projection;
	
	protected CastDateProjection(Projection projection) {
		this.projection = projection;
	}
	
	@Override
	public String build() {
		String ret = (projection != null ? projection.build() : "");
		return "DATE(" + ret + ")";
	}

	@Override
	public List<Object> buildParameters() {
		if(projection != null)
			return projection.buildParameters();
		else
			return Utils.EMPTY_LIST;
	}
}
