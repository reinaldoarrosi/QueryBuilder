package com.reinaldoarrosi.android.querybuilder.core.projection;

import java.util.ArrayList;
import java.util.List;

import com.reinaldoarrosi.android.querybuilder.Utils;

public class ConstantProjection extends Projection {
	private Object constant;
	
	protected ConstantProjection(Object constant) {
		this.constant = constant;
	}

	@Override
	public String build() {
		if(constant != null)
			return "?";
		else
			return "NULL";
	}

	@Override
	public List<Object> buildParameters() {
		if(constant != null) {
			List<Object> ret = new ArrayList<Object>();
			ret.add(constant);
			
			return ret;
		} else {
			return Utils.EMPTY_LIST;
		}
	}
}
