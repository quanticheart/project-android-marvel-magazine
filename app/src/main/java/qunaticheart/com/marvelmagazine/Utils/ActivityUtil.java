package qunaticheart.com.marvelmagazine.Utils;

import android.app.Activity;
import android.content.Intent;


/**
 * Created by John on 29/01/2018.
 */

public class ActivityUtil {

    /**
     * @param activity
     * @param intent
     * @param finish
     */
    public static void callActivity(Activity activity, final Intent intent, final Boolean finish) {
        initIntent(activity, verifieStatusIntent(intent), finish);
    }

    /**
     * @param intent
     * @return
     */
    private static Intent verifieStatusIntent(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    /**
     * @param activity
     * @param intent
     * @param finish
     */
    private static void initIntent(Activity activity, Intent intent, boolean finish) {
        activity.startActivity(intent);
        if (finish) {
            activity.finish();
        }
    }



}
