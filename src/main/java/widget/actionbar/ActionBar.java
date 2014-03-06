/*
 * Copyright (C) 2010 Johan Nilsson <http://markupartist.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.sggw.widget.actionbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import pl.sggw.R;

public class ActionBar extends RelativeLayout implements OnClickListener {

	private LayoutInflater inflater;

	private RelativeLayout mBarView;

	private LinearLayout mActionsView;

	private ProgressBar mProgress;

	private TextView tvTitleActivity;

	private OnClickListener tutorialClickListener;

	public ActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mBarView = (RelativeLayout) inflater.inflate(R.layout.actionbar_layout, null);
		addView(mBarView);

		mActionsView = (LinearLayout) mBarView.findViewById(R.id.actionbar_actions);

		mProgress = (ProgressBar) mBarView.findViewById(R.id.actionbar_progressbar);

		tvTitleActivity = (TextView) mBarView.findViewById(R.id.actionbar_title_activity);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ActionBar);

		a.recycle();
	}

	public void addTutorialClickListener(OnClickListener tutorialClickListener) {
		this.tutorialClickListener = tutorialClickListener;
	}

	/**
	 * Set the enabled state of the progress bar.
	 */
	public void setProgressBarVisibility(int visibility) {
		mProgress.setVisibility(visibility);
	}

	/**
	 * Returns the visibility status for the progress bar.
	 */
	public int getProgressBarVisibility() {
		return mProgress.getVisibility();
	}

	public void setTitleBase(CharSequence title) {
		tvTitleActivity.setText(title);
	}

	@Override
	public void onClick(View view) {
		final Object tag = view.getTag();
		if (tag instanceof Action) {
			final Action action = (Action) tag;
			action.performAction(view);
		}

		if (tutorialClickListener != null) {
			tutorialClickListener.onClick(view);
		}
	}

	public ActionBar addAction(Action action) {
		final int index = mActionsView.getChildCount();
		mActionsView.addView(inflateAction(action), index);
		return this;
	}

	public ActionBar addActionWithView(Action action, int layoutResId) {
		View view = inflater.inflate(layoutResId, mActionsView, false);
		view.setId(action.getId());
		view.setTag(action);
		view.setOnClickListener(this);

		final int index = mActionsView.getChildCount();
		mActionsView.addView(view, index);
		return this;
	}

	public ActionBar addActionWithSubmenu(Action action) {
		final int index = mActionsView.getChildCount();
		View item = inflateAction(action);
		View submenuIcon = item.findViewById(R.id.actionbar_submenu_icon);
		submenuIcon.setVisibility(VISIBLE);
		mActionsView.addView(item, index);
		return this;
	}

	/**
	 * Inflates a {@link android.view.View} with the given {@link pl.sggw.widget.actionbar.ActionBar.Action}.
	 *
	 * @param action the action to inflate
	 * @return a view
	 */
	private View inflateAction(Action action) {
		View view = inflater.inflate(R.layout.actionbar_item, mActionsView, false);

		ImageView itemIcon = (ImageView) view.findViewById(R.id.actionbar_item_icon);
		itemIcon.setImageResource(action.getDrawable());

		view.setId(action.getId());
		view.setTag(action);
		view.setOnClickListener(this);
		return view;
	}

	public void addBackAction(int icResId, final Activity activity) {
		ImageView itemIcon = (ImageView) mBarView.findViewById(R.id.actionbar_back_icon);
		itemIcon.setImageResource(icResId);

		View backItem = mBarView.findViewById(R.id.actionbar_back_item);
		backItem.setVisibility(View.VISIBLE);
		backItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				activity.finish();
				if (tutorialClickListener != null) {
					tutorialClickListener.onClick(view);
				}
			}
		});
	}

	/**
	 * Definition of an action that could be performed, along with a icon to
	 * show.
	 */
	public interface Action {

		public void performAction(View view);

		public String getTitle();

		public int getDrawable();

		public int getId();
	}
}
