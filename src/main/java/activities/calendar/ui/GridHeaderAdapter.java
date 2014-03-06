package pl.sggw.activities.calendar.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import pl.sggw.R;
import pl.sggw.util.time.WeekDay;
import pl.sggw.util.time.WeekUtil;

import java.util.List;

/**
 * @author Daniel
 */
public class GridHeaderAdapter extends BaseAdapter {

	private final Context context;

	private List<WeekDay> weekDays;

	public GridHeaderAdapter(Context context) {
		super();
		this.context = context;
		weekDays = WeekUtil.getWeekdays();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getCount() {
		return weekDays.size();
	}

	@Override
	public WeekDay getItem(int position) {
		return weekDays.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tvShortWeekday;
		if (convertView == null) {
			tvShortWeekday = createWeekdayTextView();
		} else {
			tvShortWeekday = (TextView) convertView;
		}

		WeekDay weekDay = getItem(position);
		tvShortWeekday.setText(weekDay.getShortName(context));
		return tvShortWeekday;
	}

	private TextView createWeekdayTextView() {
		TextView textView = new TextView(context);
		textView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
		textView.setGravity(Gravity.CENTER);
		int headerPadding = context.getResources().getDimensionPixelSize(R.dimen.padding_calendar_header);
		textView.setPadding(headerPadding, headerPadding, headerPadding, headerPadding);
		textView.setTextAppearance(context, android.R.attr.textAppearanceSmall);
		textView.setTextColor(Color.WHITE);
		textView.setBackgroundColor(Color.BLACK);
		textView.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.text_size_calendar_cell));
		textView.setFocusable(true);
		return textView;
	}

}
