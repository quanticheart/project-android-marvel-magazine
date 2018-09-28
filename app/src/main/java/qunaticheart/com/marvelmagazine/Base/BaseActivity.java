package qunaticheart.com.marvelmagazine.Base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import qunaticheart.com.marvelmagazine.Helpers.SplashHelper;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    //init
    public static Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = BaseActivity.this;
        initActivity();
    }

    //add initial Functions Here
    private void initActivity() {
        new SplashHelper(activity);
    }

}
