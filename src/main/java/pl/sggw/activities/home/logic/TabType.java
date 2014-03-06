package pl.sggw.activities.home.logic;

import pl.sggw.List2DoApp;
import pl.sggw.R;

public enum TabType {

	WITH_TODAY_TASKS {
		@Override
		public String getTabName() {
			return List2DoApp.getStringByResId(R.string.lbl_today);
		}
	},
	WITH_TOMORROW_TASKS {
		@Override
		public String getTabName() {
			return List2DoApp.getStringByResId(R.string.lbl_tomorrow);
		}
	},
	WITH_LATER_TASKS {
		@Override
		public String getTabName() {
			return List2DoApp.getStringByResId(R.string.lbl_later);
		}
	};

	public abstract String getTabName();

}