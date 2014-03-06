package pl.sggw.util.time;

import android.content.Context;

import java.util.GregorianCalendar;

/**
 * @author Daniel
 */
public class MonthUtil {

	private static GregorianCalendar calendar = new GregorianCalendar();

	public static String getMonthNameBy(Context context, int calendarMonthNo) {
		Month month = Month.valueOf(calendarMonthNo);
		return month.getName(context);
	}

	public static String getMonthShortNameBy(Context context, int calendarMonthNo) {
		Month month = Month.valueOf(calendarMonthNo);
		return month.getShortName(context);
	}
}
