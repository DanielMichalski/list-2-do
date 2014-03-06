package pl.sggw.activities.home;

import android.os.Bundle;
import android.view.View;
import pl.sggw.activities.home.logic.TabType;

/**
 * @author Daniel
 * @date 05.11.12
 */

public class LaterTasksActivity extends AbstractTaskActivity {
	private static String TAG = LaterTasksActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		adapter.setVisibilityCalendarPage(View.VISIBLE);
	}

	@Override
	protected TabType getTabType() {
		return TabType.WITH_LATER_TASKS;
	}

}