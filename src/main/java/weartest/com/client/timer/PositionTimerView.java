package weartest.com.client.timer;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import weartest.com.client.timer.CustomProgressBar;
import weartest.com.client.timer.SixtySecondSmallView.TimerFinishCallback;


public class PositionTimerView {

	protected Context context;

	protected LinearLayout mView;
	protected ImageView whiteArrowPointer;

	protected SixtySecondSmallView sixtySecondSmallImageView;
	protected CustomProgressBar progressBar;
	protected TimerClickListener mTimerClickListener;

	protected String mPositionId;

	public PositionTimerView(Context context, String positionId) {
		this.context = context;
		this.mPositionId = positionId;
	}

	protected int currentViewStatus;
	protected int currentViewColor;

	int containerHeight;
	int containerWidht;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public LinearLayout initTimerView(long startTime, long endTime,
			SixtySecondViewManager manager,TimerFinishCallback timerFinishCallback,TimerClickListener timerClickListener,RemoveTimerCallback removeTimerCallback) {

		sixtySecondSmallImageView = new SixtySecondSmallView(context, manager);
		// for API > 10 apply hardware acceleration for a window
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) { 
			sixtySecondSmallImageView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
		}
		sixtySecondSmallImageView.initiateView(startTime, endTime, mPositionId);
		sixtySecondSmallImageView.setOnTimerFinishCallback(timerFinishCallback);
		sixtySecondSmallImageView.setOnRemoveViewCallback(removeTimerCallback);
		if (timerClickListener != null) {
			mTimerClickListener = timerClickListener;
			sixtySecondSmallImageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if (sixtySecondSmallImageView != null)
						mTimerClickListener.onTimerClick(mPositionId);
				}
			});
		}

		currentViewStatus = sixtySecondSmallImageView.viewDrawState;
		currentViewColor = sixtySecondSmallImageView.colorType;

		mView = new LinearLayout(context);
		// for API > 10 apply hardware acceleration for a window
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) { 
			mView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		}
		
		LinearLayout.LayoutParams linParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		linParams.gravity = Gravity.CENTER_HORIZONTAL;
		mView.setOrientation(LinearLayout.VERTICAL);
		mView.setLayoutParams(linParams);
		whiteArrowPointer = new ImageView(context);
		whiteArrowPointer.setImageBitmap(manager.white_arrow);
		mView.addView(whiteArrowPointer, linParams);
		whiteArrowPointer.setVisibility(View.GONE);

		LinearLayout timerLay = new LinearLayout(context);
		// for API > 10 apply hardware acceleration for a window
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) { 
			timerLay.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		}
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				UtilityFunctions.pxFromDp(context, 35),
				UtilityFunctions.pxFromDp(context, 35));
		containerHeight = params.width;
		containerWidht = params.height;

		params.addRule(RelativeLayout.CENTER_IN_PARENT);
		timerLay.setLayoutParams(params);
		timerLay.addView(sixtySecondSmallImageView);

		progressBar = new CustomProgressBar(context);
		progressBar.setVisibility(View.GONE);
		progressBar.setImageBitmap(manager.getLoadingCircleChahedImage());

		timerLay.addView(progressBar, params);
		mView.addView(timerLay, linParams);
		return mView;
	}

	/**
	 * Set view color to green
	 * 
	 * */
	public void setViewColorToGreen() {
		currentViewColor = SixtySecondSmallView.GREEN_COLOR;
		sixtySecondSmallImageView
				.setProgressColorType(SixtySecondSmallView.GREEN_COLOR);
	}

	/**
	 * Set view color to grey
	 * 
	 * */
	public void setViewColorToGrey() {
		currentViewColor = SixtySecondSmallView.GREY_COLOR;
		sixtySecondSmallImageView
				.setProgressColorType(SixtySecondSmallView.GREY_COLOR);
	}

	/**
	 * Set view color to red
	 * 
	 * */
	public void setViewColorToRed() {
		currentViewColor = SixtySecondSmallView.RED_COLOR;
		sixtySecondSmallImageView
				.setProgressColorType(SixtySecondSmallView.RED_COLOR);
	}

	/**
	 * Show middle progress bar
	 * 
	 * */
	public void showProgressBar() {
		progressBar.setVisibility(View.VISIBLE);
		progressBar.startLoadingAnimation(containerWidht / 2,
				containerHeight / 2);
		sixtySecondSmallImageView.setLoadingProgressBarState();
	}

	/**
	 * Show middle progress bar
	 * 
	 * */
	public void hideProgressBar() {
		progressBar.setVisibility(View.GONE);
		progressBar.stopLoadingAnimation();
	}

	/**
	 * Set lost state
	 * 
	 * */
	public void setUserLost() {
		sixtySecondSmallImageView.setLost();
		currentViewStatus = sixtySecondSmallImageView.viewDrawState;
	}

	/**
	 * Set win state
	 * 
	 * */
	public void setUserWon() {
		sixtySecondSmallImageView.setWon();
		currentViewStatus = sixtySecondSmallImageView.viewDrawState;
	}

	/**
	 * Set tie state
	 * 
	 * */
	public void setUserTie() {
		sixtySecondSmallImageView.setTie();
		currentViewStatus = sixtySecondSmallImageView.viewDrawState;
	}

	public interface TimerClickListener {
		public void onTimerClick(String positionId);
	}

	/**
	 * Set visible or invisible white arrow above the timer when invisible still
	 * take space in the parent layout
	 * */
	public void showWhiteArrowVisible(boolean show) {
		if (show) {
			whiteArrowPointer.setVisibility(View.VISIBLE);
		} else
			whiteArrowPointer.setVisibility(View.INVISIBLE);
	}

	/**
	 * Remove white arrow pointer it becomes invisible and doesn't take any
	 * space in the layout
	 * */
	public void removeWhiteArrow() {
		whiteArrowPointer.setVisibility(View.GONE);
	}

	/**
	 * Get small timer current color view status
	 * */
	public int getSmallTimerColorStatus() {
		return sixtySecondSmallImageView.colorType;
	}

	/**
	 * Get small timer current draw state
	 * */
	public int getSmallTimerDrawState() {
		return sixtySecondSmallImageView.viewDrawState;
	}

	/**
	 * Remove view callback
	 * */
	public int getViewSize() {
		return sixtySecondSmallImageView.getWidth();
	}

	/**
	 * Remove view callback
	 * */
	public interface RemoveTimerCallback {
		public void onRemove(String positionId);
	}

	
	/**
	 * Get current time left until finish
	 * */
	public int getCurrentTimeLeft() {
		return sixtySecondSmallImageView.getCurrentTimeLeft();
	}
}
