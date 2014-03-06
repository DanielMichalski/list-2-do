package pl.sggw.activities.home.ui;

import android.content.Intent;
import android.os.Bundle;
import pl.sggw.task.model.Task;

/**
 * @author Daniel
 * @date 10.11.12
 */
public interface TasksView {

	void removeTaskFromList(Task task);

	void showDialog(int id);

	boolean showDialog(int id, Bundle bundle);

	void startActivityForResult(Intent intent, int reqCode);

	void setListeners();

	void vibrate(long durationInMs);

	void registerShakeListener();

	void unregisterShakeListener();
}
