package pl.sggw.task;

import android.content.Context;
import pl.sggw.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 * @date 30.10.12
 */
public enum RepeatType {
	ONLY_ONCE {
		@Override
		public String getLabel(Context context) {
			return context.getString(R.string.dialog_lbl_only_once);
		}
	},
	ONCE_A_DAY {
		@Override
		public String getLabel(Context context) {
			return context.getString(R.string.dialog_lbl_once_a_day);
		}
	},
	ONCE_A_WEEK {
		@Override
		public String getLabel(Context context) {
			return context.getString(R.string.dialog_lbl_once_a_week);
		}
	},
	ONCE_A_MONTH {
		@Override
		public String getLabel(Context context) {
			return context.getString(R.string.dialog_lbl_once_a_month);
		}
	};

	public abstract String getLabel(Context context);

	public static List<RepeatType> asList() {
		List<RepeatType> repeatTypes = new ArrayList<RepeatType>();
		for (RepeatType repeatType: values()) {
			repeatTypes.add(repeatType);
		}
		return repeatTypes;
	}
}
