package qunaticheart.com.marvelmagazine.Model;

import android.app.Activity;

import java.util.List;

import qunaticheart.com.marvelmagazine.Conexao.Dao.MagazineSQLite;
import qunaticheart.com.marvelmagazine.Conexao.Model.MagazineData;
import qunaticheart.com.marvelmagazine.Controller.MainController;

public final class MainModel {

    private Activity activity;
    private MainController controller;
    private MagazineSQLite sqLite;

    public MainModel(Activity mActivity) {
        activity = mActivity;
//        controller = new MainController(activity);
        sqLite = new MagazineSQLite(activity);
    }

    public void insertMagazine(MagazineData magazine) {
        sqLite.insertMagazine(magazine);
    }

    public void deleteTableMagazine() {
        sqLite.deleteTableMagazine();
    }

    public List<MagazineData> getOfflineList() {
        return sqLite.getOfflineList();
    }

    public void setLike(MagazineData magazine) {
        sqLite.updateLikeStatus(magazine);
    }

    public List<MagazineData> getListLike() {
        return sqLite.getListLike();
    }
}
