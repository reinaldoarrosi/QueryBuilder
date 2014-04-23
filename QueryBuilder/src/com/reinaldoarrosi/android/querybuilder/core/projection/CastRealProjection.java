package com.reinaldoarrosi.android.querybuilder.core.projection;

import java.util.List;

import com.reinaldoarrosi.android.querybuilder.Utils;

public class CastRealProjection extends Projection {
	private Projection projection;

	protected CastRealProjection(Projection projection) {
		this.projection = projection;
	}

	@Override
	public String build() {
		String ret = (projection != null ? projection.build() : "");
		return "CAST(" + ret + " AS REAL)";
	}

	@Override
	public List<Object> buildParameters() {
		if (projection != null)
			return projection.buildParameters();
		else
			return Utils.EMPTY_LIST;
	}
}
