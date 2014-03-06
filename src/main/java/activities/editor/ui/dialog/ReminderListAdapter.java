package pl.sggw.activities.editor.ui.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import pl.sggw.R;
import pl.sggw.task.ReminderType;
import pl.sggw.widget.RefreshableAdapter;

/**
 * @author Daniel
 * @date 30.10.12
 */
public class ReminderListAdapter extends RefreshableAdapter<ReminderType> {

	private int rowLayoutResId = R.layout.dialog_list_row;

	private LayoutInflater inflater;

	private int rowHeightInPX;

	public ReminderListAdapter(Context ctx) {
		super(ctx, ReminderType.asList());
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		rowHeightInPX = ctx.getResources().getDimensionPixelSize(R.dimen.height_list_row);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(rowLayoutResId, null);
			holder.tvReminderName = (TextView) convertView.findViewById(R.id.line_text_view);
			holder.tvReminderName.setHeight(rowHeightInPX);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ReminderType reminderType = getItem(position);
		holder.tvReminderName.setText(reminderType.getLabel(context));

		return convertView;
	}

	public static class ViewHolder {
		public TextView tvReminderName;
	}

}
