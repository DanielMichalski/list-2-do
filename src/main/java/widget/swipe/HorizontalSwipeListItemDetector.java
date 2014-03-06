package pl.sggw.widget.swipe;

/*
 * Copyright 2012 Roman Nurik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;


public class HorizontalSwipeListItemDetector implements View.OnTouchListener {
	// Cached ViewConfiguration and system-wide constant values
	private int scaledTouchSlop;
	private int minFlingVelocity;
	private int maxFlingVelocity;

	// Fixed properties
	private ListView listView;
	private ListAdapter adapter;
	private SwipeListItemListener swipeListItemListener;
	private int viewWidth = 1; // 1 and not 0 to prevent dividing by zero

	// Transient properties
	private float downX;
	private VelocityTracker velocityTracker;
	private int downPosition;
	private View downView;
	private boolean paused;


	public HorizontalSwipeListItemDetector(ListView listView, SwipeListItemListener swipeListItemListener) {
		ViewConfiguration vc = ViewConfiguration.get(listView.getContext());
		scaledTouchSlop = vc.getScaledTouchSlop();
		minFlingVelocity = 150;
		maxFlingVelocity = vc.getScaledMaximumFlingVelocity();

		this.listView = listView;
		this.adapter = listView.getAdapter();
		this.swipeListItemListener = swipeListItemListener;
	}

	/**
	 * Enables or disables (pauses or resumes) watching for swipe-to-dismiss gestures.
	 *
	 * @param enabled Whether or not to watch for gestures.
	 */
	public void setEnabled(boolean enabled) {
		paused = !enabled;
	}

	public AbsListView.OnScrollListener makeScrollListener() {
		return new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView absListView, int scrollState) {
				setEnabled(scrollState != AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
			}

			@Override
			public void onScroll(AbsListView absListView, int i, int i1, int i2) {
			}
		};
	}

	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		if (viewWidth < 2) {
			viewWidth = listView.getWidth();
		}

		switch (motionEvent.getActionMasked()) {
			case MotionEvent.ACTION_DOWN: {
				if (paused) {
					return false;
				}

				downView = findChildListBy(motionEvent);
				if (downView != null) {
					downX = motionEvent.getRawX();
					downPosition = listView.getPositionForView(downView);

					velocityTracker = VelocityTracker.obtain();
					velocityTracker.addMovement(motionEvent);
				}
				view.onTouchEvent(motionEvent);
				return true;
			}

			case MotionEvent.ACTION_UP: {
				if (velocityTracker == null) {
					break;
				}

				float deltaX = motionEvent.getRawX() - downX;
				velocityTracker.addMovement(motionEvent);
				velocityTracker.computeCurrentVelocity(1000);
				float velocityX = Math.abs(velocityTracker.getXVelocity());
				float velocityY = Math.abs(velocityTracker.getYVelocity());
				boolean detectedSwipe = false;
				boolean isRightDirectionSwipe = false;
				if (Math.abs(deltaX) > viewWidth / 2) {
					detectedSwipe = true;
					isRightDirectionSwipe = deltaX > 0;
				} else if (minFlingVelocity <= velocityX && velocityX <= maxFlingVelocity
						&& velocityY < velocityX) {
					detectedSwipe = true;
					isRightDirectionSwipe = velocityTracker.getXVelocity() > 0;
				}

				if (detectedSwipe && isRightDirectionSwipe) {
					swipeListItemListener.onSwipe(SwipeDirection.RIGHT, downView, adapter.getItem(downPosition));
				} else if (detectedSwipe && !isRightDirectionSwipe) {
					swipeListItemListener.onSwipe(SwipeDirection.LEFT, downView, adapter.getItem(downPosition));
				}

				velocityTracker = null;
				downX = 0;
				downView = null;
				downPosition = ListView.INVALID_POSITION;
				break;
			}

			case MotionEvent.ACTION_MOVE: {
				if (velocityTracker == null || paused) {
					break;
				}

				velocityTracker.addMovement(motionEvent);
				float deltaX = motionEvent.getRawX() - downX;
				if (Math.abs(deltaX) > scaledTouchSlop) {
					listView.requestDisallowInterceptTouchEvent(true);

					// Cancel ListView's touch (un-highlighting the item)
					MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
					cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
							(motionEvent.getActionIndex()
									<< MotionEvent.ACTION_POINTER_INDEX_SHIFT));
					listView.onTouchEvent(cancelEvent);
					return true;
				}

				break;
			}
		}
		return false;
	}

	private View findChildListBy(MotionEvent motionEvent) {
		View child = null;
		Rect rect = new Rect();
		int childCount = listView.getChildCount();
		int[] listViewCoords = new int[2];
		listView.getLocationOnScreen(listViewCoords);

		int x = (int) motionEvent.getRawX() - listViewCoords[0];
		int y = (int) motionEvent.getRawY() - listViewCoords[1];
		for (int i = 0; i < childCount; i++) {
			child = listView.getChildAt(i);
			child.getHitRect(rect);
			if (rect.contains(x, y)) {
				return child;
			}
		}
		return child;
	}
}
