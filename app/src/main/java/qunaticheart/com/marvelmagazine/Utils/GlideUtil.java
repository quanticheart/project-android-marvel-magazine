package qunaticheart.com.marvelmagazine.Utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

import qunaticheart.com.marvelmagazine.R;

public class GlideUtil {

    public static void initGlide(Activity activity, String url, ImageView imageView) {
        Glide.with(activity).applyDefaultRequestOptions(getRequestOptions()).load(url).into(imageView);
    }

    public static void initGlide(Activity activity, Uri url, ImageView imageView) {
        Glide.with(activity).applyDefaultRequestOptions(getRequestOptions()).load(url).into(imageView);
    }

    public static void initGlide(Activity activity, File url, ImageView imageView) {
        Glide.with(activity).applyDefaultRequestOptions(getRequestOptions()).load(url).into(imageView);
    }

    public static void initGlide(Activity activity, Byte[] url, ImageView imageView) {
        Glide.with(activity).applyDefaultRequestOptions(getRequestOptions()).load(url).into(imageView);
    }

    public static void initGlide(Activity activity, Drawable url, ImageView imageView) {
        Glide.with(activity).applyDefaultRequestOptions(getRequestOptions()).load(url).into(imageView);
    }

    public static void initGlideAsGif(Activity activity, Drawable url, ImageView imageView) {
        Glide.with(activity).applyDefaultRequestOptions(getRequestOptions()).asGif().load(url).into(imageView);
    }

    public static void initGlide(Activity activity, Object url, ImageView imageView) {
        Glide.with(activity).applyDefaultRequestOptions(getRequestOptions()).load(url).into(imageView);
    }

    public static void initGlide(Activity activity, Bitmap url, ImageView imageView) {
        Glide.with(activity).applyDefaultRequestOptions(getRequestOptions()).load(url).into(imageView);
    }

    public static void initGlide(Activity activity, Integer url, ImageView imageView) {
        Glide.with(activity).applyDefaultRequestOptions(getRequestOptions()).load(url).into(imageView);
    }

    private static RequestOptions getRequestOptions() {
        return new RequestOptions().centerCrop().placeholder(R.drawable.bg1).error(R.drawable.bg1);
    }
}
