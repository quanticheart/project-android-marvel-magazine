package qunaticheart.com.marvelmagazine.Utils;

import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import qunaticheart.com.marvelmagazine.Conexao.Model.MagazineData;
import qunaticheart.com.marvelmagazine.R;

public class ViewUtil {

    private static void statusLike(ImageView btn_item, int fav) {
        AlphaAnimation alphaAnimationShowIcon = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimationShowIcon.setDuration(500);

        if (fav == 0) {
            btn_item.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            btn_item.startAnimation(alphaAnimationShowIcon);

        } else {
            btn_item.setImageResource(R.drawable.ic_favorite_black_24dp);
            btn_item.startAnimation(alphaAnimationShowIcon);

        }
    }

    public static String timestampToStringFormated(List<MagazineData.dates> timestamp) {
        String date[] = timestamp.get(0).getDate().split("T");
        return date[0];
    }

    public static String moneyFormate(float numero) {
        return NumberFormat.getCurrencyInstance().format(numero);
    }

    public static String pageCountFormate(int numero) {
        return numero+" Pages";
    }

}
