package com.reinaldoarrosi.android.querybuilder;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;

import com.reinaldoarrosi.android.querybuilder.core.QueryBuildConfiguration;

public class Utils {
	public static final List<Object> EMPTY_LIST = new ArrayList<Object>();

	public static String dateToString(LocalDate date, DateTimeFormatter format) {
		if (date == null)
			return null;
		
		if(format == null)
			format = QueryBuildConfiguration.current().getDateFormat();

		try {
			return date.toString(format);
		} catch (Exception e) {
			return null;
		}
	}

	public static String dateToString(LocalDateTime date, DateTimeFormatter format) {
		if (date == null)
			return null;

		if(format == null)
			format = QueryBuildConfiguration.current().getDateTimeFormat();
		
		try {
			return date.toString(format);
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean isNullOrEmpty(String string) {
		return (string == null || string.length() <= 0);
	}
	
	public static boolean isNullOrWhiteSpace(String string) {
		return (string == null || string.trim().length() <= 0);
	}
}
