package com.reinaldoarrosi.android.querybuilder.sqlite.criteria;

import java.util.ArrayList;
import java.util.List;

import com.reinaldoarrosi.android.querybuilder.sqlite.projection.Projection;

public class NotInCriteria extends Criteria {
	private Projection projection;
	private List<Object> valuesList;
	private Object[] valuesArray;
	
	protected NotInCriteria(Projection projection, List<Object> values) {
		this.projection = projection;
		this.valuesList = values;
		this.valuesArray = null;
	}
	
	protected NotInCriteria(Projection projection, Object[] values) {
		this.projection = projection;
		this.valuesArray = values;
		this.valuesList = null;
	}

	@Override
	public String build() {
		StringBuilder sb = new StringBuilder();
		
		if(projection != null)
			sb.append(projection.build());
		
		sb.append(" NOT IN (");

		if(valuesList != null) {
			if(valuesList.size() <= 0)
				return "1=1";
			
			for (int i = 0; i < valuesList.size(); i++) {
				if(valuesList.get(i) != null)
					sb.append("?, ");
				else
					sb.append("NULL, ");
			}
		} else {
			if(valuesArray.length <= 0)
				return "1=1";
			
			for (int i = 0; i < valuesArray.length; i++) {
				if(valuesArray[i] != null)
					sb.append("?, ");
				else
					sb.append("NULL, ");
			}
		}
					
		sb.setLength(sb.length() - 2); //removes the ", " from the last entry
		sb.append(")");
		
		return sb.toString();
	}

	@Override
	public List<Object> buildParameters() {
		List<Object> ret = new ArrayList<Object>();
		
		if(projection != null)
			ret.addAll(projection.buildParameters());
		
		if(valuesList != null) {
			for (int i = 0; i < valuesList.size(); i++) {
				if(valuesList.get(i) != null)
					ret.add(valuesList.get(i));
			}
		} else {
			for (int i = 0; i < valuesArray.length; i++) {
				if(valuesArray[i] != null)
					ret.add(valuesArray[i]);
			}
		}
		
		return ret;
	}
}
