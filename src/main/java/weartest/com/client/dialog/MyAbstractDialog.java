package weartest.com.client.dialog;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by oleskiy on 24.08.16.
 */
public  class MyAbstractDialog extends android.app.Dialog {

    protected Context context;
    protected UtilityFunctions.GeneralHandler handler;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MyAbstractDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) { // for API > 10 apply hardware acceleration for a window
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.context = context;
        this.handler = new UtilityFunctions.GeneralHandler();
    }

    /**
     * Set dialogs Dim behind amount
     *
     * @param dimmAmount
     *            Dim amount
     */
    public void setDimAmmount(float dimAmount) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.dimAmount = dimAmount;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * Dismiss dialog with delay
     *
     * @param dimmAmount
     *            Dim amount
     */
    public void dismissWithDelay() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(this != null) {
                    try {
                        dismiss();
                    } catch (Exception e) {
                        //   mLogger.printError("Abstract Dialog - Dismiss Crush");
                    }
                }
            }
        }, 1050);
    }
}
final class UtilityFunctions {

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
