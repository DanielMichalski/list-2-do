package pl.sggw.activities.calendar.logic;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import pl.sggw.R;
import pl.sggw.activities.calendar.CalendarActivity;
import pl.sggw.activities.calendar.ui.CalendarView;
import pl.sggw.util.time.CalendarUtil;
import pl.sggw.util.time.DateUtil;
import pl.sggw.util.time.MonthUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Daniel
 * @since 0.0.1
 */
public class CalendarPresenter implements View.OnClickListener, AdapterView.OnItemClickListener {

	private Context ctx;

	private GregorianCalendar calendar;

	private WeekdayCellsFactory weekdayCellsFactory;

	private CalendarView view;

	private Date dueDate;

	public CalendarPresenter(Context ctx) {
		this.ctx = ctx;
		calendar = CalendarUtil.getCalendar(DateUtil.today());
		weekdayCellsFactory = new WeekdayCellsFactory();
	}

	public void initializeWith(CalendarView view, Date dueDate) {
		this.view = view;
		this.dueDate = dueDate;
		calendar.setTime(DateUtil.resetTime(dueDate));

		view.populateCalendarBar(getDisplayMonth(), getDisplayYear());
		view.populateCells(getWeekdayCells());
		view.populateTimePicker(dueDate);
		view.setListeners();
	}

    public Date getDueDateWithTime() {
        Date dueDateWithTime = DateUtil.resetTime(dueDate);
        dueDateWithTime.setHours(view.getHour());
        dueDateWithTime.setMinutes(view.getMinutes());
        return dueDateWithTime;
    }

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		WeekdayCell weekdayCell = (WeekdayCell) adapterView.getItemAtPosition(position);
		Date cellDay = weekdayCell.getDay();

		dueDate.setDate(cellDay.getDate());
		dueDate.setMonth(cellDay.getMonth());
		dueDate.setYear(cellDay.getYear());
		this.view.selectCellBy(dueDate);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.previous_month_button:
				gotoPreviousMonth();
				break;
			case R.id.next_month_button:
				gotoNextMonth();
				break;
            case R.id.buttonSave:
                Intent result = new Intent();
                result.putExtra(CalendarActivity.RESULT_DATE, getDueDateWithTime());
                view.finishWithResult(result);
                break;
		}
	}

	public void gotoPreviousMonth() {
		calendar.add(Calendar.MONTH, -1);
		view.populateCells(getWeekdayCells());
		view.populateCalendarBar(getDisplayMonth(), getDisplayYear());
	}

	public void gotoNextMonth() {
		calendar.add(Calendar.MONTH, 1);
		view.populateCells(getWeekdayCells());
		view.populateCalendarBar(getDisplayMonth(), getDisplayYear());
	}

	private List<WeekdayCell> getWeekdayCells() {
		weekdayCellsFactory.setRangeForCalendarPageWith(calendar.getTime());
		weekdayCellsFactory.actualizeDisplayMonth(calendar);
		return weekdayCellsFactory.createWeekdaysFor(dueDate);
	}

	private String getDisplayMonth() {
		int calendarMonthNo = calendar.get(Calendar.MONTH);
		return MonthUtil.getMonthNameBy(ctx, calendarMonthNo);
	}

	private int getDisplayYear() {
		return calendar.get(Calendar.YEAR);
	}

}