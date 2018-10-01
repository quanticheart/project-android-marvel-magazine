package qunaticheart.com.marvelmagazine.Helpers;

import android.app.Activity;
import com.master.killercode.wizard.GetWizardStatus;
import com.master.killercode.wizard.Splash.SplashActivity;
import com.master.killercode.wizard.Wizard.WizardPageModel;
import java.util.ArrayList;
import qunaticheart.com.marvelmagazine.R;
import qunaticheart.com.marvelmagazine.View.MainMagazineActivity;

/**
 * Created by Jonh on 16/08/2018.
 */

public class SplashHelper {

    private Activity activity;
    private String SPLASH_WIZARD_INIT = "splash_wizard_init";
    private ArrayList<WizardPageModel> wList = new ArrayList<>(); // create array for Page Wizard


    public SplashHelper(Activity activity) {
        this.activity = activity;
        initList();
        initSplash();
    }

    private void initList() {
        WizardPageModel page1 = new WizardPageModel("Hellow!");
        wList.add(page1); // add page in arrayPages for Wizard

        WizardPageModel page2 = new WizardPageModel("Marvel Magazines", "Teste inicial requerido pela empresa Robert Half");
        wList.add(page2);

        WizardPageModel page3 = new WizardPageModel("APP", "App e um gerenciado de revistas da Marvel Comix com o intuito de mostrar ao usuário, os quadrinhos e seus dados.", R.mipmap.ic_launcher);
        wList.add(page3);

    }

    private void initSplash() {

        GetWizardStatus wPrefs = new GetWizardStatus(activity); // Create GetWizardStatus with Context
        if (!wPrefs.getStatusFinishedFrom(SPLASH_WIZARD_INIT)) {

            SplashActivity splash = new SplashActivity(activity, MainMagazineActivity.class); // Create Splash With Wizard, init With Activity and Activity After Wizard Activity
            splash.setSplashFinished(SPLASH_WIZARD_INIT);
            splash.setWizard(wList); // add ArrayPage For WizardActivity
            splash.setWizardThemeGoogle(); // set Theme Google in Wizard
            splash.show(true);// create e init SplashActivity , if add true in param, activity is finished

        }
    }

}
