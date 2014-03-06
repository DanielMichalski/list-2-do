package pl.sggw.activities.calendar.ui;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import pl.sggw.activities.calendar.logic.WeekdayCell;
import pl.sggw.util.time.DateUtil;
import pl.sggw.widget.RefreshableAdapter;

import java.util.Collections;
import java.util.Date;

/**
 * @author Daniel
 */
public class GridCellAdapter extends RefreshableAdapter<WeekdayCell> {

	public GridCellAdapter(Context context) {
		super(context, Collections.<WeekdayCell>emptyList());
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public WeekdayCell getItem(int position) {
		return items.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CalendarCellView cellView;
		if (convertView == null) {
			cellView = new CalendarCellView(context);
			convertView = cellView;
		} else {
			cellView = (CalendarCellView) convertView;
		}

		WeekdayCell weekdayCell = getItem(position);
		cellView.fill(weekdayCell);

		return convertView;
	}

	public void selectCellBy(Date date){
		Date selectDay = DateUtil.resetTime(date);
		for(WeekdayCell weekdayCell : items){
			Date day = weekdayCell.getDay();
			weekdayCell.setSelected(day.equals(selectDay));
		}
		refreshItems(items);
	}

}