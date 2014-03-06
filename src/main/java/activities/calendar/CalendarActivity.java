package pl.sggw.activities.calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import pl.sggw.R;
import pl.sggw.activities.calendar.logic.CalendarPresenter;
import pl.sggw.activities.calendar.ui.CalendarView;
import pl.sggw.activities.calendar.ui.GridCellAdapter;
import pl.sggw.activities.calendar.ui.GridHeaderAdapter;
import pl.sggw.activities.calendar.logic.WeekdayCell;
import pl.sggw.activities.calendar.ui.NumericWheelAdapter;
import pl.sggw.widget.wheel.WheelView;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Daniel
 * @since 0.0.1
 */

public class CalendarActivity extends RoboActivity implements CalendarView{

	private static final String RESTORE_DATE_KEY = "saved_date_key";

	public static final String EXTRA_DATE_FOR_EDIT = "extra_date_for_edit";

	public static final String RESULT_DATE = "result_date";

    private static final String FORMAT_SCROLL_PICKER = "%02d";

	@InjectView(R.id.previous_month_button)
	private Button btnPrevMonth;

	@InjectView(R.id.next_month_button)
	private Button btnNextMonth;

	@InjectView(R.id.current_month_text_view)
	private TextView tvMonth;

	@InjectView(R.id.current_year_text_view)
	private TextView tvYear;

	@InjectView(R.id.calendar_weekdays_header_grid_view)
	private GridView viewCalendarHeader;

	@InjectView(R.id.calendar_dates_grid_view)
	private GridView viewCalendarCells;

	@InjectView(R.id.hour_wheel_view)
	private WheelView hourWheelView;

	@InjectView(R.id.minute_wheel_view)
	private WheelView minuteWheelView;

    @InjectView(R.id.buttonSave)
    private Button btnPositive;

    @InjectView(R.id.buttonCancel)
    private Button btnNegative;

	private CalendarPresenter presenter;

	private GridHeaderAdapter headerAdapter;

	private GridCellAdapter cellsAdapter;

	private NumericWheelAdapter hourAdapter;

	private NumericWheelAdapter minuteAdapter;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Date dateWithTime;
		if (savedInstanceState != null) {
			dateWithTime = (Date) savedInstanceState.get(RESTORE_DATE_KEY);
		} else {
			dateWithTime = getDateFromIntent(EXTRA_DATE_FOR_EDIT);
		}

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		presenter = new CalendarPresenter(this);
		setContentView(R.layout.calendar_layout);

		setupContent();
		presenter.initializeWith(this, dateWithTime);
	}

	private void initTimeWheel(WheelView wheelView, NumericWheelAdapter adapter) {
		wheelView.setViewAdapter(adapter);
		wheelView.setCyclic(true);
		wheelView.setInterpolator(new AnticipateOvershootInterpolator());
	}

	private Date getDateFromIntent(String key) {
		Bundle bundle = getIntent().getExtras();
		return (Date) bundle.getSerializable(key);
	}

	private void setupContent() {
		headerAdapter = new GridHeaderAdapter(this);
        viewCalendarHeader.setAdapter(headerAdapter);

        cellsAdapter = new GridCellAdapter(this);
        viewCalendarCells.setAdapter(cellsAdapter);

        hourAdapter = new NumericWheelAdapter(this, 0, 23, FORMAT_SCROLL_PICKER);
        minuteAdapter = new NumericWheelAdapter(this, 0, 59, FORMAT_SCROLL_PICKER);
        initTimeWheel(hourWheelView, hourAdapter);
        initTimeWheel(minuteWheelView, minuteAdapter);

        btnPositive.setText(R.string.btn_name_ok);
        btnNegative.setVisibility(View.GONE);
    }

	@Override
	public void populateCells(List<WeekdayCell> weekdayCells) {
		cellsAdapter.refreshItems(weekdayCells);
	}

	@Override
	public void populateCalendarBar(String month, int year) {
		tvMonth.setText(month);
		tvYear.setText(" " + year);
	}

	@Override
	public void populateTimePicker(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		hourWheelView.setCurrentItem(hour);
		minuteWheelView.setCurrentItem(min);
	}

	@Override
	public void selectCellBy(Date date) {
		cellsAdapter.selectCellBy(date);
	}

	@Override
	public void setListeners() {
        btnPositive.setOnClickListener(presenter);
		btnPrevMonth.setOnClickListener(presenter);
		btnNextMonth.setOnClickListener(presenter);
		viewCalendarCells.setOnItemClickListener(presenter);
	}

    @Override
    public int getHour() {
        return hourAdapter.getCurrentValue();
    }

    @Override
    public int getMinutes() {
        return minuteAdapter.getCurrentValue();
    }

    @Override
    public void finishWithResult(Intent result) {
        setResult(Activity.RESULT_OK, result);
        finish();
    }

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(RESTORE_DATE_KEY, presenter.getDueDateWithTime());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState != null) {
			Date date = (Date) savedInstanceState.get(RESTORE_DATE_KEY);
			populateTimePicker(date);
		}
	}
}