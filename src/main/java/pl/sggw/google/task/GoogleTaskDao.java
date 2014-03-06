package pl.sggw.google.task;

import com.google.api.services.tasks.Tasks;
import pl.sggw.util.ConverterTasks;

import java.io.IOException;
import java.util.List;

/**
 * @author Daniel
 * @date 03.01.13
 */

public class GoogleTaskDao {

	private static final String TASK_LIST_ID = "@default";

	private Tasks.TasksOperations tasksOperations;

	public GoogleTaskDao(Tasks.TasksOperations tasksOperations) {
		this.tasksOperations = tasksOperations;
	}

	public String saveGoogleTask(GoogleTask googleTask) throws IOException {
		com.google.api.services.tasks.model.Task serverTask = ConverterTasks.toServerTask(googleTask);
		if (googleTask.isPersist()) {
			serverTask = tasksOperations.update(TASK_LIST_ID, serverTask.getId(), serverTask).execute();
		} else {
			serverTask = tasksOperations.insert(TASK_LIST_ID, serverTask).execute();
		}
		return serverTask.getId();
	}

	public void deleteGoogleTasks(List<GoogleTask> googleTasks) throws IOException {
		for (GoogleTask googleTask : googleTasks) {
			if (googleTask.getId() != null) {
				tasksOperations.delete(TASK_LIST_ID, googleTask.getId()).execute();
			}
		}
	}

	public List<GoogleTask> getGoogleTasks() throws IOException {
		Tasks.TasksOperations.List listRequest = tasksOperations.list(TASK_LIST_ID);
		return ConverterTasks.fromServerTasks(listRequest.execute().getItems());
	}

}