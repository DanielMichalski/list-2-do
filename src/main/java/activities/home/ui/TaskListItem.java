package pl.sggw.activities.home.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.TextView;
import pl.sggw.R;
import pl.sggw.task.PriorityType;
import pl.sggw.task.StateType;
import pl.sggw.task.model.Task;
import pl.sggw.util.time.MonthUtil;
import pl.sggw.widget.fedview.AnimationFinishListener;
import pl.sggw.widget.fedview.FadeView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Daniel
 * Date: 06.11.12
 */

public class TaskListItem extends FrameLayout implements AnimationFinishListener {

	private View vTag;

	private TextView tvName;

    private TextView tvHour;


	private View linearCalendarPage;

	private TextView tvDayNo;

	private TextView tvShortMonth;


	private FadeView fadeStrikeLine;

	public TaskListItem(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemLayout = inflater.inflate(R.layout.task_list_item_layout, null);
		registerViews(itemLayout);

		addView(itemLayout);
	}

	private void registerViews(View itemLayout) {
		vTag = itemLayout.findViewById(R.id.task_tag_view);
		tvName = (TextView) itemLayout.findViewById(R.id.first_line_text_view);
        tvHour = (TextView) itemLayout.findViewById(R.id.second_line_text_view);

		linearCalendarPage = itemLayout.findViewById(R.id.calendar_page_linear_layout);
		tvDayNo = (TextView) itemLayout.findViewById(R.id.day_no_text_view);
		tvShortMonth = (TextView) itemLayout.findViewById(R.id.short_month_text_view);

		fadeStrikeLine = new FadeView(itemLayout.findViewById(R.id.strike_line_view));
		fadeStrikeLine.setAnimationFinishListener(this);
	}

	public void setupView(Task task, int visibilityCalendarPage) {
		linearCalendarPage.setVisibility(visibilityCalendarPage);
		PriorityType taskPriority = task.getPriority();
		vTag.setBackgroundColor(taskPriority.getBackgroundColor(getContext()));
		StateType taskState = task.getStatus();
		settleStyleByState(taskState);
	}

	public void fill(Task task) {
		tvName.setText(task.getTitle());
        tvHour.setText(new SimpleDateFormat("HH:mm")
                .format(task.getDueDate())
                .toString());
		Date date = task.getDueDate();
		tvDayNo.setText("" + date.getDate());
		tvShortMonth.setText(MonthUtil.getMonthShortNameBy(getContext(), date.getMonth()));
	}

	private void settleStyleByState(StateType stateType) {
		switch (stateType) {
			case IN_QUEUE:
				fadeStrikeLine.getView().setVisibility(View.GONE);
				tvName.setTextColor(Color.WHITE);
                tvHour.setTextColor(Color.WHITE);
				tvShortMonth.setBackgroundColor(getContext().getResources().getColor(R.color.calendar_page_month_background));
				tvShortMonth.setTextColor(Color.WHITE);
				tvDayNo.setBackgroundColor(Color.WHITE);
				tvDayNo.setTextColor(Color.BLACK);
				break;
			case DONE:
				fadeStrikeLine.getView().setVisibility(View.VISIBLE);
				tvName.setTextColor(Color.DKGRAY);
                tvHour.setTextColor(Color.DKGRAY);
				tvShortMonth.setBackgroundColor(Color.DKGRAY);
				tvShortMonth.setTextColor(Color.GRAY);
				tvDayNo.setBackgroundColor(Color.GRAY);
				tvDayNo.setTextColor(Color.DKGRAY);
				break;
		}
	}

	public void startAnimationByState(StateType stateType) {
		switch (stateType) {
			case IN_QUEUE:
				fadeStrikeLine.fadeOut();
				break;
			case DONE:
				fadeStrikeLine.fadeIn();
				break;
		}
	}

	@Override
	public void onAnimationFinished(int id) {
		switch (id) {
			case R.anim.translate_fade_out:
				settleStyleByState(StateType.IN_QUEUE);
				break;
			case R.anim.translate_fade_in:
				settleStyleByState(StateType.DONE);
				break;
		}
	}

	public boolean canRunAnimation() {
		return fadeStrikeLine.animationIsFinished();
	}
}