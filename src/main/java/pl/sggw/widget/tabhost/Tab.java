package pl.sggw.widget.tabhost;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import pl.sggw.R;

/**
 * @author Daniel
 * @since 30/07/2011
 */

public class Tab extends LinearLayout {

	private int height;

	private float textSize;

	public Tab(Context ctx) {
		super(ctx);
		height = ctx.getResources().getDimensionPixelSize(R.dimen.height_tab_widget);
		textSize = ctx.getResources().getDimension(R.dimen.text_size_tab_widget);
		setup();
	}

	private void setup() {
		setOrientation(LinearLayout.VERTICAL);

		LinearLayout.LayoutParams layoutParams =
				new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, height);
		layoutParams.weight = 1;
		setLayoutParams(layoutParams);

		setGravity(Gravity.CENTER);
		setBackgroundDrawable(this.getResources().getDrawable(R.drawable.select_tab_background));
	}

	public Tab withLabel(String label) {
		addView(createTabLabel(label));
		return this;
	}

	private TextView createTabLabel(String label) {
		TextView textView = new TextView(getContext());
		textView.setText(label);
		textView.setGravity(Gravity.CENTER);
		textView.setBackgroundColor(Color.TRANSPARENT);
		textView.setTextColor(Color.WHITE);
		textView.setTextSize(textSize);
		textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		return textView;
	}
}



