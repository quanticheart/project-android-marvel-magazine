package qunaticheart.com.marvelmagazine.Controller;

import android.app.Activity;

import java.util.List;

import qunaticheart.com.marvelmagazine.Conexao.Model.MagazineData;
import qunaticheart.com.marvelmagazine.Model.MainModel;

public class MainController {

    MainModel model;
    Activity activity;

    public MainController(Activity mActivity) {
        activity = mActivity;
        model = new MainModel(activity);
    }

    public void addMagazine(MagazineData magazine) {
        model.insertMagazine(magazine);
    }

    public void deleteTableMagazine() {
        model.deleteTableMagazine();
    }

    public List<MagazineData> getListOffline(){
       return model.getOfflineList();
    }

    public void setLike(MagazineData magazine){
        model.setLike(magazine);
    }

    public List<MagazineData> getListLike(){
        return model.getListLike();
    }

}
