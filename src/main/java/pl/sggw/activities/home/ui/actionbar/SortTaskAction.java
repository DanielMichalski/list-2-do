package pl.sggw.activities.home.ui.actionbar;

import android.content.Context;
import android.view.View;
import pl.sggw.R;
import pl.sggw.activities.home.ui.submenu.SortByDateAction;
import pl.sggw.activities.home.ui.submenu.SortByNameAction;
import pl.sggw.activities.home.ui.submenu.SortByPriorityAction;
import pl.sggw.widget.actionbar.ActionBar;
import pl.sggw.widget.submenu.Submenu;

/**
 * @author Daniel
 * @since 0.0.1
 */
public class SortTaskAction implements ActionBar.Action {
	private static String TAG = SortTaskAction.class.getName();

	private Submenu submenuExpanded;

	public SortTaskAction(Context ctx) {
		submenuExpanded = new Submenu(ctx);
		submenuExpanded.addActionItem(new SortByDateAction(ctx));
		submenuExpanded.addActionItem(new SortByPriorityAction(ctx));
		submenuExpanded.addActionItem(new SortByNameAction(ctx));
	}

	@Override
	public void performAction(View view) {
		submenuExpanded.show(view);
	}

	@Override
	public int getDrawable() {
		return R.drawable.ic_tab_sort_selected;
	}

	@Override
	public int getId() {
		return R.id.task_sort_action_item;
	}

	@Override
	public String getTitle() {
		return null;
	}

}
