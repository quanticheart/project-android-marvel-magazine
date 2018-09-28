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

    /**
     * @param url       url for connection
     * @param extencion extencion image
     * @return image url
     */
    public static String getFullImageCover(String url, String extencion) {
        return url + "." + extencion;
    }

    /**
     * @param url       url for connection
     * @param extencion extencion image
     * @return image url
     */
    public static String getFantasticImageCover(String url, String extencion) {
        return url + "/" + getPortraitFantastic() + "." + extencion;
    }

    /**
     * @param url       url for connection
     * @param extencion extencion image
     * @return image url Square
     */
    public static String getFantasticImageSquare(String url, String extencion) {
        return url + "/" + getSquareFantastic() + "." + extencion;
    }

    public static String getNumberFormated(Integer number) {
        return "#" + number;
    }

//========================================================================
//    Utils

    //cover
    private static String getPortraitFantastic() {
        return "portrait_fantastic";
    }

    //Square
    private static String getSquareFantastic() {
        return "standard_fantastic";
    }

//    portrait_small	50x75px
//    portrait_medium	100x150px
//    portrait_xlarge	150x225px
//    portrait_fantastic	168x252px
//    portrait_uncanny	300x450px
//    portrait_incredible	216x324px
//    Standard (square) aspect ratio

//    Square
//    standard_small	65x45px
//    standard_medium	100x100px
//    standard_large	140x140px
//    standard_xlarge	200x200px
//    standard_fantastic	250x250px
//    standard_amazing	180x180px
}
