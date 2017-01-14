package weartest.com.client.timer;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public final class UtilityFunctions {

	/**
	 * Convert DP units to its real pixel values on each particular device
	 * 
	 * @param ctx
	 *            application context
	 * @param dp
	 *            amount in DP units
	 * 
	 * @return amount in PX units
	 * 
	 * */
	public static int pxFromDp(Context ctx, float dp) {
		return (int) (dp * ctx.getResources().getDisplayMetrics().density);
	}

	/**
	 * Execution callback used for local use
	 * 
	 * */
	public interface ExecutionCallback {
		public abstract void onExecute(boolean isValid);
	}

	/**
	 * Used static to avoid leaks
	 * */
	public static class GeneralHandler extends Handler {
		@Override
		public void handleMessage(Message message) {
		}
	}
}
