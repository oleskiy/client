package weartest.com.client.dialog;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by oleskiy on 24.08.16.
 */
public class CustomUncaughtExpetionHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler defaultUEH;
    String fileName;

    public CustomUncaughtExpetionHandler(String fileName) {

        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        this.fileName = fileName;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        writeToFile(Log.getStackTraceString(e), fileName);
        defaultUEH.uncaughtException(t, e);
    }

    private void writeToFile(String stacktrace, String filename) {

        File file = new File(Environment.getExternalStorageDirectory()
                + File.separator + filename);
        if (file.exists())
            file.delete();

        try {
            BufferedWriter bos = new BufferedWriter(new FileWriter(file));
            bos.write(stacktrace);
            bos.flush();
            bos.close();
        } catch (Exception e) {
          //  mLogger.printError(e.getMessage());
        }
    }

}
