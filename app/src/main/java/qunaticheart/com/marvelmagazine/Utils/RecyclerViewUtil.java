package qunaticheart.com.marvelmagazine.Utils;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.LinearLayout;

public class RecyclerViewUtil {

    public static GridLayoutManager gridLayoutManager(Activity activity) {

        GridLayoutManager gridLayoutManager = null;

        if (getScreenWidthDp(activity) >= 1200) {
            gridLayoutManager = new GridLayoutManager(activity, 3);
        } else if (getScreenWidthDp(activity) >= 800) {
            gridLayoutManager = new GridLayoutManager(activity, 2);
        } else {
            gridLayoutManager = new GridLayoutManager(activity, 2);
        }

        return gridLayoutManager;
    }

//    private static int getScreenWidthDp(Activity activity) {
//        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
//        return (int) (displayMetrics.widthPixels / displayMetrics.density);
//    }
    private static int getScreenWidthDp(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        return (int) (display.getWidth() / display.getHeight());
    }

}
