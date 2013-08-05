package com.reinaldoarrosi.android.querybuilder;

class SimpleCriteria {
	public enum Type {
		AND,
		OR
	}
	
	protected String _criteria;
	protected Type _type;
	protected String[] _parameters;
	
	protected SimpleCriteria() {
		_criteria = "";
		_type = Type.AND;
		_parameters = null;
	}
	
	protected SimpleCriteria(String criteria, Type type, String[] parameters) {
		_criteria = criteria;
		_type = type;
		_parameters = parameters;
	}
	
	public String build() {
		return _criteria;
	}
	
	public void setType(Type value) {
		_type = value;
	}
	
	public Type getType() {
		return _type;
	}
	
	public void setParameters(String[] value) {
		_parameters = value;
	}
	
	public String[] getParameters() {
		return _parameters;
	}
}
