package com.reinaldoarrosi.android.querybuilder.core.from;

public abstract class AliasableFrom<T> extends From {
	protected String alias;
	
	public T as(String alias) {
		this.alias = alias;
		return (T)this;
	}
}
