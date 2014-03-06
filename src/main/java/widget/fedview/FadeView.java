package pl.sggw.widget.fedview;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import pl.sggw.R;


/**
 * @author Daniel
 * @date 09.11.12
 */

public class FadeView {
	private static String TAG = FadeView.class.getName();

	private AnimationFinishListener animationFinishListener;

	private final View view;

	private Animation fadeInAnimation;

	private Animation fadeOutAnimation;

	private boolean animationIsFinished;

	public FadeView(final View view) {
		this.view = view;
		animationIsFinished = true;

		Context context = view.getContext();
		fadeOutAnimation = AnimationUtils.loadAnimation(context, R.anim.translate_fade_out);
		fadeOutAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				animationIsFinished = true;
				if (animationFinishListener != null) {
					animationFinishListener.onAnimationFinished(R.anim.translate_fade_out);
				}
				Log.d(TAG, "koniec animacji " + R.anim.translate_fade_out);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}
		});

		fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.translate_fade_in);
		fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				animationIsFinished = true;
				if (animationFinishListener != null) {
					animationFinishListener.onAnimationFinished(R.anim.translate_fade_in);
				}
				Log.d(TAG, "koniec animacji " + R.anim.translate_fade_in);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}
		});
	}

	public void fadeOut() {
		Log.d(TAG, "uruchamianie animacji " + R.anim.translate_fade_out);
		animationIsFinished = false;
		view.setVisibility(View.VISIBLE);
		view.startAnimation(fadeOutAnimation);
	}

	public void fadeIn() {
		Log.d(TAG, "uruchamianie animacji " + R.anim.translate_fade_in);
		animationIsFinished = false;
		view.setVisibility(View.VISIBLE);
		view.startAnimation(fadeInAnimation);
	}

	public void setAnimationFinishListener(AnimationFinishListener animationFinishListener) {
		this.animationFinishListener = animationFinishListener;
	}

	public boolean animationIsFinished() {
		return animationIsFinished;
	}

	public View getView() {
		return view;
	}
}