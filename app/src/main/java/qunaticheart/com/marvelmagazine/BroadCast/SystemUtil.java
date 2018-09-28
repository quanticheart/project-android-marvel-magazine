package qunaticheart.com.marvelmagazine.BroadCast;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;

import java.util.Objects;

public class SystemUtil {
    private static Snackbar snackbar;
    private static Activity activity;

    public SystemUtil(Activity mActivity) {
        activity = mActivity;
    }

    public static boolean connection() {

        ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            if (snackbar != null) {
                snackbar.dismiss();
                snackbar = null;
            }
            return true;
        } else {
            try {
                snackbar = Snackbar.make(Objects.requireNonNull(activity.getCurrentFocus()), "No Conection", Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
            } catch (Exception e) {
            }

            return false;
        }

    }

}
