package com.reinaldoarrosi.android.querybuilder.sqlite;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.reinaldoarrosi.android.querybuilder.Utils;
import com.reinaldoarrosi.android.querybuilder.sqlite.criteria.Criteria;
import com.reinaldoarrosi.android.querybuilder.sqlite.from.From;
import com.reinaldoarrosi.android.querybuilder.sqlite.projection.Projection;


public class QueryBuilder {
	private List<Projection> projections;
	private From from;
	private Criteria criteria;
	private List<Projection> groupBy;
	private List<Projection> orderBy;
	private int skip;
	private int take;
	private boolean distinct;
	
	private DateTimeFormatter dateFormat;
	private DateTimeFormatter dateTimeFormat;
	
	public QueryBuilder() {
		this(QueryBuildConfiguration.current().getDateFormat(), QueryBuildConfiguration.current().getDateTimeFormat());
	}
	
	public QueryBuilder(String dateFormat, String dateTimeFormat) {
		this(DateTimeFormat.forPattern(dateFormat), DateTimeFormat.forPattern(dateTimeFormat));
	}
	
	public QueryBuilder(DateTimeFormatter dateFormat, DateTimeFormatter dateTimeFormat) {
		projections = new ArrayList<Projection>();
		from = null;
		criteria = null;
		groupBy = new ArrayList<Projection>();
		orderBy = new ArrayList<Projection>();
		skip = -1;
		take = -1;
		distinct = false;
		
		this.dateFormat = dateFormat;
		this.dateTimeFormat = dateTimeFormat;
	}
	
	public QueryBuilder withDateFormat(String format) {
		return withDateFormat(DateTimeFormat.forPattern(format));
	}
	
	public QueryBuilder withDateFormat(DateTimeFormatter format) {
		this.dateFormat = format;
		return this;
	}
	
	public QueryBuilder withDateTimeFormat(String format) {
		return withDateTimeFormat(DateTimeFormat.forPattern(format));
	}
	
	public QueryBuilder withDateTimeFormat(DateTimeFormatter format) {
		this.dateTimeFormat = format;
		return this;
	}
	
	public QueryBuilder select(String... columns) {
		if(columns == null)
			return this;
		
		Projection[] projections = new Projection[columns.length];
		
		for (int i = 0; i < columns.length; i++) {
			projections[i] = Projection.column(columns[i]);
		}
		
		return select(projections);
	}
	
	public QueryBuilder select(Projection... projections) {
		if(projections == null)
			return this;
		
		for (int i = 0; i < projections.length; i++) {
			this.projections.add(projections[i]);
		}
		
		return this;
	}
	
	public QueryBuilder from(From from) {
		if(from != null)
			this.from = from;
		
		return this;
	}
	
	public QueryBuilder whereAnd(Criteria criteria) {
		if(criteria != null) {
			if(this.criteria == null)
				this.criteria = criteria;
			else
				this.criteria = this.criteria.and(criteria);
		}
		
		return this;
	}
	
	public QueryBuilder whereOr(Criteria criteria) {
		if(criteria != null) {
			if(this.criteria == null)
				this.criteria = criteria;
			else
				this.criteria = this.criteria.or(criteria);
		}
		
		return this;
	}
	
	public QueryBuilder groupBy(String... columns) {
		if(columns == null)
			return this;
		
		Projection[] projections = new Projection[columns.length];
		
		for (int i = 0; i < columns.length; i++) {
			projections[i] = Projection.column(columns[i]);
		}
		
		return groupBy(projections);
	}
	
	public QueryBuilder groupBy(Projection... projections) {
		if(projections == null)
			return this;
		
		for (int i = 0; i < projections.length; i++) {
			this.groupBy.add(projections[i]);
		}
		
		return this;
	}
	
	public QueryBuilder orderBy(String... columns) {
		if(columns == null)
			return this;
		
		Projection[] projections = new Projection[columns.length];
		
		for (int i = 0; i < columns.length; i++) {
			projections[i] = Projection.column(columns[i]);
		}
		
		return orderBy(projections);
	}
	
	public QueryBuilder orderBy(Projection... projections) {
		if(projections == null)
			return this;
		
		for (int i = 0; i < projections.length; i++) {
			this.orderBy.add(projections[i]);
		}
		
		return this;
	}
	
	public QueryBuilder skip(int skip) {
		this.skip = skip;
		return this;
	}
	
	public QueryBuilder skipNone() {
		this.skip = -1;
		return this;
	}
	
	public QueryBuilder take(int take) {
		this.take = take;
		return this;
	}
	
	public QueryBuilder takeAll() {
		this.take = -1;
		return this;
	}
	
	public QueryBuilder distinct() {
		this.distinct = true;
		return this;
	}
	
	public QueryBuilder notDistinct() {
		this.distinct = false;
		return this;
	}
	
	public String build() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		
		if(distinct)
			sb.append("DISTINCT ");
		
		if(projections.size() <= 0) {
			sb.append("*");
		} else {
			for (Projection p : projections) {
				sb.append(p.build());
				sb.append(", ");
			}
			
			sb.setLength(sb.length() - 2); // removes the ", " from the last entry
		}
		
		sb.append(" ");
		
		if(from != null) {
			sb.append(from.build());
			sb.append(" ");
		}
		
		if(criteria != null) {
			sb.append("WHERE ");
			sb.append(criteria.build());
		}
		
		if(groupBy.size() > 0) {
			sb.append(" GROUP BY ");
			
			for (Projection p : groupBy) {
				sb.append(p.build());
				sb.append(", ");
			}
			
			sb.setLength(sb.length() - 2); // removes the ", " from the last entry
		}
		
		if(orderBy.size() > 0) {
			sb.append(" ORDER BY ");
			
			for (Projection p : orderBy) {
				sb.append(p.build());
				sb.append(", ");
			}
			
			sb.setLength(sb.length() - 2); // removes the ", " from the last entry
		}
		
		if(take > 0) {
			sb.append(" LIMIT ");
			sb.append(take);
		}
		
		if(skip > 0) {
			sb.append(" OFFSET ");
			sb.append(skip);
		}
		
		return sb.toString();
	}
	
	public List<Object> buildParameters() {
		List<Object> ret = new ArrayList<Object>();
		
		for (Projection p : projections) {
			ret.addAll(p.buildParameters());
		}
		
		if(from != null)
			ret.addAll(from.buildParameters());
		
		if(criteria != null)
			ret.addAll(criteria.buildParameters());
		
		for (Projection p : groupBy) {
			ret.addAll(p.buildParameters());
		}
		
		for (Projection p : orderBy) {
			ret.addAll(p.buildParameters());
		}
		
		preProcessDateValues(ret);
		return ret;
	}

	private void preProcessDateValues(List<Object> values) {
		Object value;
		int index = 0;
		
		while(index < values.size()) {
			value = values.get(index);
			
			if(value instanceof LocalDateTime) {
				values.remove(index);
				values.add(index, Utils.dateToString(((LocalDateTime)value), dateTimeFormat));
			} else if(value instanceof LocalDate) {
				values.remove(index);
				values.add(index, Utils.dateToString(((LocalDate)value), dateFormat));
			}
			
			index++;
		}
	}
}
