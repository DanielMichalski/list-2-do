package pl.sggw.widget.submenu;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import pl.sggw.R;
import pl.sggw.widget.actionbar.ActionBar;

/**
 * @author Daniel
 */
public class Submenu extends SubmenuWindows implements OnClickListener {

	private static final int ACTIONBAR_MARGIN = 2;

	private LayoutInflater inflater;

	private View submenuView;

	private ViewGroup actionsView;


	private int rootWidth = 0;

	public Submenu(Context context) {
		super(context);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		submenuView = inflater.inflate(R.layout.submenu_layout, null);
		actionsView = (LinearLayout) submenuView.findViewById(R.id.submenu_actions);
		setContentView(submenuView);

	}

	public void addActionItem(ActionBar.Action action) {
		tryAddLineSeparator();
		final int index = actionsView.getChildCount();
		actionsView.addView(inflateAction(action), index);
	}

	private View inflateAction(ActionBar.Action action) {
		View view = inflater.inflate(R.layout.submenu_action_item, actionsView, false);
		view.setId(action.getId());
		view.setTag(action);
		view.setOnClickListener(this);

		ImageView ivIcon = (ImageView) view.findViewById(R.id.submenu_action_icon_image_view);
		ivIcon.setImageResource(action.getDrawable());

		TextView tvTitle = (TextView) view.findViewById(R.id.submenu_action_title_text_view);
		tvTitle.setText(action.getTitle());
		return view;
	}

	private void tryAddLineSeparator() {
		final int index = actionsView.getChildCount();
		System.out.println("");
		if (index % 2 != 0) {
			View lineSeparator = inflater.inflate(R.layout.submenu_separator, null);
			actionsView.addView(lineSeparator, index);
		}
	}

	@Override
	public void onClick(View view) {
		final Object tag = view.getTag();
		if (tag instanceof ActionBar.Action) {
			final ActionBar.Action action = (ActionBar.Action) tag;
			action.performAction(view);
			dismiss();
		}
	}

	/**
	 * Show quickaction popup. Popup is automatically positioned, on top or bottom of anchor view.
	 */
	//TODO mstrzelecki sprobowac skrocic kod
	public void show(View anchor) {
		preShow();

		int xPos, yPos;

		int[] location = new int[2];

		anchor.getLocationOnScreen(location);

		Rect anchorRect = new Rect(
				location[0], location[1],
				location[0] + anchor.getWidth(), location[1] + anchor.getHeight() - ACTIONBAR_MARGIN
		);

		submenuView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		int rootHeight = submenuView.getMeasuredHeight();

		if (rootWidth == 0) {
			rootWidth = submenuView.getMeasuredWidth();
		}

		int screenWidth = windowManager.getDefaultDisplay().getWidth();

		//automatically get X coord of popup (top left)
		if ((anchorRect.left + rootWidth) > screenWidth) {
			xPos = anchorRect.left - (rootWidth - anchor.getWidth());
			xPos = (xPos < 0) ? 0 : xPos;

		} else {
			if (anchor.getWidth() > rootWidth) {
				xPos = anchorRect.centerX() - (rootWidth / 2);
			} else {
				xPos = anchorRect.left;
			}
		}

		yPos = anchorRect.bottom;

		window.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);
	}

}