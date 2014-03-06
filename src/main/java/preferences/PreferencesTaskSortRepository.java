package pl.sggw.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import pl.sggw.activities.home.logic.TaskSortType;

/**
 * @author Daniel
 * @date 03.11.12
 */

public class PreferencesTaskSortRepository {
	private static final String TAG = PreferencesTaskSortRepository.class.getName();

	private static final String KEY_TASK_SORT_TYPE = "restoreTaskSortType";

	private static final String DEFAULT_SORT_TYPE = TaskSortType.BY_DATE.toString();

	public PreferencesTaskSortRepository() {
		Log.d(TAG, "rejestracja");
	}

	public static void saveSortType(Context context, TaskSortType sortType) {
		SharedPreferences preferences = getPreferences(context);

		Log.d(TAG, "Zapis typu sortowania" + sortType);

		preferences.edit()
				.putString(KEY_TASK_SORT_TYPE, sortType.toString())
				.commit();
	}

	public static TaskSortType getSortType(Context ctx) {
		SharedPreferences preferences = getPreferences(ctx);
		String sortTypeString = preferences.getString(KEY_TASK_SORT_TYPE, DEFAULT_SORT_TYPE);

		TaskSortType savedSortType = TaskSortType.valueOf(sortTypeString);
		Log.d(TAG, "Pobieranie typu sortowania " + savedSortType);
		return savedSortType;
	}

	public static void flushState(Context ctx) {
		SharedPreferences preferences = getPreferences(ctx);
		preferences.edit()
				.remove(KEY_TASK_SORT_TYPE)
				.commit();
	}

	private static SharedPreferences getPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
}