/*
 * Copyright (c) 2012 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package pl.sggw.google.sync.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import pl.sggw.R;
import pl.sggw.database.dao.TaskDao;
import pl.sggw.google.account.PrefGoogleAccountRepository;
import pl.sggw.google.task.GoogleTask;
import pl.sggw.google.task.GoogleTaskDao;
import pl.sggw.receiver.RefreshTasksReceiver;
import pl.sggw.task.model.Task;
import pl.sggw.util.ConverterTasks;

import java.io.IOException;
import java.util.*;

/**
 * @author Daniel
 * @date 06.12.12
 */

public class AsyncGoogleTasksLoader extends AsyncTask<Void, Void, List<GoogleTask>> {
	private String TAG = AsyncGoogleTasksLoader.class.getSimpleName();

	private final ProgressDialog dialog;

	private Context ctx;

	private GoogleTasksSynchronizer tasksSynchronizer;

	private TaskDao taskDao;

	private GoogleTaskDao googleTaskDao;

	public AsyncGoogleTasksLoader(Context ctx, GoogleTasksSynchronizer tasksSynchronizer) {
		this.ctx = ctx;
		this.tasksSynchronizer = tasksSynchronizer;
		this.taskDao = new TaskDao(ctx);
		this.googleTaskDao = new GoogleTaskDao(tasksSynchronizer.getService().tasks());

		String accountName = PrefGoogleAccountRepository.getAccountName(ctx);
		dialog = new ProgressDialog(ctx);
		dialog.setMessage(ctx.getString(R.string.dialog_msg_sync_google_tasks) + " \"" + accountName + "\"");
	}

	@Override
	protected void onPreExecute() {
		dialog.show();
	}

	@Override
	protected List<GoogleTask> doInBackground(Void... voids) {
		List<GoogleTask> result = new ArrayList<GoogleTask>();
		try {
			// usuwanie taskow z serwera ze statusem REMOVED
			List<Task> tasksForRemove = taskDao.getRemovedTasks();
			googleTaskDao.deleteGoogleTasks(ConverterTasks.toGoogleTasks(tasksForRemove));
			taskDao.deleteTasks(tasksForRemove);

			//usuwanie z bazy taksow ktore byly zsynchronizowane ale zostaly usuniete na serwerze
			List<Task> tasks = taskDao.getSynchronizedTasks();
			List<GoogleTask> googleTasks = googleTaskDao.getGoogleTasks();
			Map<String, GoogleTask> googleTasksMap = createGoogleTasksMap(googleTasks);
			for(Task task : tasks){
				GoogleTask compareTask = googleTasksMap.get(task.getGoogleId());
				if (compareTask == null) {
					taskDao.deleteTask(task);
				}
			}

			// insert do serwera niezsynchronizowanych taskow
			List<Task> notSynchronizedTasks = taskDao.getNotSynchronizedTasks();
			for (Task task : notSynchronizedTasks) {
				String googleId = googleTaskDao.saveGoogleTask(ConverterTasks.toGoogleTask(task));
				task.setGoogleId(googleId);
				taskDao.save(task);
			}

			// insert w lokalnej bazie oraz update baza/serwer
			tasks = taskDao.getSynchronizedTasks();
			googleTasks = googleTaskDao.getGoogleTasks();
			Map<String, Task> tasksMap = createTasksMap(tasks);
			for (GoogleTask googleTask : googleTasks) {
				Task compareTask = tasksMap.get(googleTask.getId());
				if (compareTask != null) {
					if (compareTask.getUpdatedDate().compareTo(googleTask.getUpdatedDate()) == 1) {
						googleTaskDao.saveGoogleTask(ConverterTasks.toGoogleTask(compareTask));
					} else if (compareTask.getUpdatedDate().compareTo(googleTask.getUpdatedDate()) == -1) {
						compareTask.setTitle(googleTask.getTitle());
						compareTask.setNotes(googleTask.getNotes());
						compareTask.setStatus(googleTask.getStatus());
						compareTask.setUpdatedDate(googleTask.getUpdatedDate());

						Date dueDate = googleTask.getDueDateWithoutTime();
						compareTask.getDueDate().setDate(dueDate.getDate());
						compareTask.getDueDate().setMonth(dueDate.getMonth());
						compareTask.getDueDate().setYear(dueDate.getYear());
						taskDao.save(compareTask);
					}
				} else {
					taskDao.save(ConverterTasks.fromGoogleTask(googleTask));
				}
			}

		} catch (IOException e) {
			tasksSynchronizer.handleGoogleException(e);
		} finally {
			taskDao.destroy();
			tasksSynchronizer.onRequestCompleted();
		}
		return result;
	}

	@Override
	protected void onPostExecute(List<GoogleTask> result) {
		ctx.sendBroadcast(new Intent(RefreshTasksReceiver.REFRESH_TASKS_BROADCAST));
		dialog.dismiss();
	}

	private Map<String, Task> createTasksMap(List<Task> tasks) {
		Map<String, Task> tasksMap = new HashMap<String, Task>();
		for (Task task : tasks) {
			tasksMap.put(task.getGoogleId(), task);
		}
		return tasksMap;
	}

	private Map<String, GoogleTask> createGoogleTasksMap(List<GoogleTask> googleTasks) {
		Map<String, GoogleTask> googleTasksMap = new HashMap<String, GoogleTask>();
		for (GoogleTask googleTask : googleTasks) {
			googleTasksMap.put(googleTask.getId(), googleTask);
		}
		return googleTasksMap;
	}
}
