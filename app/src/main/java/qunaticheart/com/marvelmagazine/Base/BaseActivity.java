package qunaticheart.com.marvelmagazine.Base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import qunaticheart.com.marvelmagazine.BroadCast.MyReceiver;
import qunaticheart.com.marvelmagazine.BroadCast.SystemUtil;
import qunaticheart.com.marvelmagazine.Helpers.SplashHelper;
import qunaticheart.com.marvelmagazine.R;

public abstract class BaseActivity extends AppCompatActivity implements SystemUtil.ConnectionVerify {

    //init
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;

    //ConnectionVerify Verifie
    private Snackbar snackbar = null;
    private MyReceiver connectionReceiver;

    public static boolean connected = false;
    public static boolean showSnackbar = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVars();
        initActions();

    }

    private void initVars() {
        connected = false;
        showSnackbar = true;
        //
        activity = BaseActivity.this;
        new SplashHelper(activity);
    }

    private void initActions() {

    }

    //==============================================================================================
    //
    // Systems Verifications
    //
    //==============================================================================================

    private void connectionReceiverRegister() {

        new SystemUtil(activity);
        SystemUtil.setConnectionVerify(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        connectionReceiver = new MyReceiver();
        registerReceiver(connectionReceiver, filter);

    }

    //==============================================================================================
    //
    // @Override connection
    //
    //==============================================================================================

    @Override
    public void ConnectionOK() {
        connected = true;
        if (!verifySnackbar()) {
            clearSnackbar();
        }
    }

    @Override
    public void ConnectionFail() {
        connected = false;
        if (verifySnackbar() && showSnackbar) {
            showSnackbar();
        }
    }

    //==============================================================================================
    //
    // Utils
    //
    //==============================================================================================

    private void showSnackbar() {

        final Runnable setImageRunnable = new Runnable() {
            @Override
            public void run() {
                snackbar = Snackbar.make(Objects.requireNonNull(activity.getCurrentFocus()), R.string.msg_no_connection, Snackbar.LENGTH_INDEFINITE);
                snackbar.show();
//next Update
//                startActivityForResult(new Intent(Settings.ACTION_WIRELESS_SETTINGS), 0);

            }
        };

        TimerTask task = new TimerTask() {
            public void run() {
                activity.runOnUiThread(setImageRunnable);
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 2000);

    }

    private void clearSnackbar() {
        snackbar.dismiss();
        snackbar = null;
    }

    private boolean verifySnackbar() {
        return snackbar == null;
    }

    public void dontShowSnackbarConnection() {
        showSnackbar = false;
    }

    //==============================================================================================
    //
    // @Override Life Cycle Activity
    //
    //==============================================================================================

    @Override
    protected void onPause() {
        super.onPause();
        if (connectionReceiver != null) {
            unregisterReceiver(connectionReceiver);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        connectionReceiverRegister();
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
