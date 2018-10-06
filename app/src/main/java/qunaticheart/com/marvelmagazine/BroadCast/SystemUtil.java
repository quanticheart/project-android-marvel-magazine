package qunaticheart.com.marvelmagazine.BroadCast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class SystemUtil {

    @SuppressLint("StaticFieldLeak")
    private static Activity activity;

    //==============================================================================================
    //
    // Constructor
    //
    //==============================================================================================

    public SystemUtil(Activity mActivity) {
        activity = mActivity;
    }

    //==============================================================================================
    //
    // connection
    //
    //==============================================================================================

    public static void connection() {

        ConnectivityManager manager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert manager != null;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            connectionVerify.ConnectionOK();
        } else {
            connectionVerify.ConnectionFail();
        }
    }

    // ** Interface Connection
    private static ConnectionVerify connectionVerify;

    public static void setConnectionVerify(ConnectionVerify connectionVerify) {
        SystemUtil.connectionVerify = connectionVerify;
    }

    public interface ConnectionVerify {
        void ConnectionOK();

        void ConnectionFail();
    }

}
