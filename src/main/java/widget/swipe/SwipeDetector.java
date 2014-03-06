package pl.sggw.widget.swipe;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * User: Daniel
 * Date: 08.11.12
 */

public class SwipeDetector implements View.OnTouchListener {
	private static final String TAG = SwipeDetector.class.getName();

	private static final int MIN_DISTANCE = 100;

	private float downX;

	private float downY;

	private float upX;

	private float upY;

	private SwipeDirection direction;


	public SwipeDetector() {
		direction = SwipeDirection.NONE;
	}

	public boolean detected() {
		return direction != SwipeDirection.NONE;
	}

	public SwipeDirection getDirection() {
		return direction;
	}

	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				downX = event.getX();
				downY = event.getY();
				direction = SwipeDirection.NONE;
				return false;
			}
			case MotionEvent.ACTION_MOVE: {
				upX = event.getX();
				upY = event.getY();

				float deltaX = downX - upX;
				float deltaY = downY - upY;

				if (Math.abs(deltaX) > MIN_DISTANCE) {
					if (deltaX < 0) {
						Log.i(TAG, "Swipe Left to Right");
						direction = SwipeDirection.RIGHT;
						return true;
					}
					if (deltaX > 0) {
						Log.i(TAG, "Swipe Right to Left");
						direction = SwipeDirection.LEFT;
						return true;
					}
				} else if (Math.abs(deltaY) > MIN_DISTANCE) {
					if (deltaY < 0) {
						Log.i(TAG, "Swipe Top to Bottom");
						direction = SwipeDirection.BOTTOM;
						return false;
					}
					if (deltaY > 0) {
						Log.i(TAG, "Swipe Bottom to Top");
						direction = SwipeDirection.TOP;
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
}