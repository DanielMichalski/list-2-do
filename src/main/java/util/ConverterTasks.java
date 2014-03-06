package pl.sggw.util;

import pl.sggw.google.task.GoogleTask;
import pl.sggw.task.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 * @date 03.01.13
 */

public class ConverterTasks {

	//******** API_TASK <-> GOOGLE_TASK **********
	public static Task fromGoogleTask(GoogleTask googleTask) {
		Task apiTask = new Task(
				googleTask.getTitle(),
				googleTask.getDueDateWithoutTime()
		);
		apiTask.setGoogleId(googleTask.getId());
		apiTask.setNotes(googleTask.getNotes());
		apiTask.setStatus(googleTask.getStatus());
		apiTask.setUpdatedDate(googleTask.getUpdatedDate());

		return apiTask;
	}

	public static GoogleTask toGoogleTask(Task apiTask) {
		GoogleTask googleTask = new GoogleTask(
				apiTask.getGoogleId(),
				apiTask.getTitle()
		);
		googleTask.setDue(apiTask.getDueDate());
		googleTask.setNotes(apiTask.getNotes());
		googleTask.setStatus(apiTask.getStatus());
		googleTask.setUpdated(apiTask.getUpdatedDate());

		return googleTask;
	}

	public static List<GoogleTask> toGoogleTasks(List<Task> apiTasks) {
		List<GoogleTask> googleTasks = new ArrayList<GoogleTask>();
		if (apiTasks != null) {
			for (Task apiTask : apiTasks) {
				GoogleTask convertedTask = toGoogleTask(apiTask);
				googleTasks.add(convertedTask);
			}
		}
		return googleTasks;
	}


	//******** SERVER_GOOGLE_TASK <-> CUSTOM_GOOGLE_TASK **********
	public static GoogleTask fromServerTask(com.google.api.services.tasks.model.Task serverTask) {
		GoogleTask googleTask = new GoogleTask(
				serverTask.getId(),
				serverTask.getTitle()
		);
		googleTask.setDue(serverTask.getDue());
		googleTask.setNotes(serverTask.getNotes());
		googleTask.setUpdated(serverTask.getUpdated());
		googleTask.setStatus(serverTask.getStatus());

		return googleTask;
	}

	public static com.google.api.services.tasks.model.Task toServerTask(GoogleTask googleTask) {
		com.google.api.services.tasks.model.Task serverTask = new com.google.api.services.tasks.model.Task();
		serverTask.setId(googleTask.getId());
		serverTask.setTitle(googleTask.getTitle());
		serverTask.setNotes(googleTask.getNotes());
		serverTask.setDue(googleTask.getDueDateTime());
		serverTask.setUpdated(googleTask.getUpdatedDateTime());
		serverTask.setStatus(googleTask.getServerStatus());
		return serverTask;
	}

	public static List<GoogleTask> fromServerTasks(List<com.google.api.services.tasks.model.Task> serverTasks) {
		List<GoogleTask> googleTasks = new ArrayList<GoogleTask>();
		if (serverTasks != null) {
			for (com.google.api.services.tasks.model.Task serverTask : serverTasks) {
				GoogleTask convertedTask = fromServerTask(serverTask);
				googleTasks.add(convertedTask);
			}
		}
		return googleTasks;
	}

	public static List<com.google.api.services.tasks.model.Task> toServerTasks(List<GoogleTask> googleTasks) {
		List<com.google.api.services.tasks.model.Task> serverTasks = new ArrayList<com.google.api.services.tasks.model.Task>();
		if (googleTasks != null) {
			for (GoogleTask googleTask : googleTasks) {
				com.google.api.services.tasks.model.Task convertedTask = toServerTask(googleTask);
				serverTasks.add(convertedTask);
			}
		}
		return serverTasks;
	}

}