package pl.sggw.activities.calendar.ui;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;
import pl.sggw.R;
import pl.sggw.activities.calendar.logic.WeekdayCell;
import pl.sggw.activities.calendar.logic.WeekdayCellType;

public class CalendarCellView extends TextView {

	public CalendarCellView(Context context) {
		super(context);
		setup();
	}

	private void setup() {
		int cellHeight = getContext().getResources().getDimensionPixelSize(R.dimen.height_calendar_cell);
		setMinHeight(cellHeight);
		setGravity(Gravity.CENTER);
		setTextAppearance(getContext(), android.R.attr.textAppearanceMedium);
		setTextSize(getContext().getResources().getDimension(R.dimen.text_size_calendar_cell));
		setBackgroundResource(R.drawable.calendar_default_cell);
	}

	public void fill(WeekdayCell weekday) {
		setFocusable(weekday.isFocus());
		setText("" + weekday.getDayNo());
		WeekdayCellType cellType = weekday.getCellType();
		setTextColor(cellType.getColorText(getContext(), weekday.getDay(), weekday.isSelected()));
		setBackgroundResource(cellType.getBackgroundResId(getContext(), weekday.isSelected()));
	}
}
