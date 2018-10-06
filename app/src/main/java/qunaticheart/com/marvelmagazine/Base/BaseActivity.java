package qunaticheart.com.marvelmagazine.Base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import java.util.Objects;

import qunaticheart.com.marvelmagazine.BroadCast.MyReceiver;
import qunaticheart.com.marvelmagazine.BroadCast.SystemUtil;
import qunaticheart.com.marvelmagazine.Helpers.SplashHelper;

public abstract class BaseActivity extends AppCompatActivity implements SystemUtil.ConnectionVerify {

    //init
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;
    private static Snackbar snackbar = null;

    //ConnectionVerify Verifie
    public boolean connected = false;
    private MyReceiver connectionReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVars();
        initActions();
        //
        connectionReceiver();

    }

    private void initVars() {
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

    private void connectionReceiver() {

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
    public void ConnectionFail() {
        connected = false;
        if (snackbar == null) {
            snackbar = Snackbar.make(Objects.requireNonNull(activity.getCurrentFocus()), "No Conection", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        }
    }

    @Override
    public void ConnectionOK() {
        connected = true;
        if (snackbar != null) {
            snackbar.dismiss();
            snackbar = null;
        }
    }

    //==============================================================================================
    //
    // Utils
    //
    //==============================================================================================



    //==============================================================================================
    //
    // @Override Life Cycle
    //
    //==============================================================================================

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(connectionReceiver);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        connectionReceiver();
    }

    @Override
    protected void onResume() {
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

//    private boolean verifieSnackbar(){
//        return snackbar == null? true : false;
//    }
}
