package pl.sggw.activities.calendar.logic;

import java.util.Date;

/**
 * @author Daniel
 */
public class WeekdayCell {

	private Date day;

	private WeekdayCellType cellType;

	private boolean selected;

	private boolean focus;

	public WeekdayCell(Date day) {
		this.day = day;
		cellType = WeekdayCellType.FROM_CURRENT_MONTH;
		selected = false;
		focus = false;
	}

	public void setType(Date today, int displayMonth) {
		if (day.getMonth() != displayMonth) {
			cellType = WeekdayCellType.FROM_ANOTHER_MONTH;
		} else if (day.equals(today)) {
			cellType = WeekdayCellType.TODAY;
		} else if (day.getMonth() == displayMonth) {
			cellType = WeekdayCellType.FROM_CURRENT_MONTH;
		}

		focus = cellType.equals(WeekdayCellType.FROM_ANOTHER_MONTH) || day.before(today);
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Date getDay() {
		return day;
	}

	public WeekdayCellType getCellType() {
		return cellType;
	}

	public boolean isSelected() {
		return selected;
	}

	public boolean isFocus() {
		return focus;
	}

	public int getDayNo() {
		return day.getDate();
	}

	@Override
	public String toString() {
		return "WeekdayCell{" +
				"day=" + day +
				", selected=" + selected +
				", cellType=" + cellType +
				'}';
	}
}