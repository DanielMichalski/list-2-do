package pl.sggw.util.time;

import java.util.*;

/**
 * @author Daniel
 */
public class WeekUtil {

	private static GregorianCalendar calendar = new GregorianCalendar();

	public static List<WeekDay> getWeekdays() {
		List<WeekDay> weekdays = new ArrayList<WeekDay>();
		WeekDay firstDayOfWeek = getFirstWeekDay();
		WeekDay weekDay = getFirstWeekDay();
		do {
			weekdays.add(weekDay);
			weekDay = weekDay.next();
		} while (!firstDayOfWeek.equals(weekDay));

		return weekdays;
	}

	public static List<Date> getDaysForWeek(Date src) {
		List<Date> daysForWeek = new ArrayList<Date>();
		Date firstDayOfWeek = getFirstDayOfWeek(src);
		Date lastDayOfWeek = getLastDayOfWeek(src);
		GregorianCalendar calendar = CalendarUtil.getCalendar(firstDayOfWeek);
		Date calendarDay;
		while (calendar.getTime().compareTo(lastDayOfWeek) != 1) {
			calendarDay = calendar.getTime();
			daysForWeek.add(calendarDay);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return daysForWeek;
	}

	public static Date getFirstDayOfWeek(Date src) {
		GregorianCalendar calculationCalendar = CalendarUtil.getCalendar(src);
		int firstDayOfWeek = getFirstWeekDay().getId();
		while (calculationCalendar.get(Calendar.DAY_OF_WEEK) != firstDayOfWeek) {
			calculationCalendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		return calculationCalendar.getTime();
	}

	public static Date getLastDayOfWeek(Date src) {
		GregorianCalendar calculationCalendar = CalendarUtil.getCalendar(src);
		int lastDayOfWeek = getLastWeekDay().getId();
		while (calculationCalendar.get(Calendar.DAY_OF_WEEK) != lastDayOfWeek) {
			calculationCalendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return calculationCalendar.getTime();
	}

	public static WeekDay getLastWeekDay() {
		int firstDayOfWeek = calendar.getFirstDayOfWeek();
		WeekDay weekDay = WeekDay.valueOf(firstDayOfWeek);
		return weekDay.prev();
	}

	public static WeekDay getFirstWeekDay() {
		int firstDayOfWeek = calendar.getFirstDayOfWeek();
		return WeekDay.valueOf(firstDayOfWeek);
	}
}
