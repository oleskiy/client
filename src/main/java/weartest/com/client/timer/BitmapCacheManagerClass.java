package weartest.com.client.timer;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.util.LruCache;

public class BitmapCacheManagerClass {

	private LruCache<String, Bitmap> mMemoryCache;

	public BitmapCacheManagerClass(Context ctx) {
		initLruBitmapCache(ctx);
	}

	/**
	 * Initiate LRU bitmap cache with specific size (Used to safely hold bitmaps
	 * without crushing the application when memory exceed
	 * 
	 * */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	private void initLruBitmapCache(Context ctx) {
		// Get max available VM memory, exceeding this amount will throw an
		// OutOfMemory exception. Stored in kilobytes as LruCache takes an
		// int in its constructor.
		final int memClass = ((ActivityManager) ctx
				.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		// Use 1/8th of the available memory for this memory cache.
		final int cacheSize = 1024 * 1024 * memClass / 4;

		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {

			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// The cache size will be measured in kilobytes rather than
				// number of items.
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) { // for
																					// API
																					// <
																					// 12
					return bitmap.getRowBytes() * bitmap.getHeight();
				} else { // for API > 12
					return bitmap.getByteCount() / 1024;
				}
			}
		};
	}

	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	public Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}
}
