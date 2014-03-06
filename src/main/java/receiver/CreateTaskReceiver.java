package pl.sggw.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import com.google.inject.Singleton;
import pl.sggw.activities.home.logic.TaskUpdater;
import pl.sggw.task.model.Task;

/**
 * @author Daniel
 * @date 03.11.12
 */

@Singleton
public class CreateTaskReceiver extends BroadcastReceiver {

	public static final String CREATE_TASK_BROADCAST = "pl.sggw.receiver.CreateTaskReceiver.CREATOR_TASK_BROADCAST";

	public static final String EXTRA_NULL_OBJECT_TASK = "newTaskForEdit";

	private TaskUpdater taskUpdater;

	public CreateTaskReceiver() {
	}

	public void initializeWith(TaskUpdater taskUpdater) {
		this.taskUpdater = taskUpdater;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Task nullObjTask = (Task) bundle.get(EXTRA_NULL_OBJECT_TASK);

		taskUpdater.editTask(nullObjTask);
	}

	public IntentFilter specifyIntentFilter() {
		return new IntentFilter(CREATE_TASK_BROADCAST);
	}
}
