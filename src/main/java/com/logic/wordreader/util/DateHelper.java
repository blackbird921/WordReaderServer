package com.logic.wordreader.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DateHelper {

	private static Logger logger = LoggerFactory.getLogger(DateHelper.class);

	private DateHelper() {
	}

	public static Date nextXDay(Date date, int x) {
		Calendar calStart = getInstance();
		calStart.setTime(date);
		calStart.add(Calendar.DATE, x);
		return calStart.getTime();
	}

	public static Date getLast2MonthStart() {
		Calendar calStart = getInstance();
		calStart.setTime(getThisMonthStart());
		calStart.add(Calendar.MONTH, -2);
		return calStart.getTime();
	}

	public static Date getLastMonthStart() {
		Calendar calStart = getInstance();
		calStart.setTime(getThisMonthStart());
		calStart.add(Calendar.MONTH, -1);
		return calStart.getTime();
	}

	public static Date getNextMonthStart() {
		Calendar calStart = getInstance();
		calStart.setTime(getThisMonthStart());
		calStart.add(Calendar.MONTH, 1);
		return calStart.getTime();
	}


	public static Date getThisMonthStart() {
		Calendar cal = getInstance();
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.HOUR);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

// get start of the month
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	public static Date getLastWeekStart() {
		Calendar calStart = getInstance();
		calStart.setTime(getThisWeekStart());
		calStart.add(Calendar.DATE, -7);
		return calStart.getTime();
	}


	public static Date getThisWeekStart() {

		Calendar cal = getInstance();
		cal.clear(Calendar.HOUR_OF_DAY);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.HOUR);
		cal.clear(Calendar.MILLISECOND);

		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		return cal.getTime();

	}

	public static Date getNextWeekStart() {
		Calendar calStart = getInstance();
		calStart.setTime(getThisWeekStart());
		calStart.add(Calendar.DATE, 7);
		return calStart.getTime();
	}


	public static void main(String[] args) {
//		System.out.println(getThisWeekStart());
//		System.out.println(getLastWeekStart());
//		System.out.println(getThisMonthStart());
//		System.out.println(getLastMonthStart());
//		System.out.println(getLast2MonthStart());
	}

	public static String formatDate(java.util.Date date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateStr = "";
		if (date != null) {
			dateStr = formatter.format(date);
		}
		return dateStr;
	}

	public static Calendar getInstance() {
		Locale locale = new Locale("en", "GB");
		return Calendar.getInstance(locale);
	}

	public static java.sql.Date toSqlDate(Date date) {
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

	public static Calendar convertStringToCal(String date, String format) {
		java.util.Date theDate = null;

		try {
			SimpleDateFormat dateformat = new SimpleDateFormat(format);
			dateformat.setLenient(false);
			theDate = dateformat.parse(date);
		} catch (Exception e) {
			throw new RuntimeException("Can't parse the string into a Calendar type. make sure the date format is " + format + " for " + date);
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(theDate);

		return calendar;
	}

	/**
	 * Convert string to date.
	 *
	 * @param date   the date
	 * @param format the format
	 * @return the java.util. date
	 */
	public static java.util.Date convertStringToDate(String date, String format) {
		return convertStringToCal(date, format).getTime();
	}
}
