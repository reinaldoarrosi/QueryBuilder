package com.reinaldoarrosi.android.querybuilder.sqlite.criteria;

import java.util.ArrayList;
import java.util.List;

import com.reinaldoarrosi.android.querybuilder.sqlite.projection.AliasedProjection;
import com.reinaldoarrosi.android.querybuilder.sqlite.projection.Projection;

public class ValueBetweenCriteria extends Criteria {
	private Object value;
	private Projection projectionStart;
	private Projection projectionEnd;
	
	protected ValueBetweenCriteria(Object value, Projection projectionStart, Projection projectionEnd) {
		this.value = value;
		this.projectionStart = projectionStart;
		this.projectionEnd = projectionEnd;
		
		if(this.projectionStart instanceof AliasedProjection)
			this.projectionStart = ((AliasedProjection)this.projectionStart).removeAlias();
		
		if(this.projectionEnd instanceof AliasedProjection)
			this.projectionEnd = ((AliasedProjection)this.projectionEnd).removeAlias();
	}
	
	@Override
	public String build() {
		StringBuilder sb = new StringBuilder();
		
		sb.append((value != null ? "?" : "NULL"));
		sb.append(" BETWEEN ");
		sb.append((projectionStart != null ? projectionStart.build() : "NULL"));
		sb.append(" AND ");
		sb.append((projectionEnd != null ? projectionEnd.build() : "NULL"));
		
		return sb.toString();
	}

	@Override
	public List<Object> buildParameters() {
		List<Object> ret = new ArrayList<Object>();
		
		if(value != null)
			ret.add(value);
		
		if(projectionStart != null)
			ret.addAll(projectionStart.buildParameters());
		
		if(projectionEnd != null)
			ret.addAll(projectionEnd.buildParameters());
		
		return ret;
	}
}
