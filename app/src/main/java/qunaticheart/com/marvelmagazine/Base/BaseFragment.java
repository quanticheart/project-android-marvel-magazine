package qunaticheart.com.marvelmagazine.Base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import qunaticheart.com.marvelmagazine.Helpers.SplashHelper;

@SuppressLint("Registered")
public class BaseFragment extends Fragment {

    //init
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        initActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    //add initial Functions Here
    private void initActivity() {

    }

}
