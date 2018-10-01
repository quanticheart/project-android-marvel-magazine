package qunaticheart.com.marvelmagazine.Base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import java.util.Objects;

import qunaticheart.com.marvelmagazine.BroadCast.MyReceiver;
import qunaticheart.com.marvelmagazine.BroadCast.SystemUtil;
import qunaticheart.com.marvelmagazine.Helpers.SplashHelper;
import qunaticheart.com.marvelmagazine.Utils.LoggerUtils;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements SystemUtil.ShowMsg {

    //init
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;
    private static Snackbar snackbar = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = BaseActivity.this;
        initActivity();
        initBroadcast();
    }

    //add initial Functions Here
    private void initActivity() {
        new SplashHelper(activity);
    }

    private void initBroadcast() {

        new SystemUtil(activity);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        MyReceiver myReceiver = new MyReceiver();
        registerReceiver(myReceiver, filter);

        SystemUtil.setShowMsg(this);
    }

    @Override
    public void showMsg() {
        if (snackbar == null) {
            snackbar = Snackbar.make(Objects.requireNonNull(activity.getCurrentFocus()), "No Conection", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        }
    }

    @Override
    public void hideMsg() {
        if (snackbar != null) {
            snackbar.dismiss();
            snackbar = null;
        }
    }

//    private boolean verifieSnackbar(){
//        return snackbar == null? true : false;
//    }
}
