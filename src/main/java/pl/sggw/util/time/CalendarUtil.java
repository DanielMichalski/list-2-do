package pl.sggw.util.time;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Daniel
 */
public class CalendarUtil {

	public static GregorianCalendar getCalendar(Date day) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(day);
		return gregorianCalendar;
	}

	public static Date getFirstDayInCalendarPage(Date src) {
		GregorianCalendar calculationUtil = getCalendar(src);

		int firstDayNoInMonth = calculationUtil.getActualMinimum(GregorianCalendar.DAY_OF_MONTH);
		calculationUtil.set(GregorianCalendar.DAY_OF_MONTH, firstDayNoInMonth);

		Date calendarDay = calculationUtil.getTime();
		return WeekUtil.getFirstDayOfWeek(calendarDay);
	}

	public static Date getLastDayInCalendarPage(Date src) {
		GregorianCalendar calculationUtil = getCalendar(src);

		int lastDayNoInMonth = calculationUtil.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		calculationUtil.set(GregorianCalendar.DAY_OF_MONTH, lastDayNoInMonth);

		Date calendarDay = calculationUtil.getTime();
		return WeekUtil.getLastDayOfWeek(calendarDay);
	}
}
