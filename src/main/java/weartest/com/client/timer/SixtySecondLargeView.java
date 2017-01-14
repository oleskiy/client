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
import android.util.Log;
import android.util.TimeFormatException;
import android.widget.ImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import weartest.com.client.Timer;


public class SixtySecondLargeView extends ImageView {

	public static final int GREEN_COLOR = 1;
	public static final int RED_COLOR = 2;
	public static final int GREY_COLOR = 3;

	public static final int COUNT_DOWN_STATE = 1;
	public static final int STOP_STATE = 2;
	public static final int WIN_STATE = 3;
	public static final int LOSS_STATE = 4;
	public static final int TIE_STATE = 5;
	public static final int PROGRESS_BAR_STATE = 6;
	public static final int NO_DRAW_STATE = 7;

	private final int MAX_MASK_ANGLE = 360;
	private int COUNTER_START_VALUE;
	private int COUNTER_VALUE_MILLIS;
	private float ANGLE_MULTILPY_FACTOR;

	private Paint erasePaint;
	private Paint textPaint;
	private Paint textPaintsmaller;
	private float textOffsetNum;
	private float textOffset;

	private Bitmap eraseBitmap;
	private Canvas eraseCanvas;

	private RectF eraseOval;

	private int colorType = GREY_COLOR;

	private float counterClockWiseAngle = MAX_MASK_ANGLE;
	private int countDown = COUNTER_START_VALUE;

	private SixtySecondViewManager managerInstance;
	protected long startTimeMillis;
	public long endTimeMillis;

	private int timerViewState = NO_DRAW_STATE;

	public SixtySecondLargeView(Context context, SixtySecondViewManager manager) {
		super(context);
		this.managerInstance = manager;

		initPaint();

		Rect bounds = new Rect();
		textPaint.getTextBounds(String.valueOf(COUNTER_START_VALUE), 0,
				(String.valueOf(COUNTER_START_VALUE)).length(), bounds);
		textOffsetNum = bounds.height() / 2;
		textPaintsmaller.getTextBounds(String.valueOf(COUNTER_START_VALUE), 0,
				(String.valueOf(COUNTER_START_VALUE)).length(), bounds);
		textOffset = bounds.height() / 2;

		eraseBitmap = Bitmap.createBitmap(managerInstance.surfaceBigWidth,
				managerInstance.surfaceBigHeight, Bitmap.Config.ARGB_8888);
		eraseCanvas = new Canvas(eraseBitmap);
		eraseCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		eraseOval = new RectF(0, 0, managerInstance.surfaceBigWidth,
				managerInstance.surfaceBigHeight);

		Bitmap surface = Bitmap.createBitmap(managerInstance.surfaceBigWidth,
				managerInstance.surfaceBigHeight, Bitmap.Config.ARGB_8888);
		setImageBitmap(surface);

		setWillNotDraw(false);
	}

	public SixtySecondLargeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SixtySecondLargeView(Context context, AttributeSet attrs,
								int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		DateFormat formatter = new SimpleDateFormat("00:ss");

		switch (timerViewState) {
		case COUNT_DOWN_STATE:

			long diff = endTimeMillis - SixtySecondSmallView.getServerRealGMTtime();//AppConfigAndVars.getServerRealGMTtime();
			countDown = (int) (diff / 1000);
			counterClockWiseAngle = MAX_MASK_ANGLE
					- (MAX_MASK_ANGLE - (diff * ANGLE_MULTILPY_FACTOR));
			String dateFormatted = formatter.format(diff);

			if (countDown > 0) {
				if(countDown<10)colorType = RED_COLOR;
				drawTimerView(canvas, dateFormatted, counterClockWiseAngle, null,
						colorType);
			} else {
				drawTimerView(canvas, "0", 0, null, colorType);
				timerViewState = STOP_STATE;
			}

			break;
		case STOP_STATE:
			drawTimerView(canvas, "0", 0, null, colorType);
			managerInstance.stopUpdatingViews();

			Log.d("Timer","STOP_STATE");
			Timer.showEndTimeDialog.flush();
			break;
		case WIN_STATE:
			drawTimerView(
					canvas,
					"0",
					0,
					"Win",
					GREEN_COLOR);
			break;
		case LOSS_STATE:
			drawTimerView(canvas, "0", 0,
					"Los",
					RED_COLOR);
			break;
		case TIE_STATE:
			drawTimerView(
					canvas,
					"0",
					0,
					"TIE",
					GREY_COLOR);
		case PROGRESS_BAR_STATE:
			drawTimerView(canvas, "0", 0, "PROGRESS_BAR_STATE", colorType); // drawTimerView(canvas,
														// 0, 360,"",colorType);
														// for empty
			break;
		case NO_DRAW_STATE:
			break;
		}
		super.onDraw(canvas);
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
	private void drawTimerView(Canvas canvas, String countdown, float angle,
			String text, int colorType) {
		canvas.drawColor(Color.TRANSPARENT);
		eraseCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

		switch (colorType) {
		case GREEN_COLOR:
			eraseCanvas.drawBitmap(managerInstance.bigGreen_full, 0, 0, null);
			break;
		case RED_COLOR:
			eraseCanvas.drawBitmap(managerInstance.bigRed_full, 0, 0, null);
			break;
		case GREY_COLOR:
			eraseCanvas.drawBitmap(managerInstance.bigGrey_full, 0,0, null);
			break;
		}

		eraseCanvas.drawArc(eraseOval, -90, angle, true, erasePaint);

		switch (colorType) {
		case GREEN_COLOR:
			canvas.drawBitmap(managerInstance.bigGreen_empty, 0, 0, null);
			canvas.drawBitmap(eraseBitmap, 0, 0, null);
			break;
		case RED_COLOR:
			canvas.drawBitmap(managerInstance.bigRed_empty, 0, 0, null);
			canvas.drawBitmap(eraseBitmap, 0, 0, null);
			break;
		case GREY_COLOR:
			canvas.drawBitmap(managerInstance.bigGrey_empty, 0, 0, null);
			canvas.drawBitmap(eraseBitmap, 0, 0, null);
			break;
		}

		if (text != null) {
			textPaintsmaller.setTextSize(42 - (int) ((text.length() - 3) * 3f));
			canvas.drawText(text, getWidth() / 2, getHeight() / 2 + textOffset,
					textPaintsmaller);
		} else {
			canvas.drawText(String.valueOf(countdown), getWidth() / 2,
					getHeight() / 2 + textOffsetNum, textPaint);
		}

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
		textPaint.setColor(Color.WHITE);
		textPaint.setAntiAlias(true);
		textPaint.setDither(true);
		textPaint.setSubpixelText(true);
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		textPaint.setTextAlign(Paint.Align.CENTER);
		int textSize = managerInstance.surfaceBigWidth * 17 / 100;
		textPaint.setTextSize(textSize);

		textPaintsmaller = new Paint();
		textPaintsmaller.setColor(Color.WHITE);
		textPaintsmaller.setAntiAlias(true);
		textPaintsmaller.setDither(true);
		textPaintsmaller.setSubpixelText(true);
		textPaintsmaller.setStyle(Paint.Style.FILL);
		textPaintsmaller.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		textPaintsmaller.setTextAlign(Paint.Align.CENTER);
		textSize = managerInstance.surfaceBigWidth * 11 / 100;
		textPaintsmaller.setTextSize(textSize);
	}

	/**
	 * Update current timer view (invalidate)
	 * */
	public synchronized void postUpdateView() {
		postInvalidate();
	}

	/**
	 * Set progress bar color type (Default is green)
	 * */
	public synchronized void setProgressColorType(int type) {
		if (timerViewState == COUNT_DOWN_STATE)
			colorType = type;
	}

	/**
	 * Restart timer with predefined position and time point
	 * */
	public synchronized void refreshTimer(long startTime, long endTime,
			int timerViewState, int timerViewColor) {
		this.startTimeMillis = startTime;
		this.endTimeMillis = endTime;
		this.timerViewState = timerViewState;
		this.colorType = timerViewColor;

		COUNTER_START_VALUE = (int) (endTime - startTime) / 1000;
		COUNTER_VALUE_MILLIS = (int) (endTime - startTime);
		ANGLE_MULTILPY_FACTOR = (float) MAX_MASK_ANGLE
				/ (float) COUNTER_VALUE_MILLIS;
	}

	/**
	 * Set user win view
	 * */
	public void setWon() {
		timerViewState = WIN_STATE;
	}

	/**
	 * Set user loss view
	 * */
	public void setLost() {
		timerViewState = LOSS_STATE;
	}

	/**
	 * Set user tie view
	 * */
	public void setTie() {
		timerViewState = TIE_STATE;
	}

	/**
	 * Set progress bar state
	 * */
	public void setProgressBar() {
		timerViewState = PROGRESS_BAR_STATE;
	}

	/**
	 * Set view color to green
	 * 
	 * */
	public void setViewColorToGreen() {
		colorType = GREEN_COLOR;
	}

	/**
	 * Set view color to grey
	 * 
	 * */
	public void setViewColorToGrey() {
		colorType = GREY_COLOR;
	}

	/**
	 * Set view color to red
	 * 
	 * */
	public void setViewColorToRed() {
		colorType = RED_COLOR;
	}

}
