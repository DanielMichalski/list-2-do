package pl.sggw.task;

import android.content.Context;
import pl.sggw.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Daniel
 * @date 30.10.12
 */
public enum ReminderType {
	OFF {
		@Override
		public String getLabel(Context context) {
			return context.getString(R.string.dialog_lbl_off);
		}

		@Override
		public Long getDifferentTimeInMs() {
			return null;
		}
	},
	ON_TIME {
		@Override
		public String getLabel(Context context) {
			return context.getString(R.string.dialog_lbl_on_time);
		}

		@Override
		public Long getDifferentTimeInMs() {
			return 0L;
		}
	},
	FIVE_MIN_BEFORE {
		@Override
		public String getLabel(Context context) {
			return context.getString(R.string.dialog_lbl_five_min_before);
		}

		@Override
		public Long getDifferentTimeInMs() {
			return 1000L * 60L * 5L;
		}
	},
	TEN_MIN_BEFORE {
		@Override
		public String getLabel(Context context) {
			return context.getString(R.string.dialog_lbl_ten_min_before);
		}

		@Override
		public Long getDifferentTimeInMs() {
			return 1000L * 60L * 10L;
		}
	},
	THIRTY_MIN_BEFORE {
		@Override
		public String getLabel(Context context) {
			return context.getString(R.string.dialog_lbl_thirty_min_before);
		}

		@Override
		public Long getDifferentTimeInMs() {
			return 1000L * 60L * 30L;
		}
	},
	ONE_HOUR_BEFORE {
		@Override
		public String getLabel(Context context) {
			return context.getString(R.string.dialog_lbl_one_hour_before);
		}

		@Override
		public Long getDifferentTimeInMs() {
			return 1000L * 60L * 60L;
		}
	};

	public abstract String getLabel(Context context);

	public abstract Long getDifferentTimeInMs();

	public static List<ReminderType> asList() {
		List<ReminderType> reminderTypes = new ArrayList<ReminderType>();
		for (ReminderType reminderType : values()) {
			reminderTypes.add(reminderType);
		}
		return reminderTypes;
	}

	public static ReminderType valueOf(Date dueDate, Date reminderDate) {
		ReminderType reminderType = ReminderType.OFF;
		if (reminderDate != null) {
			Long different = dueDate.getTime() - reminderDate.getTime();
			for (ReminderType reminder : values()) {
				if (different.equals(reminder.getDifferentTimeInMs())) {
					return reminder;
				}
			}
		}
		return reminderType;
	}
}
