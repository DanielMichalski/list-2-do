package pl.sggw.util.time;

import android.content.Context;
import pl.sggw.R;

/**
 * @author Daniel
 */
public enum WeekDay {
	MON(2) {
		@Override
		public WeekDay next() {
			return TUE;
		}

		@Override
		public WeekDay prev() {
			return SUN;
		}

		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_day_lbl_monday);
		}
	}, TUE(3) {
		@Override
		public WeekDay next() {
			return WED;
		}

		@Override
		public WeekDay prev() {
			return MON;
		}

		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_day_lbl_tuesday);
		}
	}, WED(4) {
		@Override
		public WeekDay next() {
			return THU;
		}

		@Override
		public WeekDay prev() {
			return TUE;
		}

		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_day_lbl_wednesday);
		}
	}, THU(5) {
		@Override
		public WeekDay next() {
			return FRI;
		}

		@Override
		public WeekDay prev() {
			return WED;
		}

		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_day_lbl_thursday);
		}
	}, FRI(6) {
		@Override
		public WeekDay next() {
			return SAT;
		}

		@Override
		public WeekDay prev() {
			return THU;
		}

		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_day_lbl_friday);
		}
	}, SAT(7) {
		@Override
		public WeekDay next() {
			return SUN;
		}

		@Override
		public WeekDay prev() {
			return FRI;
		}

		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_day_lbl_saturday);
		}
	}, SUN(1) {
		@Override
		public WeekDay next() {
			return MON;
		}

		@Override
		public WeekDay prev() {
			return SAT;
		}

		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_day_lbl_sunday);
		}
	};

	private int id;

	private WeekDay(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public abstract WeekDay next();

	public abstract WeekDay prev();

	public abstract String getShortName(Context ctx);

	public static WeekDay valueOf(int id) {
		for (WeekDay weekDay : values()) {
			if (weekDay.id == id) {
				return weekDay;
			}
		}
		throw new IllegalArgumentException("Couldn't find weekday for " + id);
	}

}
