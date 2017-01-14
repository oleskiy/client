package weartest.com.client.timer;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import weartest.com.client.R;


public class CustomProgressBar extends ImageView {

	Animation rotation;
	Context context;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public CustomProgressBar(Context context) {
		super(context);
		this.context = context;
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) { // for
																			// API
																			// >
																			// 10
																			// apply
																			// hardware
																			// acceleration
																			// for
																			// a
																			// window
			this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public CustomProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) { // for API > 10 apply hardware acceleration for a window
			this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public CustomProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) { // for API > 10
																			// apply hardware acceleration for a window
			this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		}
	}

	public void startLoadingAnimation() {
		if (rotation == null)
			rotation = AnimationUtils.loadAnimation(context,
					R.anim.loading_dialog_rotation_animation);
		startAnimation(rotation);
	}

	public void startLoadingAnimation(int pivotX, int pivotY) {

		if (rotation == null) {
			rotation = new RotateAnimation(360, 0, pivotX, pivotY);
			rotation.setDuration(1000);
			rotation.setRepeatCount(Animation.INFINITE);
			rotation.setInterpolator(new LinearInterpolator());
		}
		startAnimation(rotation);
	}

	public void stopLoadingAnimation() {
		clearAnimation();
	}

}
