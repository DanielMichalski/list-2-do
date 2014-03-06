package pl.sggw.activities.home.logic;

import pl.sggw.task.model.Task;

/**
 * @author Daniel
 * @date 10.11.12
 */

public interface TaskUpdater {
	//TODO TaskUpdater z pl.sggw.activities.editor.logic; trzeba zmienić nazwę na TaskEditor

	void editTask(Task task);
	
	void saveTaskInDB(Task task);

	void removeTaskFromDB(Task task);

	void removeDoneTasksFromDB();

}
