package weartest.com.client.timer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ImageView;


import java.util.Calendar;
import java.util.TimeZone;

import weartest.com.client.timer.PositionTimerView.RemoveTimerCallback;


public class SixtySecondSmallView extends ImageView {

	public static final int GREEN_COLOR = 1;
	public static final int RED_COLOR = 2;
	public static final int GREY_COLOR = 3;

	public static final int COUNT_DOWN_STATE = 1;
	public static final int STOP_STATE = 2;
	public static final int WIN_STATE = 3;
	public static final int LOSS_STATE = 4;
	public static final int TIE_STATE = 5;
	public static final int PROGRESS_BAR_STATE = 6;

	private static final int MAX_MASK_ANGLE = 360;

	private int COUNTER_FULL_START_VALUE;
	private int COUNTER_VALUE_MILLIS;
	private float ANGLE_MULTILPY_FACTOR;

	private static final float REMOVE_VIEW_DELAY = 2000;

	private Paint erasePaint;
	private Paint textPaint;
	private Paint textPaintsmaller;
	private float textOffsetNum;
	private float textOffset;

	private Bitmap eraseBitmap;
	private Canvas eraseCanvas;

	private RectF eraseOval;

	protected int colorType = GREEN_COLOR;

	protected float counterClockWiseAngle = MAX_MASK_ANGLE;
	protected int countDown = COUNTER_FULL_START_VALUE;

	 public static SixtySecondViewManager managerInstance;

	public long startTimeMillis;
	public long endTimeMillis;

	protected int viewDrawState = COUNT_DOWN_STATE;
	private TimerFinishCallback mTimerFinishCallback;
	private RemoveTimerCallback mRemoveTimerViewCallback;
	private long removeViewStartTime;

	public String positionID;

	public SixtySecondSmallView(Context context, SixtySecondViewManager manager) {
		super(context);
		//this.managerInstance = manager;
		initPaint();

		Rect bounds = new Rect();
		textPaint.getTextBounds(String.valueOf(COUNTER_FULL_START_VALUE), 0,
				(String.valueOf(COUNTER_FULL_START_VALUE)).length(), bounds);
		textOffsetNum = bounds.height() / 2;
		textPaintsmaller.getTextBounds(
				String.valueOf(COUNTER_FULL_START_VALUE), 0,
				(String.valueOf(COUNTER_FULL_START_VALUE)).length(), bounds);
		textOffset = bounds.height() / 2;

		eraseBitmap = Bitmap.createBitmap(manager.smallGreen_center.getWidth(),
				manager.smallGreen_full.getHeight(), Bitmap.Config.ARGB_4444);
		eraseCanvas = new Canvas(eraseBitmap);
		eraseCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

		eraseOval = new RectF(0, 0, managerInstance.smallGreen_full.getWidth(),
				manager.smallGreen_full.getHeight());

		Bitmap surface = Bitmap.createBitmap(managerInstance.smallGreen_full.getWidth(),
				manager.smallGreen_full.getHeight(), Bitmap.Config.ARGB_4444);
		setImageBitmap(surface);
	}

	public SixtySecondSmallView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SixtySecondSmallView(Context context, AttributeSet attrs,
								int defStyle) {
		super(context, attrs, defStyle);
	}
	public static long getServerRealGMTtime() {
		// mLogger.printInfo("@@@@@   CURRENT GAP  : " + serverTimeSyncGap);
		Calendar GMTtime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		return GMTtime.getTimeInMillis() + 0;
	}
	@Override
	protected void onDraw(Canvas canvas) {

		switch (viewDrawState) {
		case COUNT_DOWN_STATE:

			long diff = 60000 - 0;
			countDown = (int) (diff / 1000);
			counterClockWiseAngle = (diff * ANGLE_MULTILPY_FACTOR)
					- MAX_MASK_ANGLE;

			if (countDown > 0) {
				drawSmallTimerView(canvas, countDown, counterClockWiseAngle,
						null, colorType);
			} else {
				drawSmallTimerView(canvas, 0, MAX_MASK_ANGLE, null, colorType);

				viewDrawState = STOP_STATE;
				if (mTimerFinishCallback != null)
					mTimerFinishCallback.onTimerFinish(positionID);
			}

			break;
		case STOP_STATE:
			drawSmallTimerView(canvas, 0, MAX_MASK_ANGLE, null, colorType);
			break;
		case WIN_STATE:
			drawSmallTimerView(canvas, 0, MAX_MASK_ANGLE,
				/*	LanguageManager
							.getLanguageStringValue(LanguageManager.SHORT_WIN)*/"win",
					GREEN_COLOR);
			checkForDelayAndRemoveThisView();
			break;
		case LOSS_STATE:
			drawSmallTimerView(
					canvas,
					0,
					MAX_MASK_ANGLE,
					/*LanguageManager
							.getLanguageStringValue(LanguageManager.SHORT_LOSS)*/"los",
					RED_COLOR);
			checkForDelayAndRemoveThisView();
			break;
		case TIE_STATE:
			drawSmallTimerView(canvas, 0, MAX_MASK_ANGLE,
					/*LanguageManager
							.getLanguageStringValue(LanguageManager.SHORT_TIE)*/"tie",
					GREY_COLOR);
			checkForDelayAndRemoveThisView();
			break;
		case PROGRESS_BAR_STATE:
			drawSmallTimerView(canvas, 0, MAX_MASK_ANGLE, "", colorType);
			break;
		}
		super.onDraw(canvas);
	}

	/**
	 * Initiate sixty view
	 */
	public void initiateView(long startTime, long endTime, String positionId) {
		this.positionID = positionId;
		this.startTimeMillis = startTime;
		this.endTimeMillis = endTime;

		COUNTER_FULL_START_VALUE = (int) (endTime - startTime) / 1000;
		COUNTER_VALUE_MILLIS = (int) (endTime - startTime);
		ANGLE_MULTILPY_FACTOR = MAX_MASK_ANGLE / (float) COUNTER_VALUE_MILLIS;
		
		long diff = endTimeMillis;// - AppConfigAndVars.getServerRealGMTtime();
		countDown = (int) (diff / 1000);
	}

	/**
	 * Remove this view
	 */
	private void checkForDelayAndRemoveThisView() {
		if ((/*AppConfigAndVars.getServerRealGMTtime() -*/ removeViewStartTime) > REMOVE_VIEW_DELAY) {
			if (mRemoveTimerViewCallback != null)
				mRemoveTimerViewCallback.onRemove(positionID);
		}
	}

	/**
	 * Draw current timer state view
	 * 
	 * @param canvas
	 *            canvas to draw on
	 * @param countdown
	 *            the count down value
	 * @param angle
	 *            the angle value
	 * 
	 * */
	private void drawSmallTimerView(Canvas canvas, int countdown, float angle,
			String text, int colorType) {
		canvas.drawColor(Color.TRANSPARENT);
		eraseCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

		if (colorType == GREEN_COLOR)
			eraseCanvas.drawBitmap(managerInstance.smallGreen_full, 0, 0, null);
		else if (colorType == RED_COLOR)
			eraseCanvas.drawBitmap(managerInstance.smallRed_full, 0, 0, null);
		else if (colorType == GREY_COLOR)
			eraseCanvas.drawBitmap(managerInstance.smallGrey_full, 0, 0, null);

		eraseCanvas.drawArc(eraseOval, -90, angle, true, erasePaint);
		canvas.drawBitmap(managerInstance.smallGreen_full, 0, 0, null);

		if (colorType == GREEN_COLOR) {
			canvas.drawBitmap(eraseBitmap, managerInstance.surfaceSmallWidth
					/ 2 - managerInstance.smallGreen_full.getWidth() / 2,
					managerInstance.surfaceSmallHeight / 2
							- managerInstance.smallGreen_full.getHeight() / 2,
					null);
			canvas.drawBitmap(
					managerInstance.smallGreen_full,
					managerInstance.surfaceSmallWidth / 2
							- managerInstance.smallGreen_full.getWidth() / 2,
					managerInstance.surfaceSmallHeight / 2
							- managerInstance.smallGreen_full.getHeight() / 2,
					null);
		} else if (colorType == RED_COLOR) {
			canvas.drawBitmap(eraseBitmap, managerInstance.surfaceSmallWidth
					/ 2 - managerInstance.smallGreen_full.getWidth() / 2,
					managerInstance.surfaceSmallHeight / 2
							- managerInstance.smallGreen_full.getHeight() / 2,
					null);
			canvas.drawBitmap(managerInstance.smallGreen_full,
					managerInstance.surfaceSmallWidth / 2
							- managerInstance.smallGreen_full.getWidth() / 2,
					managerInstance.surfaceSmallHeight / 2
							- managerInstance.smallGreen_full.getHeight() / 2,
					null);
		} else if (colorType == GREY_COLOR) {
			canvas.drawBitmap(eraseBitmap, managerInstance.surfaceSmallWidth
					/ 2 - managerInstance.smallGreen_full.getWidth() / 2,
					managerInstance.surfaceSmallHeight / 2
							- managerInstance.smallGreen_full.getHeight() / 2,
					null);
			canvas.drawBitmap(managerInstance.smallGreen_full,
					managerInstance.surfaceSmallWidth / 2
							- managerInstance.smallGreen_full.getWidth() / 2,
					managerInstance.surfaceSmallHeight / 2
							- managerInstance.smallGreen_full.getHeight() / 2,
					null);
		}

		if (text != null) {
			canvas.drawText(text, managerInstance.surfaceSmallWidth / 2,
					managerInstance.surfaceSmallHeight / 2 + textOffset,
					textPaintsmaller);
		} else
			canvas.drawText(String.valueOf(countdown),
					managerInstance.surfaceSmallWidth / 2,
					managerInstance.surfaceSmallHeight / 2 + textOffsetNum,
					textPaint);
	}

	/**
	 * Initialize paints
	 * 
	 * */
	private void initPaint() {
		erasePaint = new Paint();
		erasePaint.setAntiAlias(true);
		erasePaint.setDither(true);
		erasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		erasePaint.setFilterBitmap(true);

		textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
		textPaint.setAntiAlias(true);
		textPaint.setDither(true);
		textPaint.setSubpixelText(true);
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		textPaint.setTextAlign(Paint.Align.CENTER);
		int textSize = managerInstance.surfaceSmallWidth * 23 / 100;
		textPaint.setTextSize(textSize);

		textPaintsmaller = new Paint();
		textPaintsmaller.setColor(Color.BLACK);
		textPaintsmaller.setAntiAlias(true);
		textPaintsmaller.setDither(true);
		textPaintsmaller.setSubpixelText(true);
		textPaintsmaller.setStyle(Paint.Style.FILL);
		textPaintsmaller.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		textPaintsmaller.setTextAlign(Paint.Align.CENTER);
		textSize = managerInstance.surfaceSmallWidth * 24 / 100;
		textPaintsmaller.setTextSize(textSize);
	}

	/**
	 * Post Update current timer view (invalidate)
	 * */
	public void postUpdateView() {
		postInvalidate();
	}

	/**
	 * Update current timer view (invalidate)
	 * */
	public void updateView() {
		invalidate();
	}

	/**
	 * Set progress bar color type (Default is green)
	 * */
	public synchronized void setProgressColorType(int type) {
		if (viewDrawState == COUNT_DOWN_STATE)
			colorType = type;
	}

	/**
	 * Set on timer finish count down callback
	 * */
	public void setOnTimerFinishCallback(TimerFinishCallback timerFinishCallback) {
		this.mTimerFinishCallback = timerFinishCallback;
	}

	/**
	 * Set on timer finish count down callback
	 * */
	public void setOnRemoveViewCallback(RemoveTimerCallback removeTimerCallback) {
		this.mRemoveTimerViewCallback = removeTimerCallback;
	}

	/**
	 * Set user win view
	 * */
	public void setWon() {
		removeViewStartTime = getServerRealGMTtime();
		viewDrawState = WIN_STATE;
	}

	/**
	 * Set user loss view
	 * */
	public void setLost() {
		removeViewStartTime = getServerRealGMTtime();
		viewDrawState = LOSS_STATE;
	}

	/**
	 * Set user tie view
	 * */
	public void setTie() {
		removeViewStartTime = getServerRealGMTtime();
		viewDrawState = TIE_STATE;
	}

	/**
	 * Set loading progress bar state
	 * */
	public void setLoadingProgressBarState() {
		viewDrawState = PROGRESS_BAR_STATE;
	}

	/**
	 * Timer view click callback interface
	 * */
	public interface TimerFinishCallback {
		public void onTimerFinish(String optionId);
	}

	/**
	 * Set loading progress bar state
	 * */
	public void resetTimer() {
		viewDrawState = COUNT_DOWN_STATE;
	}
	
	/**
	 * Get current time left until finish
	 * */
	public int getCurrentTimeLeft() {
		return countDown;
	}
}
