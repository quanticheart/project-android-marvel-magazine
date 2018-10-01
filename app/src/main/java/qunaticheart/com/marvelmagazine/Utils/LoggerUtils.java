package qunaticheart.com.marvelmagazine.Utils;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import qunaticheart.com.marvelmagazine.BuildConfig;

public class LoggerUtils {

    public static void LogW(String tag, String msg) {
        if (getStatusDebug()) {
            Log.w(tag, msg);
        }
    }

    public static void LogE(String tag, String msg) {
        if (getStatusDebug()) {
            Log.e(tag, msg);
        }
    }

    public static void callToast(Activity activity, String s) {
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
    }

    //===

    private static boolean getStatusDebug() {
        return BuildConfig.DEBUG;
    }

    public static void showMsg(Activity activity, String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}
