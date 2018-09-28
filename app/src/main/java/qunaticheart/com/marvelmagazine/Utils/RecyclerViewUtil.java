package qunaticheart.com.marvelmagazine.Utils;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

public class RecyclerViewUtil {

    public static LinearLayoutManager Funcao_LinearLayoutManager(Activity activity, Boolean horizontal) {

        LinearLayoutManager linearLayoutManager = null;

        if (horizontal) {
            linearLayoutManager = new LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false);
        } else {
            linearLayoutManager = new LinearLayoutManager(activity, LinearLayout.VERTICAL, false);
        }

        return linearLayoutManager;
    }

    public static GridLayoutManager Funcao_GridLayoutManager(Activity activity) {

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

    private static int getScreenWidthDp(Activity activity) {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        return (int) (displayMetrics.widthPixels / displayMetrics.density);
    }

}
