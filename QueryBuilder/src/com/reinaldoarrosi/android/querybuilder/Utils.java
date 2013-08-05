package com.reinaldoarrosi.android.querybuilder;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Utils {
	public static final DateTimeFormatter DATE_FORMAT_DB = DateTimeFormat.forPattern("yyyy-MM-dd");
	public static final DateTimeFormatter TIME_FORMAT_DB = DateTimeFormat.forPattern("HH:mm");
	public static final DateTimeFormatter DATETIME_FORMAT_DB = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

	public static String dateToString(LocalDate date) {
		if (date == null)
			return null;

		try {
			return date.toString(DATE_FORMAT_DB);
		} catch (Exception e) {
			return null;
		}
	}

	public static String dateToString(LocalDateTime date) {
		if (date == null)
			return null;

		try {
			return date.toString(DATETIME_FORMAT_DB);
		} catch (Exception e) {
			return null;
		}
	}

	public static String toString(Object value) {
		if (value == null)
			return null;

		if (value instanceof Float)
			return new BigDecimal((Float) value).stripTrailingZeros().toPlainString();
		else if (value instanceof Double)
			return new BigDecimal((Double) value).stripTrailingZeros().toPlainString();
		else
			return String.valueOf(value);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(Class<T> clazz, Collection<T> collection) {
		T[] array = (T[]) Array.newInstance(clazz, collection.size());
		collection.toArray(array);

		return array;
	}
}
