package pl.sggw.widget.swipe;

import android.view.View;

/**
 * User: Daniel
 * Date: 08.11.12
 */

public interface SwipeListItemListener<T> {

	void onSwipe(SwipeDirection direction, View view, T item);

}
