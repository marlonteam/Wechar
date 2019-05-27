 package com.mazhe.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Comparator;

public final class DateUtil {
	public static final int DURATION_ERROR = -1;
	public static final Date INFINITE_DATE = getInfiniteDate();

	public static final String TIME_00 = "00:00:00";
	public static final String TIME_24 = "23:59:59";
	
	public static final String FORMAT_DATE = "yyyy-MM-dd";
	
	public static Date adjust(Date d, TimeZone srcZone) {
		TimeZone tgtZone = TimeZone.getDefault();
		int timeDiff = srcZone.getRawOffset() - tgtZone.getRawOffset();
		if (timeDiff == 0) {
			return d;
		}
		Calendar calendar = Calendar.getInstance(tgtZone);
		calendar.setTime(d);
		calendar.add(Calendar.MILLISECOND, timeDiff);
		return calendar.getTime();
	}

	public static Date adjust(Date d, TimeZone srcZone, TimeZone tgtZone) {
		int timeDiff = srcZone.getRawOffset() - tgtZone.getRawOffset();
		if (timeDiff == 0)
			return d;

		Calendar calendar = Calendar.getInstance(tgtZone);
		calendar.setTime(d);
		calendar.add(Calendar.MILLISECOND, timeDiff);
		return calendar.getTime();
	}

	public static Date valueOf(String date, DateFormat format) {
		if (StringUtil.isEmpty(date))
			return null;

		try {
			Date d = format.parse(date);
			if (isInfinite(d)) {
				d = INFINITE_DATE;
			}
			return d;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Date valueOf(Date date) {
		return valueOf(format(date));
	}

	public static Date valueOf(String date, String format) {
		if (StringUtil.isEmpty(date)) {
			return null;
		}
		return valueOf(date, new SimpleDateFormat(format));
	}

	public static Date valueOf(String date) {
		return valueOf(date, DateFormat.getDateInstance());
	}

	public static String format(Date date, DateFormat formater) {
		if (date == null)
			return "";

		String result = formater.format(date);
		if (isInfinite(date)) {
			result = getInfiniteDateString(formater);
		}

		return result;
	}

	public static String format(Date date, String f) {
		return format(date, new SimpleDateFormat(f));
	}

	public static String format(Date date) {
		return format(date, DateFormat.getDateInstance());
	}

	/**
	 * convert date string in format "yyyymmdd" into format "yyyy-mm-dd"
	 * 
	 * @return date string of format "yyyy-mm-dd"
	 * @param inputDate8
	 *            date string of format "yyyymmdd"
	 */
	public static String formatDate(String inputDate8) {
		String result = "";
		if (!isEmpty(inputDate8) && (inputDate8.length() == 10)) {
			result = inputDate8;
		} else if (!isEmpty(inputDate8) && (inputDate8.trim().length() == 8)) {
			result = (inputDate8.substring(0, 4) + "-"
					+ inputDate8.substring(4, 6) + "-" + inputDate8.substring(
					6, 8));
		}
		return result;
	}

	public static boolean isEmpty(String str) {
		if (str == null)
			return true;

		if (str.trim().equals(""))
			return true;

		return false;
	}

	public static String getIssueCentaryByYear(String yearCheck) {
		try {
			if (Integer.parseInt(yearCheck) >= 80)
				return "19";
			else
				return "20";
		} catch (Exception e) {
			return "00";
		}
	}

	public static int compareDate(String date1, String date2) {
		GregorianCalendar cDate1 = null;
		GregorianCalendar cDate2 = null;
		int date1a[] = new int[3];
		int date2a[] = new int[3];
		// get start date and end date
		StringTokenizer st = new StringTokenizer(date1, "/-");
		int count = 0;
		while (st.hasMoreTokens() && count < 3)
			date1a[count++] = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(date2, "/-");
		count = 0;
		while (st.hasMoreTokens() && count < 3)
			date2a[count++] = Integer.parseInt(st.nextToken());
		// convert into calendar type
		cDate1 = new GregorianCalendar(date1a[0], date1a[1] - 1, date1a[2]);
		cDate2 = new GregorianCalendar(date2a[0], date2a[1] - 1, date2a[2]);
		// convert into long
		long date1Millis = cDate1.getTime().getTime();
		long date2Millis = cDate2.getTime().getTime();
		// compare startdate with enddate
		if (date1Millis > date2Millis)
			return 1;
		if (date1Millis == date2Millis)
			return 0;
		if (date1Millis < date2Millis)
			return -1;
		return -99;
	}

	public static Timestamp getCurTimestamp() {
		return  new Timestamp(new Date().getTime());
	}
	
	public static String getCurrentDateYYYYMMDD() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	public static String getCurrent() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String getCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy/MM/dd");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String getCurrentDateSDF() {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}
	
	public static int getYearOfAge(String startDate, String endDate) {
		try {
			GregorianCalendar cStart = null;
			GregorianCalendar cEnd = null;
			int sDate[] = new int[3];
			int eDate[] = new int[3];
			int elapsed = 0;
			GregorianCalendar gc1, gc2;

			// get start date and end date
			StringTokenizer st = new StringTokenizer(startDate, "/-");
			int count = 0;
			while (st.hasMoreTokens() && count < 3)
				sDate[count++] = Integer.parseInt(st.nextToken());
			st = new StringTokenizer(endDate, "/-");
			count = 0;
			while (st.hasMoreTokens() && count < 3)
				eDate[count++] = Integer.parseInt(st.nextToken());

			// convert into calendar type
			boolean isRollDown = false;
			cStart = new GregorianCalendar(sDate[0], sDate[1] - 1, sDate[2]);
			if (cStart.isLeapYear(sDate[0]) && sDate[1] == 2 && sDate[2] == 29) {
				cStart.roll(GregorianCalendar.DATE, false);
				isRollDown = true;
			}
			cEnd = new GregorianCalendar(eDate[0], eDate[1] - 1, eDate[2]);
			if (isRollDown && cEnd.isLeapYear(eDate[0]) && eDate[1] == 2
					&& eDate[2] == 29)
				cEnd.roll(GregorianCalendar.DATE, false);

			gc1 = (GregorianCalendar) cStart.clone();
			gc2 = (GregorianCalendar) cEnd.clone();

			gc1.clear(Calendar.MILLISECOND);
			gc1.clear(Calendar.SECOND);
			gc1.clear(Calendar.MINUTE);
			gc1.clear(Calendar.HOUR_OF_DAY);

			gc2.clear(Calendar.MILLISECOND);
			gc2.clear(Calendar.SECOND);
			gc2.clear(Calendar.MINUTE);
			gc2.clear(Calendar.HOUR_OF_DAY);

			// calculate duration in year
			int year = eDate[0] - sDate[0];

			gc1.add(GregorianCalendar.YEAR, year);
			gc1.clear(Calendar.MILLISECOND);
			gc1.clear(Calendar.SECOND);
			gc1.clear(Calendar.MINUTE);
			gc1.clear(Calendar.HOUR_OF_DAY);
			gc1.clear(Calendar.HOUR);
			if (gc1.after(gc2))
				elapsed = year - 1;
			else
				elapsed = year;
			return elapsed;

		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		} catch (Throwable th) {
			th.printStackTrace();
		}
		return DURATION_ERROR;
	}

	public static String formatDate6_10(String inputDate6,
			String sConnectionString) {
		if ((!(isEmpty(inputDate6))) && (inputDate6.trim().length() == 6)) {
			if (inputDate6.substring(0, 2).compareTo("90") < 0)
				return ("20" + inputDate6.substring(0, 2) + sConnectionString
						+ inputDate6.substring(2, 4) + sConnectionString + inputDate6
							.substring(4, 6));
			else
				return ("19" + inputDate6.substring(0, 2) + sConnectionString
						+ inputDate6.substring(2, 4) + sConnectionString + inputDate6
							.substring(4, 6));
		} else {
			if (inputDate6 != null && inputDate6.trim().length() == 10)
				return inputDate6.trim();
			return "";
		}
	}

	public static String rightSubString(String sOriginalString, int iRightLength) {
		if (sOriginalString.length() <= iRightLength)
			return sOriginalString;
		else {
			return sOriginalString.substring(sOriginalString.length()
					- iRightLength);
		}
	}

	public static String format(String date, DateFormat sourceFormat,
			DateFormat targetFormat) {
		if (sourceFormat.equals(targetFormat)) {
			return date;
		}

		return format(valueOf(date, sourceFormat), targetFormat);
	}

	public static String format(String date, String sourceFormat,
			String targetFormat) {
		return format(date, new SimpleDateFormat(sourceFormat),
				new SimpleDateFormat(targetFormat));
	}

	public static String format(Date date, Locale locale) {
		return DateFormat.getDateInstance(DateFormat.LONG, locale).format(date);
	}

	public static String format(String date, DateFormat sourceFormat,
			Locale locale) {
		return format(date, sourceFormat,
				DateFormat.getDateInstance(DateFormat.LONG, locale));
	}

	public static String format(String date, String sourceFormat, Locale locale) {
		return format(date, new SimpleDateFormat(sourceFormat), locale);
	}

	public static Date dateAdvance(Date aDate, int period) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(aDate);
		gc.add(Calendar.DATE, period);
		return gc.getTime();
	}

	public static Date currentDate() {
		return valueOf(format(new Date()));
	}

	public static String currentDate(DateFormat format) {
		return format(new Date(), format);
	}

	public static String currentDate(String format) {
		return currentDate(new SimpleDateFormat(format));
	}

	public static Date dateAdvance_Month(Date date, int period) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, period);
		return c.getTime();
	}

	public static Date dateAdvance_Year(Date date, int period) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, period);
		return c.getTime();
	}

	public static Date dateAdvance_Day(Date date, int period) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, period);
		return c.getTime();
	}

	public static Date dateAdvance_WorkDay(Date date, int period) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		int leftWorkDays = 6 - c.get(Calendar.DAY_OF_WEEK);
		int leftPeriod = period - leftWorkDays;
		if (leftPeriod > 0) {
			int weeks = leftPeriod / 5 + 1;
			period += weeks * 2;
		}

		c.add(Calendar.DAY_OF_YEAR, period);
		return c.getTime();
	}

	public static int getDaysBetweenTwoDates(GregorianCalendar Date1,
			GregorianCalendar Date2) {
		if (Date1 == null || Date2 == null)
			return 0;
		long day1 = Date1.getTime().getTime();
		long day2 = Date2.getTime().getTime();
		if (day1 > day2)
			return new Long((day1 - day2) / 1000 / (60 * 60 * 24)).intValue();
		else
			return new Long((day2 - day1) / 1000 / (60 * 60 * 24)).intValue();
	}

	public static int daysBetween(Date d1, Date d2) {
		if (d1 == null || d2 == null)
			throw new IllegalArgumentException("Date can not be null.");

		return (int) (Math.abs(d1.getTime() - d2.getTime()) / (1000 * 60 * 60 * 24));
	}

	public static int yearsBetween(Date d1, Date d2) {
		if (d1 == null || d2 == null)
			throw new IllegalArgumentException("Date can not be null.");

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(d1);
		c2.setTime(d2);

		return Math.abs(c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR));
	}
	
	public static int MonthBetween(Date d1, Date d2) {
		if (d1 == null || d2 == null)
			throw new IllegalArgumentException("Date can not be null.");

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(d1);
		c2.setTime(d2);
		int year =c2.get(Calendar.YEAR)-c1.get(Calendar.YEAR);
		//开始日期若小月结束日期
		if(year<0){
		year=-year;
		return year*12+c1.get(Calendar.MONTH)-c2.get(Calendar.MONTH);
		}
		return year*12+c2.get(Calendar.MONTH)-c1.get(Calendar.MONTH);
	}

	public static int daysBetween(Calendar d1, Calendar d2) {
		return daysBetween(d1.getTime(), d2.getTime());
	}

	public static String formatDateToStringCHN(Date date) {
		String chnDate = format(date, new SimpleDateFormat("yyyy/MM/dd"));
		chnDate = chnDate.substring(0, 4) + "��" + chnDate.substring(5, 7)
				+ "��" + chnDate.substring(8) + "��";
		return chnDate;
	}

	public static boolean isInfinite(Date date) {
		if (date == null)
			return false;

		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			return date.after(format.parse("9000-01-01"));
		} catch (Exception e) {
			return false;
		}
	}

	@Deprecated
	public static boolean isForever(Date date) {
		if (date == null) {
			return false;
		}

		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			return date.after(format.parse("9000-01-01"));
		} catch (Exception e) {
			return false;
		}
	}

	private static Date getInfiniteDate() {
		try {
			return (new SimpleDateFormat("yyyy-MM-dd")).parse("9999-12-31");
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	private static String getInfiniteDateString(DateFormat f) {
		try {
			Date d = INFINITE_DATE;

			String s = f.format(d);
			s = s.replaceAll("12", "99");

			s = s.replaceAll("31", "99");
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String formatMonthDayString(int dayOrMonth) {
		if (String.valueOf(dayOrMonth).length() < 2)
			return "0" + String.valueOf(dayOrMonth);
		return String.valueOf(dayOrMonth);

	}

	public static String formatDate10_8(String inputDate10) {

		if ((!(isEmpty(inputDate10))) && (inputDate10.trim().length() == 10)) {
			String month = null;
			String tmpMonth = String.valueOf(Integer.parseInt(inputDate10
					.substring(5, 7)));
			if (tmpMonth.length() < 2)
				month = ("0" + tmpMonth).substring(0, 2);
			else
				month = ("0" + tmpMonth).substring(1, 3);

			return (inputDate10.substring(0, 4) + month + inputDate10
					.substring(8, 10));

		} else {
			if (inputDate10 != null && inputDate10.trim().length() == 8)
				return inputDate10.trim();
			return "";
		}
	}

	/**
	 * 输入一个日期的时间段，以及相应的星期数，获得这些星期的日期
	 */
	private static Map weekNumberMap = new HashMap();
	static {
		weekNumberMap.put(0, 1);
		weekNumberMap.put(1, 2);
		weekNumberMap.put(2, 3);
		weekNumberMap.put(3, 4);
		weekNumberMap.put(4, 5);
		weekNumberMap.put(5, 6);
		weekNumberMap.put(6, 7);
	}

	public static List<String> getDatesByString(String startDate, String endDate,
			String weekDays, String specialDateList) throws ParseException {
	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d1 = DateUtil.valueOf(startDate, sdf);
		Date d2 = DateUtil.valueOf(endDate, sdf);
		
		return DateUtil.getDates(d1, d2, weekDays, specialDateList);
		
	}
	
	
	public static List<String> getDates(Date startDate, Date endDate,
			String weekDays, String specialDateList) throws ParseException {

		long time;
		long perDayMilSec = 24L * 60 * 60 * 1000;
		List<String> dateList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null && endDate != null) {

			String dateFrom = sdf.format(startDate);
			String dateEnd = sdf.format(endDate);
			// 需要查询的星期系数
			// String strWeekNumber = weekForNum(weekDays);
			String strWeekNumber = weekForNum(weekDays);
			try {
				dateFrom = sdf.format(sdf.parse(dateFrom).getTime()
						- perDayMilSec);
				while (true) {
					time = sdf.parse(dateFrom).getTime();
					time = time + perDayMilSec;
					Date date = new Date(time);
					dateFrom = sdf.format(date);
					if (dateFrom.compareTo(dateEnd) <= 0) {
						// 查询的某一时间的星期系数
						Integer weekDay = dayForWeek(date);
						// 判断当期日期的星期系数是否是需要查询的
						if (strWeekNumber.contains(weekDay.toString())) {
							dateList.add(dateFrom);
						}
					} else {
						break;
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (specialDateList != null && !("").equals(specialDateList)) {
			String[] specialDateArray = specialDateList.split(",");
			List<String> specialDate = new ArrayList<String>();
			for (String sd : specialDateArray) {

				specialDate.add(sd);

			}
			dateList.removeAll(specialDate);
			dateList.addAll(specialDate);
			ComparatorDate c = new ComparatorDate();
			Collections.sort(dateList, c);

		}

		return dateList;
	}

	/*
	 * public static List getDates(String dateFrom, String dateEnd, List
	 * weekDays) { long time; long perDayMilSec = 24L * 60 * 60 * 1000; List
	 * dateList = new ArrayList(); SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd"); // 需要查询的星期系数 String strWeekNumber =
	 * weekForNum(weekDays); try { dateFrom =
	 * sdf.format(sdf.parse(dateFrom).getTime() - perDayMilSec); while (true) {
	 * time = sdf.parse(dateFrom).getTime(); time = time + perDayMilSec; Date
	 * date = new Date(time); dateFrom = sdf.format(date); if
	 * (dateFrom.compareTo(dateEnd) <= 0) { // 查询的某一时间的星期系数 Integer weekDay =
	 * dayForWeek(date); // 判断当期日期的星期系数是否是需要查询的 if
	 * (strWeekNumber.contains(weekDay.toString())) { dateList.add(dateFrom); }
	 * } else { break; } } } catch (ParseException e) { e.printStackTrace(); }
	 * return dateList; }
	 */
	// 等到当期时间的周系数。星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7
	public static Integer dayForWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 得到对应星期的系数 0：1，星期
	 */
	/*
	 * public static String weekForNum(List<String> weekDays) { // 返回结果为组合的星期系数
	 * String weekNumber = ""; // for (Object weekDay:weekDays) { // weekNumber
	 * = weekNumber + "" + getWeekNum((Integer)weekDay).toString(); for (String
	 * weekDay:weekDays) { weekNumber = weekNumber + "" +
	 * getWeekNum(Integer.parseInt(weekDay)).toString(); } return weekNumber; }
	 * 
	 * 
	 * 
	 * 
	 * // 将星期转换为对应的系数 0,星期日; 1,星期一; 2.... public static Integer getWeekNum(int
	 * strWeek) { return (Integer)(weekNumberMap.get(strWeek)); }
	 */

	public static String weekForNum(String weekDays) {

		String weekNumber = "";
		if(!("").equals(weekDays)&&weekDays!=null){
		//if (weekDays != "" && weekDays != null) {
			if (weekDays.indexOf(",") != -1) {
				String[] strWeeks = weekDays.split(",");
				for (int i = 0; i < strWeeks.length; i++) {
					weekNumber = weekNumber + ""
							+ getWeekNum(strWeeks[i].toString());
				}
			} else {
				weekNumber = getWeekNum(weekDays).toString();
			}
		}
		return weekNumber;

	}

	public static Integer getWeekNum(String strWeek) {
		Integer number = 0;
		if ("0".equals(strWeek)) {
			number = 1;
		} else if ("1".equals(strWeek)) {
			number = 2;
		} else if ("2".equals(strWeek)) {
			number = 3;
		} else if ("3".equals(strWeek)) {
			number = 4;
		} else if ("4".equals(strWeek)) {
			number = 5;
		} else if ("5".equals(strWeek)) {
			number = 6;
		} else if ("6".equals(strWeek)) {
			number = 7;
		}
		return number;
	}

	/*
	 * public static Integer getWeekNum(String strWeek) { Integer number = 1; if
	 * ("星期日".equals(strWeek)) { number = 1; } else if ("星期一".equals(strWeek)) {
	 * number = 2; } else if ("星期二".equals(strWeek)) { number = 3; } else if
	 * ("星期三".equals(strWeek)) { number = 4; } else if ("星期四".equals(strWeek)) {
	 * number = 5; } else if ("星期五".equals(strWeek)) { number = 6; } else if
	 * ("星期六".equals(strWeek)) { number = 7; } return number; }
	 */
	/** 
     * 获得指定日期的前一天 
     *  
     * @param specifiedDay 
     * @return 
     * @throws Exception 
     */  
    public static String getSpecifiedDayBefore(String specifiedDay) {//可以用new Date().toLocalString()传递参数  
        Calendar c = Calendar.getInstance();  
        Date date = null;  
        try {  
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        c.setTime(date);  
        int day = c.get(Calendar.DATE);  
        c.set(Calendar.DATE, day - 1);  
  
        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c  
                .getTime());  
        return dayBefore;  
    }  
  
    /** 
     * 获得指定日期的后一天 
     *  
     * @param specifiedDay 
     * @return 
     */  
    public static String getSpecifiedDayAfter(String specifiedDay) {  
        Calendar c = Calendar.getInstance();  
        Date date = null;  
        try {  
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        c.setTime(date);  
        int day = c.get(Calendar.DATE);  
        c.set(Calendar.DATE, day + 1);  
  
        String dayAfter = new SimpleDateFormat("yyyy-MM-dd")  
                .format(c.getTime());  
        return dayAfter;  
    }  

}

class ComparatorDate implements Comparator<String> {
	@Override
	public int compare(String date1, String date2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date begin = sdf.parse(date1);
			Date end = sdf.parse(date2);
			if (begin.after(end)) {
				return 1;
			} else {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

		

	}
	

	
}

