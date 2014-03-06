package pl.sggw.activities.database_test;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import pl.sggw.R;
import pl.sggw.database.dao.TaskDao;
import pl.sggw.task.model.Task;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

import java.util.List;

/**
 * @author Daniel Michalski
 * @date 03.11.12
 */

public class TestDatabaseActivity extends RoboActivity{

    @InjectView(R.id.console_test_database)
    private TextView consoleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        StringBuilder stringBuilder = new StringBuilder();

        setContentView(R.layout.test_database_layout);


        TaskDao taskDao = new TaskDao(this);
        List<Task> todayTasks = taskDao.getTasksForToday();
        List<Task> tomorrowTasks = taskDao.getTasksForTomorrow();
        List<Task> laterTasks = taskDao.getTasksForLater();

        // Taski na dzisiaj
        stringBuilder.append("Taski na dzisiaj: \n");

        for (Task task : todayTasks) {
            Log.i("TaskDao", "Task na dzisiaj - " + task.getTitle());
            stringBuilder.append(task.getTitle() + "\n");
        }

        // Taski na jutro
       stringBuilder.append("\nTaski na jutro:\n");

        for (Task task : tomorrowTasks) {
            Log.i("TaskDao", "Task na jutro - " + task.getTitle());
            stringBuilder.append(task.getTitle() + "\n");
        }

        // Taski na później
        stringBuilder.append("\nTaski na później:\n");

        for (Task task : laterTasks) {
            Log.i("TaskDao", "Task na później - " + task.getTitle());
            stringBuilder.append(task.getTitle() + "\n");
        }


        consoleTextView.setText(stringBuilder.toString());
    }
}
