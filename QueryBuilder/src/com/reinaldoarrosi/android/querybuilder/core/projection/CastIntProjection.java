package com.reinaldoarrosi.android.querybuilder.core.projection;

import java.util.List;

import com.reinaldoarrosi.android.querybuilder.Utils;

public class CastIntProjection extends Projection {
	private Projection projection;

	protected CastIntProjection(Projection projection) {
		this.projection = projection;
	}

	@Override
	public String build() {
		String ret = (projection != null ? projection.build() : "");
		return "CAST(" + ret + " AS INTEGER)";
	}

	@Override
	public List<Object> buildParameters() {
		if (projection != null)
			return projection.buildParameters();
		else
			return Utils.EMPTY_LIST;
	}
}
