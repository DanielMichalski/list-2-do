package pl.sggw.activities.home;

import android.os.Bundle;
import android.view.View;
import pl.sggw.activities.home.logic.TabType;

/**
 * @author Daniel
 * @date 05.11.12
 */

public class TomorrowTasksActivity extends AbstractTaskActivity {
	private static String TAG = TomorrowTasksActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter.setVisibilityCalendarPage(View.GONE);
	}

	@Override
	protected TabType getTabType() {
		return TabType.WITH_TOMORROW_TASKS;
	}

}