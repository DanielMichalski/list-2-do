package pl.sggw.activities.calendar.ui;

import android.content.Intent;
import pl.sggw.activities.calendar.logic.WeekdayCell;

import java.util.Date;
import java.util.List;

/**
 * @author Lukasz Strzelecki
 * @since 0.0.1
 */
public interface CalendarView {

	void populateCells(List<WeekdayCell> weekdayCells);

	void populateCalendarBar(String month, int year);

	void populateTimePicker(Date date);

	void selectCellBy(Date date);

	void setListeners();

    void finishWithResult(Intent result);

    int getHour();

    int getMinutes();

}
