package pl.sggw.activities.calendar.logic;

import pl.sggw.util.time.CalendarUtil;
import pl.sggw.util.time.DateUtil;

import java.util.*;

/**
 * @author Daniel
 * @since 0.0.2
 */
public class WeekdayCellsFactory {

	private static final int AMOUNT_CELLS = 7 * 6;

	private Date today;

	private int displayCalendarMonthNo;

	private Date firstPageDay;

	public WeekdayCellsFactory() {
		today = DateUtil.today();
	}

	public void setRangeForCalendarPageWith(Date src) {
		firstPageDay = CalendarUtil.getFirstDayInCalendarPage(src);
	}

	public List<WeekdayCell> createWeekdaysFor(Date selectDate) {
		selectDate = DateUtil.resetTime(selectDate);
		List<WeekdayCell> weekdayCells = new ArrayList<WeekdayCell>();

		GregorianCalendar calendar = CalendarUtil.getCalendar(firstPageDay);
		Date calendarDay;
		int cellCounter = 0;
		while (cellCounter != AMOUNT_CELLS) {
			calendarDay = calendar.getTime();
			WeekdayCell weekdayCell = new WeekdayCell(calendarDay);
			weekdayCell.setType(today, displayCalendarMonthNo);
			weekdayCell.setSelected(calendarDay.equals(selectDate));
			weekdayCells.add(weekdayCell);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			cellCounter++;
		}

		return weekdayCells;
	}

	public void actualizeDisplayMonth(GregorianCalendar gregorianCalendar) {
		displayCalendarMonthNo = gregorianCalendar.get(Calendar.MONTH);
	}
}
