package weartest.com.client.timer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import weartest.com.client.R;
import weartest.com.client.timer.SixtySecondSmallView.TimerFinishCallback;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class SixtySecondViewManager implements PositionTimerView.RemoveTimerCallback {

	private static final String SMALL_EMPTY_CIRCLE = "s_empty";
	private static final String SMALL_GREEN_FULL_CIRCLE = "s_green_full";
	private static final String SMALL_RED_FULL_CIRCLE = "s_red_full";
	private static final String SMALL_GREY_FULL_CIRCLE = "s_grey_full";
	private static final String SMALL_GREEN_EMPTY_CIRCLE = "s_green_empty";
	private static final String SMALL_RED_EMPTY_CIRCLE = "s_red_empty";
	private static final String SMALL_GREY_EMPTY_CIRCLE = "s_grey_empty";

	private static final String BIG_GREEN_FULL_CIRCLE = "b_green_full";
	private static final String BIG_RED_FULL_CIRCLE = "b_red_full";
	private static final String BIG_GREY_FULL_CIRCLE = "b_grey_full";
	private static final String BIG_GREEN_EMPTY_CIRCLE = "b_green_empty";
	private static final String BIG_RED_EMPTY_CIRCLE = "b_red_empty";
	private static final String BIG_GREY_EMPTY_CIRCLE = "b_grey_empty";

	private static final String WHITE_ARROW = "white_arrow";
	private static final String LOADING_CIRCLE = "loading_circle";

	private Context context;
	private LinearLayout sixtySecSrollViewHolder;
	private LinearLayout bigViewContainer;
	private Timer updateTimer;

	protected Bitmap small_empty;
	protected Bitmap smallGreen_full;
	protected Bitmap smallRed_full;
	protected Bitmap smallGrey_full;
	protected Bitmap smallGreen_center;
	protected Bitmap smallRed_center;
	protected Bitmap smallGrey_center;

	protected Bitmap bigGreen_full;
	protected Bitmap bigRed_full;
	protected Bitmap bigGrey_full;
	protected Bitmap bigGreen_empty;
	protected Bitmap bigRed_empty;
	protected Bitmap bigGrey_empty;

	protected Bitmap white_arrow;

	protected int surfaceSmallHeight;
	protected int surfaceSmallWidth;
	protected int surfaceBigHeight;
	protected int surfaceBigWidth;

	private ArrayList<PositionTimerView> timerViews = new ArrayList<PositionTimerView>();
	private HashMap<String, PositionTimerView> timerMapSet = new HashMap<String, PositionTimerView>();

	private SixtySecondLargeView sixtySecondLargeView = null;
	private LinearLayout sixtyBigViewWrapper = null;
	private CustomProgressBar sixtySecondLargeViewLoadingProgressBar = null;
	private int circlePivotX;
	private int circlePivoty;

	private int currentPickedTimerIndex = -1;
	private CloseViewCallback closeViewCallback;
	private ScrollCallback scrollCallback;
	private LargeSixtyViewStatusUpdateCallback sixtyViewStatusUpdateCallback;

	private Resources resources;
	BitmapFactory.Options bfOptions;

	public static BitmapCacheManagerClass bitmapCacheManager;
	private boolean smallViewBitmapsInitiated = false;
	private boolean largeViewBitmapsInitiated = false;

	private int largesixtyViewMAxSize;
	private UtilityFunctions.GeneralHandler handler = new UtilityFunctions.GeneralHandler();

	public SixtySecondViewManager(Context ctx, LinearLayout sContainer,
								  final LinearLayout bConainer, final int largeImageMaxSize,CustomProgressBar mCustomProgressBar) {

		this.context = ctx;
		this.sixtySecSrollViewHolder = sContainer;
		this.bigViewContainer = bConainer;
		this.resources = context.getResources();
		this.largesixtyViewMAxSize = largeImageMaxSize;
		bitmapCacheManager = new BitmapCacheManagerClass(ctx);
		// Collections.synchronizedList(timerViews);
		// Collections.synchronizedMap(timerMapSet);
		sixtySecondLargeViewLoadingProgressBar = mCustomProgressBar;
		sixtyBigViewWrapper = new LinearLayout(context);
		sixtySecondLargeViewLoadingProgressBar
				.setImageBitmap(getLoadingCircleChahedImage());
		bfOptions = new BitmapFactory.Options(); 
		bfOptions.inDither=false;          //Disable Dithering mode

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				if (!smallViewBitmapsInitiated)
					initSmallViewBitmaps();
			}
		});
		thread.start();
	}

	/**
	 * Initiate and populate local bitmaps used for drawing
	 * 
	 * */
	public void initSmallViewBitmaps() {
		small_empty = bitmapCacheManager.getBitmapFromMemCache(SMALL_EMPTY_CIRCLE);
		if (small_empty == null) {
			small_empty = BitmapFactory.decodeResource(resources,R.drawable.small_empty_bg,bfOptions);
			bitmapCacheManager.addBitmapToMemoryCache(SMALL_EMPTY_CIRCLE, small_empty);
		}

		smallGreen_full = bitmapCacheManager.getBitmapFromMemCache(SMALL_GREEN_FULL_CIRCLE);
		if (smallGreen_full == null) {
			smallGreen_full = BitmapFactory.decodeResource(resources,R.drawable.small_full_green_bg,bfOptions);
			bitmapCacheManager.addBitmapToMemoryCache(SMALL_GREEN_FULL_CIRCLE, smallGreen_full);
		}

		smallRed_full = bitmapCacheManager.getBitmapFromMemCache(SMALL_RED_FULL_CIRCLE);
		if (smallRed_full == null) {
			smallRed_full = BitmapFactory.decodeResource(context.getResources(), R.drawable.small_full_red_bg,bfOptions);
			bitmapCacheManager.addBitmapToMemoryCache(SMALL_RED_FULL_CIRCLE, smallRed_full);
		}

		smallGrey_full = bitmapCacheManager.getBitmapFromMemCache(SMALL_GREY_FULL_CIRCLE);
		if (smallGrey_full == null) {
			smallGrey_full = BitmapFactory.decodeResource(context.getResources(), R.drawable.small_full_grey_bg,bfOptions);
			bitmapCacheManager.addBitmapToMemoryCache(SMALL_GREY_FULL_CIRCLE, smallGrey_full);
		}

		smallGreen_center = bitmapCacheManager.getBitmapFromMemCache(SMALL_GREEN_EMPTY_CIRCLE);
		if (smallGreen_center == null) {
			smallGreen_center = BitmapFactory.decodeResource(context.getResources(), R.drawable.small_green_center_bg,bfOptions);
			bitmapCacheManager.addBitmapToMemoryCache(SMALL_GREEN_EMPTY_CIRCLE, smallGreen_center);
		}

		smallRed_center = bitmapCacheManager.getBitmapFromMemCache(SMALL_RED_EMPTY_CIRCLE);
		if (smallRed_center == null) {
			smallRed_center = BitmapFactory.decodeResource(context.getResources(), R.drawable.small_red_center_bg,bfOptions);
			bitmapCacheManager.addBitmapToMemoryCache(SMALL_RED_EMPTY_CIRCLE, smallRed_center);
		}

		smallGrey_center = bitmapCacheManager.getBitmapFromMemCache(SMALL_GREY_EMPTY_CIRCLE);
		if (smallGrey_center == null) {
			smallGrey_center = BitmapFactory.decodeResource(context.getResources(), R.drawable.small_grey_center_bg,bfOptions);
			bitmapCacheManager.addBitmapToMemoryCache(SMALL_GREY_EMPTY_CIRCLE, smallGrey_center);
		}

		white_arrow = bitmapCacheManager.getBitmapFromMemCache(WHITE_ARROW);
		if (white_arrow == null) {
			white_arrow = BitmapFactory.decodeResource(resources,R.drawable.sixty_seconds_white_arrow,bfOptions);
			bitmapCacheManager.addBitmapToMemoryCache(WHITE_ARROW,white_arrow);
		}

		surfaceSmallWidth = small_empty.getWidth();
		surfaceSmallHeight = small_empty.getHeight();

		smallViewBitmapsInitiated = true;
	}

	/**
	 * Initiate and populate local bitmaps used for drawing
	 * 
	 * */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public void initBigViewBitmaps(int bigImageMaxSize) {
		// In order to find initial image size
		bigGreen_full = BitmapFactory.decodeResource(context.getResources(), R.drawable.big_full_green_bg,bfOptions);

		if (bigImageMaxSize != -1) {
			int newWidth = 0;
			int newHeight = 0;

			int width = 300;//bigGreen_full.getWidth();
			int height = 300;//bigGreen_full.getHeight();

			if (width > height) {
				newWidth = bigImageMaxSize;
				newHeight = (bigImageMaxSize * height) / width;
			} else if (width < height) {
				newHeight = bigImageMaxSize;
				newWidth = (bigImageMaxSize * width) / height;
			} else if (width == height) {
				newHeight = bigImageMaxSize;
				newWidth = bigImageMaxSize;
			}

			surfaceBigWidth = newWidth;
			surfaceBigHeight = newHeight;
		} else {

			surfaceBigWidth = bigGreen_full.getWidth();
			surfaceBigHeight = bigGreen_full.getHeight();
		}
		
		bigGreen_full = bitmapCacheManager.getBitmapFromMemCache(BIG_GREEN_FULL_CIRCLE);
		if (bigGreen_full == null) {
			bigGreen_full = BitmapFactory.decodeResource(context.getResources(), R.drawable.big_full_green_bg,bfOptions);
			if (bigImageMaxSize != -1)
				bigGreen_full = Bitmap.createScaledBitmap(bigGreen_full,surfaceBigWidth, surfaceBigHeight, false);
			bitmapCacheManager.addBitmapToMemoryCache(BIG_GREEN_FULL_CIRCLE, bigGreen_full);
		}
		else
		{

			Bitmap resizedBitmap = Bitmap.createScaledBitmap(
					bigGreen_full, surfaceBigWidth, surfaceBigHeight, false);
			bigGreen_full=resizedBitmap;
		}

		// We cache bitmaps in LRU cache to not load them each time we open a big view		 
		bigRed_full = bitmapCacheManager.getBitmapFromMemCache(BIG_RED_FULL_CIRCLE);
		if (bigRed_full == null) {
			bigRed_full = BitmapFactory.decodeResource(context.getResources(),R.drawable.big_full_red_bg,bfOptions);
			if (bigImageMaxSize != -1)
				bigRed_full = Bitmap.createScaledBitmap(bigRed_full,surfaceBigWidth, surfaceBigHeight, false);
			bitmapCacheManager.addBitmapToMemoryCache(BIG_RED_FULL_CIRCLE, bigRed_full);
		}else
		{
			Bitmap resizedBitmap = Bitmap.createScaledBitmap(
					bigRed_full, surfaceBigWidth, surfaceBigHeight, false);
			bigRed_full=resizedBitmap;
		}

		bigGrey_full = bitmapCacheManager.getBitmapFromMemCache(BIG_GREY_FULL_CIRCLE);
		if (bigGrey_full == null) {
			bigGrey_full = BitmapFactory.decodeResource(context.getResources(),R.drawable.big_full_grey_bg,bfOptions);
			if (bigImageMaxSize != -1)
				bigGrey_full = Bitmap.createScaledBitmap(bigGrey_full,surfaceBigWidth, surfaceBigHeight, false);
			bitmapCacheManager.addBitmapToMemoryCache(BIG_GREY_FULL_CIRCLE, bigGrey_full);
		}else
		{
			Bitmap resizedBitmap = Bitmap.createScaledBitmap(
					bigGrey_full, surfaceBigWidth, surfaceBigHeight, false);
			bigGrey_full=resizedBitmap;
		}

		bigGreen_empty = bitmapCacheManager.getBitmapFromMemCache(BIG_GREEN_EMPTY_CIRCLE);
		if (bigGreen_empty == null) {
			bigGreen_empty = BitmapFactory.decodeResource(context.getResources(), R.drawable.big_empty_green_bg,bfOptions);
			if (bigImageMaxSize != -1)
				bigGreen_empty = Bitmap.createScaledBitmap(bigGreen_empty,surfaceBigWidth, surfaceBigHeight, false);
			bitmapCacheManager.addBitmapToMemoryCache(BIG_GREEN_EMPTY_CIRCLE, bigGreen_empty);
		}else
		{
			Bitmap resizedBitmap = Bitmap.createScaledBitmap(
					bigGreen_empty, surfaceBigWidth, surfaceBigHeight, false);
			bigGreen_empty=resizedBitmap;
		}

		bigRed_empty = bitmapCacheManager.getBitmapFromMemCache(BIG_RED_EMPTY_CIRCLE);
		if (bigRed_empty == null) {
			bigRed_empty = BitmapFactory.decodeResource(context.getResources(),R.drawable.big_empty_red_bg,bfOptions);
			if (bigImageMaxSize != -1)
				bigRed_empty = Bitmap.createScaledBitmap(bigRed_empty,surfaceBigWidth, surfaceBigHeight, false);
			bitmapCacheManager.addBitmapToMemoryCache(BIG_RED_EMPTY_CIRCLE, bigRed_empty);
		}else
		{
			Bitmap resizedBitmap = Bitmap.createScaledBitmap(
					bigRed_empty, surfaceBigWidth, surfaceBigHeight, false);
			bigRed_empty=resizedBitmap;
		}

		bigGrey_empty = bitmapCacheManager.getBitmapFromMemCache(BIG_GREY_EMPTY_CIRCLE);
		if (bigGrey_empty == null) {
			bigGrey_empty = BitmapFactory.decodeResource(context.getResources(), R.drawable.big_empty_grey_bg,bfOptions);
			if (bigImageMaxSize != -1)
				bigGrey_empty = Bitmap.createScaledBitmap(bigGrey_empty,surfaceBigWidth, surfaceBigHeight, false);
			bitmapCacheManager.addBitmapToMemoryCache(BIG_GREY_EMPTY_CIRCLE, bigGrey_empty);
		}else
		{
			Bitmap resizedBitmap = Bitmap.createScaledBitmap(
					bigGrey_empty, surfaceBigWidth, surfaceBigHeight, false);
			bigGrey_empty=resizedBitmap;
		}

		largeViewBitmapsInitiated = true;
	}

	/**
	 * Start timer views updating timer
	 * 
	 * */
	public void startUpdatingViews() {
		startUpdateTimer();
		Log.d("Test","startUpdatingViews");
	}

	/**
	 * Stop timer views updating timer
	 * 
	 * */
	public void stopUpdatingViews() {
		Log.d("Test","stopUpdatingViews");
		stopUpdateTimer();
	}

	/**
	 * Sixty seconds update views timer
	 * 
	 * */
	private void startUpdateTimer() {
		if (updateTimer != null) {
			updateTimer.cancel();
			updateTimer.purge();
		}
		updateTimer = new Timer();
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				for (int i = 0; i < timerViews.size(); i++) {
					timerViews.get(i).sixtySecondSmallImageView
							.postUpdateView();

					if (sixtySecondLargeView != null)
						sixtySecondLargeView.postUpdateView();
				}
			}
		};
		updateTimer.schedule(timerTask, 0, 250);
	}

	/**
	 * Stop option timer updates
	 * */
	private void stopUpdateTimer() {
		if (updateTimer != null) {
			updateTimer.cancel();
			updateTimer.purge();
			Log.d("Test","stopUpdateTimer");
		}
	}

	/**
	 * Create new sixty second timer view
	 * */
	public void createSixtySecondView(String positionId,TimerFinishCallback timerFinishCallback,PositionTimerView.TimerClickListener timerClickListener) {
		//initBigViewBitmaps(520);
		PositionTimerView positionTimerView = new PositionTimerView(context,positionId);
		//Position position = getOpenPositionByID(positionId);
		//if (position != null) {
			if (!timerMapSet.containsKey(positionId)) {
				LinearLayout layout = positionTimerView.initTimerView(0, 60000, this,timerFinishCallback, timerClickListener, this);
				
				int currTime = positionTimerView.getCurrentTimeLeft();
				int setPos = 0;
				
				for(int i = 0 ; i < timerViews.size() ; i ++) {
					if(currTime > timerViews.get(i).getCurrentTimeLeft()) {						
						break;
					}					
					setPos = i + 1;
				}
				
				timerViews.add(setPos, positionTimerView);
				timerMapSet.put(positionId, positionTimerView);
				
				sixtySecSrollViewHolder.addView(layout,0,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
			}
		//}

		// If big timer view is shown leave the white array point on the last
		// added view
		if (sixtySecondLargeView != null) {
			currentPickedTimerIndex++;

			if (timerViews.size() > currentPickedTimerIndex) {
				PositionTimerView timerView = timerViews.get(currentPickedTimerIndex);

				if (timerView != null)
					updatePickedTimerView(timerView.mPositionId);
			}
		}
	}

	/**
	 * Create new sixty second timer view (Used in the open/expired positions
	 * window to show sixty sec status)
	 * */
	public SixtySecondSmallView createRawSmallSixtySecView(
			TimerFinishCallback timerFinishCallback) {
		//initBigViewBitmaps(largesixtyViewMAxSize);
		SixtySecondSmallView smallImageView = new SixtySecondSmallView(context,
				this);

		smallImageView.setOnTimerFinishCallback(timerFinishCallback);
		return smallImageView;
	}

	/**
	 * Create new sixty second timer view
	 * */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void createBigSixtySecondView(final String positionId) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				if (!largeViewBitmapsInitiated)
					initBigViewBitmaps(largesixtyViewMAxSize);

				if (sixtySecondLargeView == null) {
					sixtySecondLargeView = new SixtySecondLargeView(context,SixtySecondViewManager.this);
					// for API > 10 apply hardware acceleration for a window
					if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) { 
						sixtySecondLargeView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
					}

					handler.post(new Runnable() {
						@Override
						public void run() {
							sixtyBigViewWrapper.removeAllViews();
							bigViewContainer.removeAllViews();

							RelativeLayout.LayoutParams rparams = new RelativeLayout.LayoutParams(
									RelativeLayout.LayoutParams.WRAP_CONTENT,
									RelativeLayout.LayoutParams.WRAP_CONTENT);
							sixtyBigViewWrapper.addView(sixtySecondLargeView,rparams);

							LinearLayout.LayoutParams linParams = new LinearLayout.LayoutParams(
									LinearLayout.LayoutParams.WRAP_CONTENT,
									LinearLayout.LayoutParams.WRAP_CONTENT);
							bigViewContainer.addView(sixtyBigViewWrapper,linParams);

							RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
									UtilityFunctions.pxFromDp(context, 35),
									UtilityFunctions.pxFromDp(context, 35));
							params.addRule(RelativeLayout.CENTER_IN_PARENT);

							// Initiate big sixty view loading circle
							if (sixtySecondLargeViewLoadingProgressBar == null) {

								circlePivotX = params.width / 2;
								circlePivoty = params.height / 2;

								sixtySecondLargeViewLoadingProgressBar = new CustomProgressBar(
										context);
								sixtySecondLargeViewLoadingProgressBar
										.setVisibility(View.GONE);
								sixtySecondLargeViewLoadingProgressBar
										.setImageBitmap(getLoadingCircleChahedImage());
							}

							sixtyBigViewWrapper.addView(sixtySecondLargeViewLoadingProgressBar,params);
							updatePickedTimerView(positionId);
							startUpdatingViews();
						}
					});

				}
			}
		});
		thread.start();
	}

	/**
	 * Get loading circle cached image bitmap
	 * 
	 * @return bitmap
	 * */
	protected Bitmap getLoadingCircleChahedImage() {
		Bitmap bmp = bitmapCacheManager.getBitmapFromMemCache(LOADING_CIRCLE);
		if (bmp == null) {
			bmp = BitmapFactory.decodeResource(context.getResources(),R.drawable.loading_circle_small);
			bitmapCacheManager.addBitmapToMemoryCache(LOADING_CIRCLE, bmp);
		}
		return bmp;
	}

	/**
	 * Remove big sixty second timer view
	 * */
	public void removeBigSixtySecondView() {
		hideBigSixtySecLoadingProgressBar();
		sixtyBigViewWrapper.removeAllViews();
		bigViewContainer.removeAllViews();
		sixtySecondLargeView = null;
		currentPickedTimerIndex = -1;
	}

	/**
	 * Check if timer position for specified position is already exist and
	 * showing
	 * */
	public boolean isPositionTimerExist(String positionId) {
		return timerMapSet.containsKey(positionId);
	}

	/**
	 * Update timer views according to the new rate and entry rate data a timer
	 * has
	 * 
	 * @param newRate
	 *            new rate came from streamer
	 * */
	public enum PositionStatus {

		STATUS_WIN, STATUS_LOSE, STATUS_TIE
	}
	public void updateAllTimersAccordingToNewRateLevel(String newRate) {
		PositionStatus status = PositionStatus.STATUS_TIE;
		// Update small timer views
		for (int i = 0; i < timerViews.size(); i++) {
			/*Position position = getOpenPositionByID(timerViews
					.get(i).mPositionId);*/

			//if (position != null) {
				//if (position.entryRate != null) {
					status =PositionStatus.STATUS_WIN;// position.getCurrentWinLossStatus(newRate);

					switch (status) {
					case STATUS_WIN:
						timerViews.get(i).setViewColorToGreen();
						break;
					case STATUS_LOSE:
						timerViews.get(i).setViewColorToRed();
						break;
					case STATUS_TIE:
						timerViews.get(i).setViewColorToGrey();
						break;
					}

					// Update big timer view of currently picked small timer if
					// shown
					if (sixtySecondLargeView != null) {
						if (currentPickedTimerIndex < timerViews.size()) {
							if (i == currentPickedTimerIndex) {

								switch (status) {
								case STATUS_WIN:
									sixtySecondLargeView.setViewColorToGreen();
									break;
								case STATUS_LOSE:
									sixtySecondLargeView.setViewColorToRed();
									break;
								case STATUS_TIE:
									sixtySecondLargeView.setViewColorToGrey();
									break;
								}

								if (sixtyViewStatusUpdateCallback != null)
									sixtyViewStatusUpdateCallback
											.onStatusChanged(timerViews.get(i).mPositionId);
							}
						}
					}
			//	}
			//}
		}
	}

	/**
	 * Reset timers color to neutral (TIE) - used in case the streamer is stop
	 * working
	 * 
	 * */
	public void setTimersColorToNeutral() {
		for (int i = 0; i < timerViews.size(); i++) {
			timerViews.get(i).setViewColorToGrey();
		}
		if (sixtySecondLargeView != null) {
			sixtySecondLargeView.setViewColorToGrey();
		}
	}

	/**
	 * Set timer to lost view
	 * 
	 * @param optionID
	 *            option id
	 * */
	public void setUserLostView(String positionId) {
		if (timerMapSet.containsKey(positionId)) {
			timerMapSet.get(positionId).setUserLost();
		}
		if (sixtySecondLargeView != null) {
			if (findTimerArrayIndex(positionId) == currentPickedTimerIndex)
				sixtySecondLargeView.setLost();
		}
	}

	/**
	 * Set timer to tie view
	 * 
	 * @param optionID
	 *            option id
	 * */
	public void setUserTieView(String positionId) {
		if (timerMapSet.containsKey(positionId)) {
			timerMapSet.get(positionId).setUserTie();
		}
		if (sixtySecondLargeView != null) {
			if (findTimerArrayIndex(positionId) == currentPickedTimerIndex)
				sixtySecondLargeView.setTie();
		}
	}

	/**
	 * Set timer to lost view
	 * 
	 * @param optionID
	 *            option id
	 * */
	public void setUserWonView(String positionId) {
		if (timerMapSet.containsKey(positionId)) {
			timerMapSet.get(positionId).setUserWon();
		}
		if (sixtySecondLargeView != null) {
			if (findTimerArrayIndex(positionId) == currentPickedTimerIndex)
				sixtySecondLargeView.setWon();
		}
	}

	/**
	 * Show small sixty view loading progress bar
	 * 
	 * @param optionID
	 *            option id
	 * */
	public void showLoadingProgressBar(String positionId) {
		if (timerMapSet.containsKey(positionId)) {
			timerMapSet.get(positionId).showProgressBar();
		}

		// In case the big view is showing
		if (sixtySecondLargeView != null) {
			int index = findTimerArrayIndex(positionId);
			if (index == currentPickedTimerIndex) {
				showBigSixtySecLoadingProgressBar();
			}
		}
	}

	/**
	 * Hide small sixty view loading progress bar
	 * 
	 * @param optionID
	 *            option id
	 * */
	public void hideLoadingProgressBar(String positionId) {
		if (timerMapSet.containsKey(positionId)) {
			timerMapSet.get(positionId).hideProgressBar();
		}

		// In case the big view is showing
		if (sixtySecondLargeView != null) {
			int index = findTimerArrayIndex(positionId);
			if (index == currentPickedTimerIndex) {
				hideBigSixtySecLoadingProgressBar();
			}
		}
	}

	/**
	 * Show Big sixty view loading progress bar
	 * 
	 * @param optionID
	 *            option id
	 * */
	public void showBigSixtySecLoadingProgressBar() {
		if (sixtySecondLargeView != null) {
			if (sixtySecondLargeViewLoadingProgressBar != null) {
				sixtySecondLargeView.setProgressBar();
				sixtySecondLargeViewLoadingProgressBar.setVisibility(View.VISIBLE);
				sixtySecondLargeViewLoadingProgressBar.startLoadingAnimation(circlePivotX, circlePivoty);
			}
		}
	}

	/**
	 * Hide Big sixty view loading progress bar
	 * 
	 * @param optionID
	 *            option id
	 * */
	public void hideBigSixtySecLoadingProgressBar() {
		if (sixtySecondLargeViewLoadingProgressBar != null) {
			sixtySecondLargeViewLoadingProgressBar.stopLoadingAnimation();
			sixtySecondLargeViewLoadingProgressBar.setVisibility(View.GONE);			
		}
	}

	/**
	 * Clear all white arrow pointers from top of views Doesn't not remove them
	 * !, Make them invisible - what means that the space they took in the
	 * layout is still visible and exist
	 * 
	 * */
	public void clearAllWhitePointersFromTimers() {
		for (int i = 0; i < timerViews.size(); i++) {
			timerViews.get(i).showWhiteArrowVisible(false);
		}
	}

	/**
	 * Remove all white arrow pointers from top of views
	 * 
	 * */
	public void removeAllWhitePointersFromTimers() {
		for (int i = 0; i < timerViews.size(); i++) {
			timerViews.get(i).removeWhiteArrow();
		}
	}

	/**
	 * Show single white arrow pointer above specified timer and update the
	 * large timer view.
	 * 
	 * @param positionId
	 *            option id (unique for each timer view)
	 * 
	 * */
	public void updatePickedTimerView(String positionId) {
		clearAllWhitePointersFromTimers();
		if (timerMapSet.containsKey(positionId)) {
			PositionTimerView positionTimerView = timerMapSet.get(positionId);
			positionTimerView.showWhiteArrowVisible(true);
			
			// what we try to do here isto scroll the views in container so that we could see some invisible sixty second view 
				// that got a white arrow and is outof screen bounds 
			  if(currentPickedTimerIndex != -1 ) { 
				  if(scrollCallback != null) {
					  scrollCallback.onScrollRequest(positionTimerView.getViewSize() * currentPickedTimerIndex); 
				 } 				  
			  }	 
		}

		if (sixtySecondLargeView != null) {
			//Position position = getOpenPositionByID(positionId);
			PositionTimerView positionTimerView = timerMapSet.get(positionId);
			//if (position != null) {
				sixtySecondLargeView.refreshTimer(SixtySecondSmallView.getServerRealGMTtime(),
						SixtySecondSmallView.getServerRealGMTtime()+30000,
						positionTimerView.getSmallTimerDrawState(),
						positionTimerView.getSmallTimerColorStatus());
				if (positionTimerView.getSmallTimerDrawState() == SixtySecondSmallView.PROGRESS_BAR_STATE) {
					showBigSixtySecLoadingProgressBar();
				} else {
					hideBigSixtySecLoadingProgressBar();
				}
			//}
			sixtySecondLargeView.postUpdateView();
		}

		currentPickedTimerIndex = findTimerArrayIndex(positionId);
	}

	/**
	 * Find timer current index in the timers array
	 * 
	 * */
	private int findTimerArrayIndex(String positionId) {
		int index = -1;
		if (timerMapSet.containsKey(positionId)) {
			PositionTimerView timerView = timerMapSet.get(positionId);
			index = timerViews.indexOf(timerView);
		}
		return index;
	}

	/**
	 * Swap to next view (In the big timer view mode)
	 * */
	public String swapToNextTimerView() {
		String positionId = null;
		if (sixtySecondLargeView != null) {
			if (currentPickedTimerIndex < timerViews.size() - 1) {
				currentPickedTimerIndex++;
				positionId = timerViews.get(currentPickedTimerIndex).mPositionId;
				updatePickedTimerView(positionId);
			}
		}
		return positionId;
	}

	/**
	 * Swap to previous view (In the big timer view mode)
	 * 
	 * */
	public String swapToPreviousTimerView() {
		String positionId = null;
		if (sixtySecondLargeView != null) {
			if (currentPickedTimerIndex > 0) {
				currentPickedTimerIndex--;
				positionId = timerViews.get(currentPickedTimerIndex).mPositionId;
				updatePickedTimerView(positionId);
			}
		}
		return positionId;
	}

	@Override
	public void onRemove(String positionId) {
		removeSmallSixtySecTimerFromView(positionId);
	}

	/**
	 * Remove small timer from view and update the timers current presentation
	 * if necessary
	 * 
	 * @param positionId
	 *            position id
	 * 
	 * */
	public synchronized void removeSmallSixtySecTimerFromView(String positionId) {
		if (timerMapSet.containsKey(positionId)) {
			int index = findTimerArrayIndex(positionId);

			if (sixtySecondLargeView != null) {
				if (index != -1) { // and if the big sixty second view is
									// showing
					if (index < currentPickedTimerIndex) {
						if (currentPickedTimerIndex > 0)
							currentPickedTimerIndex--;
					} else if (index >= currentPickedTimerIndex) {
						if (index == currentPickedTimerIndex) {
							currentPickedTimerIndex--;
							if (index == 0) {
								if (timerViews.size() > 0)
									currentPickedTimerIndex++;
							}
						}
						// Remove timer
						removeTimerByPositionID(positionId);

						if (timerViews.size() - 1 >= currentPickedTimerIndex)
							updatePickedTimerView(timerViews
									.get(currentPickedTimerIndex).mPositionId);
					}
				}
			} else {
				// Remove timer
				removeTimerByPositionID(positionId);
			}

			if (timerViews.isEmpty()) {
				if (closeViewCallback != null)
					closeViewCallback.onCloseRequest();
				currentPickedTimerIndex = -1;
			}
		}
	}

	/**
	 * Clear all containers and inner arrays
	 * 
	 * */
	public void clearAll() {
		sixtySecSrollViewHolder.removeAllViews();
		bigViewContainer.removeAllViews();
		timerViews.clear();
		timerMapSet.clear();
	}

	/**
	 * Removes a timer by position id
	 * 
	 * */
	public void removeTimerByPositionID(String positionId) {

		if (timerMapSet.containsKey(positionId)) {
			final PositionTimerView view = timerMapSet.remove(positionId);
			timerViews.remove(view);
			final ViewGroup parent = (ViewGroup) view.mView.getParent();
			parent.post(new Runnable() {
				@Override
				public void run() {
					parent.removeView(view.mView);
				}
			});
		}
	}

	/**
	 * Set callback that will be launched when a expanded big timer window
	 * should be closed
	 * 
	 * */
	public void setOnInnerCloseRequestListener(
			CloseViewCallback closeViewCallback) {
		this.closeViewCallback = closeViewCallback;
	}

	/**
	 * Set callback that will be launched when a expanded big timer window
	 * should be closed
	 * 
	 * */
	public void setOnScrollRequestCallback(ScrollCallback scrollCallback) {
		this.scrollCallback = scrollCallback;
	}

	/**
	 * Set callback that will be launched when a expanded big timer is shown and
	 * a change on picked psotion timer has been made (win/loos/tie)
	 * 
	 * */
	public void setOnLargeSixtyViewStatusUpdateCallback(
			LargeSixtyViewStatusUpdateCallback sixtyViewStatusUpdateCallback) {
		this.sixtyViewStatusUpdateCallback = sixtyViewStatusUpdateCallback;
	}

	public interface CloseViewCallback {
		public void onCloseRequest();
	}

	/**
	 * Used in large sixty view when swamping between small sixty views
	 * */
	public interface ScrollCallback {
		public void onScrollRequest(int scrollValue);
	}

	/**
	 * Used in large sixty view when swamping between small sixty views
	 * */
	public interface LargeSixtyViewStatusUpdateCallback {
		public void onStatusChanged(String positionId);
	}

}
