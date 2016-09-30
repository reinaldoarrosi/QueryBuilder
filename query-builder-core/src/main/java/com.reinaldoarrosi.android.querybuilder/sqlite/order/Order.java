package com.reinaldoarrosi.android.querybuilder.sqlite.order;

import java.util.List;

import com.reinaldoarrosi.android.querybuilder.sqlite.projection.AliasedProjection;
import com.reinaldoarrosi.android.querybuilder.sqlite.projection.Projection;

public abstract class Order {
	public static Order orderByAscending(String column) {
		return new OrderAscending(Projection.column(column));
	}
	
	public static Order orderByDescending(String column) {
		return new OrderDescending(Projection.column(column));
	}
	
	public static Order orderByAscending(Projection projection) {
		return new OrderAscending(projection);
	}
	
	public static Order orderByDescending(Projection projection) {
		return new OrderDescending(projection);
	}
	
	public static Order orderByAscendingIgnoreCase(String column) {
		return new OrderAscendingIgnoreCase(Projection.column(column));
	}
	
	public static Order orderByDescendingIgnoreCase(String column) {
		return new OrderDescendingIgnoreCase(Projection.column(column));
	}
	
	public static Order orderByAscendingIgnoreCase(Projection projection) {
		return new OrderAscendingIgnoreCase(projection);
	}
	
	public static Order orderByDescendingIgnoreCase(Projection projection) {
		return new OrderDescendingIgnoreCase(projection);
	}
	
	
	protected Projection projection;
	
	public Order(Projection projection) {
		this.projection = projection;
		
		if(this.projection instanceof AliasedProjection)
			this.projection = ((AliasedProjection)this.projection).removeAlias();
	}
	
	public abstract String build();
	public abstract List<Object> buildParameters();
}
