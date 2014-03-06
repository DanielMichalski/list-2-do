package pl.sggw.task;

import android.content.Context;
import android.graphics.Color;
import pl.sggw.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 * @date 30.10.12
 */
public enum PriorityType {

	NONE {

		@Override
		public String getLabel(Context context) {
			return context.getString(R.string.dialog_lbl_none);
		}

		@Override
		public int getBackgroundColor(Context context) {
			return Color.TRANSPARENT;
		}

		@Override
		public Integer getValue() {
			return 0;
		}
	},
	LOW {

		@Override
		public String getLabel(Context context) {
			return context.getString(R.string.dialog_lbl_low);
		}

		@Override
		public int getBackgroundColor(Context context) {
			return context.getResources().getColor(R.color.low_priority_background);
		}

		@Override
		public Integer getValue() {
			return 1;
		}
	},
	MEDIUM {

		@Override
		public String getLabel(Context context) {
			return context.getString(R.string.dialog_lbl_medium);
		}

		@Override
		public int getBackgroundColor(Context context) {
			return context.getResources().getColor(R.color.medium_priority_background);
		}

		@Override
		public Integer getValue() {
			return 2;
		}
	},
	HIGH {

		@Override
		public String getLabel(Context context) {
			return context.getString(R.string.dialog_lbl_height);
		}

		@Override
		public int getBackgroundColor(Context context) {
			return context.getResources().getColor(R.color.height_priority_background);
		}

		@Override
		public Integer getValue() {
			return 3;
		}
	};

	public abstract String getLabel(Context context);

	public abstract int getBackgroundColor(Context context);

	public abstract Integer getValue();

	public static List<PriorityType> asList() {
		List<PriorityType> priorityTypes = new ArrayList<PriorityType>();
		for (PriorityType priorityType: values()) {
			priorityTypes.add(priorityType);
		}
		return priorityTypes;
	}
}
