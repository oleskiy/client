package weartest.com.client.dialog;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import weartest.com.client.R;

@SuppressLint("NewApi")
public class Dialog extends MyAbstractDialog {

     ImageView circleView;
     ImageView lableView;
     TextView dialogText;

    public Dialog(Context context, int text_color, int img_res,
                               int dialog_bg_color) {
        super(context);

        setContentView(R.layout.loading_custom_dialog_layout);
        circleView = (ImageView) findViewById(R.id.cirlcleAnimationView);
        lableView = (ImageView) findViewById(R.id.labelInnerView);
        lableView.setImageResource(img_res);
        dialogText = (TextView) findViewById(R.id.loadingDialog_texView);
        dialogText.setTextColor(text_color);

        float[] radii = new float[8];
        radii[0] = UtilityFunctions.pxFromDp(context, 5);
        radii[1] = UtilityFunctions.pxFromDp(context, 5);
        radii[2] = UtilityFunctions.pxFromDp(context, 5);
        radii[3] = UtilityFunctions.pxFromDp(context, 5);
        radii[4] = UtilityFunctions.pxFromDp(context, 5);
        radii[5] = UtilityFunctions.pxFromDp(context, 5);
        radii[6] = UtilityFunctions.pxFromDp(context, 5);
        radii[7] = UtilityFunctions.pxFromDp(context, 5);

        ShapeDrawable background = new ShapeDrawable();
        background.setShape(new RoundRectShape(radii, null, null));
        background.getPaint().setColor(dialog_bg_color);

        LinearLayout backgroundViewLay = (LinearLayout) findViewById(R.id.backgroundDialogView1);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) { // for
            // API
            // <
            // 15
            backgroundViewLay.setBackgroundDrawable(background);
        } else {
            backgroundViewLay.setBackground(background);
        }
        // setDimAmmount(0.3f);
    }

    @Override
    public void onAttachedToWindow() {
        Animation rotation = AnimationUtils.loadAnimation(context,
                R.anim.loading_dialog_rotation_animation);
        circleView.startAnimation(rotation);
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        circleView.clearAnimation();
        super.onDetachedFromWindow();
    }

    @Override
    public void show() {
        Animation rotation = AnimationUtils.loadAnimation(context,
                R.anim.loading_dialog_rotation_animation);
        circleView.startAnimation(rotation);
        super.show();
    }

    /**
     * Add text to loading dialog
     *
     * @param text
     *            text in string representation
     * */
    public void setText(String text) {
        if (text == null)
            dialogText.setVisibility(View.GONE);
        else
            dialogText.setVisibility(View.VISIBLE);
        dialogText.setText(text);
    }

    /**
     * Add text to loading dialog
     *
     * @param res_id
     *            text as a resource id
     * */
    public void setText(int res_id) {
        if (res_id == -1)
            dialogText.setVisibility(View.GONE);
        else
            dialogText.setVisibility(View.VISIBLE);
        dialogText.setText(res_id);
    }



}
