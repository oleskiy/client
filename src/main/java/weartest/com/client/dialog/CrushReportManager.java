package weartest.com.client.dialog;

import android.annotation.TargetApi;
import android.app.*;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.WindowManager;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import weartest.com.client.MainActivity;
import weartest.com.client.SplashActivity;
import weartest.com.client.language.LanguageManager;

public class CrushReportManager {

	private static final String EMAIL_SEND = "grach.doc@gmail.com";

	private Context context;
	private AlertDialog alertDialogBuilder;
	private String fileName;

	private OnReportActionDialogCallback actionDialogCallback;

	public CrushReportManager(Context ctx, String fileName) {
		context = ctx;
		this.fileName = fileName;
	}

	/**
	 * Check if to show crush report notification
	 * */
	public boolean isToShowCrushNotification() {
		File file = new File(Environment.getExternalStorageDirectory()
				+ File.separator + fileName);
		if (file.exists())
			return true;
		else
			return false;
	}

	/**
	 * Get crush report from save file if exist
	 * */
	private String getCrushReport() {
		String result = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File file = new File(Environment.getExternalStorageDirectory(),
					File.separator + fileName);
			if (file.exists()) {
				StringBuilder text = new StringBuilder();

				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String line;
					while ((line = br.readLine()) != null) {
						text.append(line);
						text.append('\n');
					}

					result = text.toString();
					br.close();
					file.delete();
				} catch (IOException e) {
					//mLogger.printError(e.getMessage());
				}
			}
		}

		String madeReport = prepareCrushReport(result);

		return madeReport;
	}

	/**
	 * Prepare crush report
	 * 
	 * @param trace
	 *            stuck trace
	 * */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private String prepareCrushReport(String trace) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("----------------------------------------------\n");
		stringBuilder.append("DEVICE: " + Build.DEVICE + "\n");

		int width;
		int height = 0;
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
			width = wm.getDefaultDisplay().getWidth();
			height = wm.getDefaultDisplay().getHeight();
		} else {
			Point point = new Point();
			wm.getDefaultDisplay().getSize(point);
			width = point.x;
			height = point.y;
		}

		stringBuilder.append("DISPLAY: " + "WIDTH: " + width + "   HEIGHT: "
				+ height + "\n");
		stringBuilder.append("MANUFACTURER: " + Build.MANUFACTURER + "\n");
		stringBuilder.append("MODEL: " + Build.MODEL + "\n");
		stringBuilder.append("PRODUCT: " + Build.PRODUCT + "\n");
		stringBuilder.append("SDK NUM TYPE: " + Build.VERSION.SDK_INT + "\n");
		stringBuilder.append("SDK NAME: " + Build.VERSION.RELEASE + "\n");
		stringBuilder
				.append("PACKAGE NAME: " + context.getPackageName() + "\n");

		PackageInfo pInfo;
		try {
			pInfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			stringBuilder.append("APP VERSION NAME: " + pInfo.versionName
					+ "\n");
			stringBuilder.append("APP VERSION CODE: " + pInfo.versionCode
					+ "\n");
			if (context.getPackageName() != null) {
				if (context.getPackageName().contains("com.spotoption.android")) {
					stringBuilder.append("LABEL: "
							+ context.getPackageName().replace(
									"com.spotoption.android.", "") + "\n");
				}
			}

		} catch (NameNotFoundException e) {
			//mLogger.printInfo("ERROR : " + e.getMessage());
		}

		stringBuilder
				.append("----------------------------------------------\n\n\n");
		stringBuilder.append(trace + "\n");

		return stringBuilder.toString();
	}

	/**
	 * Start crush notification dialog
	 * */
	public void startCrushNotificationDialog(final Context context) {

		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("Crash report send?");
		dialog.setPositiveButton(LanguageManager.getLanguageStringValue(LanguageManager.OK), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				String report = getCrushReport();
				//	alertDialogBuilder.dismiss();
				sendEmail(EMAIL_SEND,
						"Label name"
								+ " CrashReport !", report);




				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					File file = new File(Environment
							.getExternalStorageDirectory(),
							File.separator + fileName);
					if (file.exists()) {
						file.delete();
					}
				}
			}
		})
				.setNegativeButton(LanguageManager.getLanguageStringValue(LanguageManager.CANCEL), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						if (Environment.getExternalStorageState().equals(
								Environment.MEDIA_MOUNTED)) {
							File file = new File(Environment
									.getExternalStorageDirectory(),
									File.separator + fileName);
							if (file.exists()) {
								file.delete();
							}
						}
						Intent intent = new Intent(context, MainActivity.class);
						context.startActivity(intent);
					}
				});
		dialog.show();



	}

	/**
	 * Start crush notification dialog
	 * */
	public void setCrushReportDialogActionCallback(
			OnReportActionDialogCallback actionDialogCallback) {
		this.actionDialogCallback = actionDialogCallback;
	}

	/**
	 * Report action dialog callback
	 * */
	public interface OnReportActionDialogCallback {
		abstract void onCancel();
	}

	/**
	 * Send mail with crush report via Gmail application
	 * 
	 * @param email
	 *            Email to send report to
	 * @param subject
	 *            Subject
	 * @param content
	 *            the content
	 * */
	private void sendEmail(String email, String subject, String content) {

		Uri emailUri = Uri.parse("mailto:" + email);
		Intent intent = new Intent(Intent.ACTION_SENDTO, emailUri);
		intent.setClassName("com.google.android.gm",
				"com.google.android.gm.ComposeActivityGmail");
		intent.putExtra("android.intent.extra.SUBJECT", subject);
		intent.putExtra("android.intent.extra.TEXT", content);
		context.startActivity(intent);
	}

}
