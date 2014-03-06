package pl.sggw.activities.calendar.logic;

import android.content.Context;
import android.graphics.Color;
import pl.sggw.R;
import pl.sggw.util.time.DateUtil;

import java.util.Date;

/**
 * @author Daniel
 */
public enum WeekdayCellType {
	TODAY {
		@Override
		public int getColorText(Context ctx, Date day, boolean isSelected) {
			int colorText;
			if (isSelected) {
				colorText = Color.WHITE;
			} else {
				colorText = ctx.getResources().getColor(R.color.calendar_today);
			}
			return colorText;
		}

		@Override
		public int getBackgroundResId(Context ctx, boolean isSelected) {
			int backgroundResId;
			if (isSelected) {
				backgroundResId = R.drawable.calendar_selected_cell;
			} else {
				backgroundResId = R.drawable.calendar_default_cell;
			}
			return backgroundResId;
		}

	},
	FROM_CURRENT_MONTH {
		@Override
		public int getColorText(Context ctx, Date day, boolean isSelected) {
			int colorText;
			if (day.before(DateUtil.today())) {
				colorText = Color.DKGRAY;
			} else if (isSelected) {
				colorText = Color.WHITE;
			} else {
				colorText = Color.LTGRAY;
			}
			return colorText;
		}

		@Override
		public int getBackgroundResId(Context ctx, boolean isSelected) {
			int backgroundResId;
			if (isSelected) {
				backgroundResId = R.drawable.calendar_selected_cell;
			} else {
				backgroundResId = R.drawable.calendar_default_cell;
			}
			return backgroundResId;
		}
	}, FROM_ANOTHER_MONTH {
		@Override
		public int getColorText(Context ctx, Date day, boolean isSelected) {
			return Color.TRANSPARENT;
		}

		@Override
		public int getBackgroundResId(Context ctx, boolean isSelected) {
			return R.drawable.calendar_default_cell;
		}
	};

	abstract public int getColorText(Context ctx, Date day, boolean isSelected);

	abstract public int getBackgroundResId(Context ctx, boolean isSelected);

}