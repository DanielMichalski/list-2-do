package pl.sggw.widget.submenu;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import pl.sggw.R;

/**
 * @author Daniel
 */
public class SubmenuWindows implements Animation.AnimationListener {

	protected Context ctx;

	protected PopupWindow window;

	protected WindowManager windowManager;

	protected View mRootView;

	private Animation animation;

	/**
	 * Constructor.
	 *
	 * @param context Context
	 */
	public SubmenuWindows(Context context) {
		ctx = context;
		windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		window = new PopupWindow(context);

		animation = AnimationUtils.loadAnimation(ctx, R.anim.disappear);
		animation.setAnimationListener(this);
	}

	/**
	 * On pre show
	 */
	protected void preShow() {
		if (mRootView == null) {
			throw new IllegalStateException("setContentView was not called with a view to display.");
		}
		mRootView.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.grow_from_top));

		window.setBackgroundDrawable(new BitmapDrawable());
		window.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
		window.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		window.setTouchable(true);
		window.setFocusable(true);
		window.setOutsideTouchable(true);

		window.setContentView(mRootView);
	}

	/**
	 * Set content view.
	 *
	 * @param root Root view
	 */
	public void setContentView(View root) {
		mRootView = root;

		window.setContentView(root);
	}

	@Override
	public void onAnimationStart(Animation animation) {
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		dismiss();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
	}

	/**
	 * Dismiss the popup window.
	 */
	public void dismiss() {
		window.dismiss();
	}
}

