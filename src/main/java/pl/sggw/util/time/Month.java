package pl.sggw.util.time;

import android.content.Context;
import pl.sggw.R;

/**
 * @author Daniel
 */
public enum Month {

	JANUARY(0) {
		@Override
		public String getName(Context ctx) {
			return ctx.getString(R.string.calendar_month_lbl_january);
		}

		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_month_short_lbl_january);
		}
	}, FEBRUARY(1) {
		@Override
		public String getName(Context ctx) {
			return ctx.getString(R.string.calendar_month_lbl_february);
		}

		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_month_short_lbl_february);
		}
	}, MARCH(2) {
		@Override
		public String getName(Context ctx) {
			return ctx.getString(R.string.calendar_month_lbl_march);
		}

		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_month_short_lbl_march);
		}
	}, APRIL(3) {
		@Override
		public String getName(Context ctx) {
			return ctx.getString(R.string.calendar_month_lbl_april);
		}

		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_month_short_lbl_april);
		}
	}, MAY(4) {
		@Override
		public String getName(Context ctx) {
			return ctx.getString(R.string.calendar_month_lbl_may);
		}

		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_month_short_lbl_may);
		}
	}, JUNE(5) {
		@Override
		public String getName(Context ctx) {
			return ctx.getString(R.string.calendar_month_lbl_june);
		}

		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_month_short_lbl_june);
		}
	}, JULY(6) {
		@Override
		public String getName(Context ctx) {
			return ctx.getString(R.string.calendar_month_lbl_july);
		}

		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_month_short_lbl_july);
		}
	}, AUGUST(7) {
		@Override
		public String getName(Context ctx) {
			return ctx.getString(R.string.calendar_month_lbl_august);
		}

		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_month_short_lbl_august);
		}
	}, SEPTEMBER(8) {
		@Override
		public String getName(Context ctx) {
			return ctx.getString(R.string.calendar_month_lbl_september);
		}

		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_month_short_lbl_september);
		}
	}, OCTOBER(9) {
		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_month_short_lbl_october);
		}

		@Override
		public String getName(Context ctx) {
			return ctx.getString(R.string.calendar_month_lbl_october);
		}
	}, NOVEMBER(10) {
		@Override
		public String getName(Context ctx) {
			return ctx.getString(R.string.calendar_month_lbl_november);
		}

		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_month_short_lbl_november);
		}
	}, DECEMBER(11) {
		@Override
		public String getName(Context ctx) {
			return ctx.getString(R.string.calendar_month_lbl_december);
		}

		@Override
		public String getShortName(Context ctx) {
			return ctx.getString(R.string.calendar_month_short_lbl_december);
		}
	};

	private final int id;

	private Month(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public abstract String getName(Context ctx);

	public abstract String getShortName(Context ctx);

	public static Month valueOf(int id) {
		for (Month month : values()) {
			if (month.id == id) {
				return month;
			}
		}
		throw new IllegalArgumentException("Couldn't find Month for " + id);
	}

}