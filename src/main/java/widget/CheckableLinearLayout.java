package pl.sggw.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

/**
 * @author Daniel
 * @date 30.10.12
 */
public class CheckableLinearLayout extends LinearLayout implements Checkable {

	private CheckedTextView checkbox;

	public CheckableLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// find checked text view
//		int childCount = getChildCount();
//		for (int i = 0; i < childCount; ++i) {
//			View v = getChildAt(i);
//			if (v instanceof CheckedTextView) {
//				checkbox = (CheckedTextView)v;
//			}
//		}

	}

	@Override
	public boolean isChecked() {
		System.out.println("Mattie is chacked");
		return false;
	}

	@Override
	public void setChecked(boolean checked) {
		System.out.println("Mattie set " +checked );
//		if(checked){
//			setBackgroundColor(Color.RED);
//		}                                else {
//			setBackgroundColor(Color.WHITE);
//
//		}
	}

	@Override
	public void toggle() {
		System.out.println("Mattie is toggle");
	}
}
