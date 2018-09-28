package qunaticheart.com.marvelmagazine.Conexao.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import qunaticheart.com.marvelmagazine.Conexao.Model.ListMagazine;
import retrofit2.Response;

import static qunaticheart.com.marvelmagazine.Utils.LoggerUtils.LogE;
import static qunaticheart.com.marvelmagazine.Utils.LoggerUtils.LogW;

public class ConnectUtils {

    private static ProgressDialog mProgressDialog;

    public static String getUrlForConection() {
        String ts = Long.toString(System.currentTimeMillis() / 1000);
        String publicK = "42f63a8d6c0a9760154b9c8e1284a9a1";
        String privateK = "11f10099031b272383d15c345a1bb1df798a2c7d";
        String hash = getHashForConection(ts + privateK + publicK);
        StringBuilder b = new StringBuilder();
        b.append("ts" + "=" + ts);
        b.append("&" + "apikey" + "=" + publicK);
        b.append("&" + "hash" + "=" + hash);
        Log.w("URL", b.toString());
        return b.toString();
    }


    public static String getHashForConection(String stringForEncrypt) {
        try {
            MessageDigest md5Encoder = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5Encoder.digest(stringForEncrypt.getBytes());

            StringBuilder md5 = new StringBuilder();
            for (byte md5Byte : md5Bytes) {
                md5.append(Integer.toHexString((md5Byte & 0xFF) | 0x100).substring(1, 3));
            }
            return md5.toString();
        } catch (NoSuchAlgorithmException ignored) {
            return "";
        }
    }

    public static String getTimeStamp() {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = new Date();
        Log.w("", dateFormat.format(date));
        return dateFormat.format(date);
    }

    @SuppressLint("LongLogTag")
    public static void logFunction(Response<ListMagazine> response, String conectDescription) {
        Log.w("Connect With = " + conectDescription, "OK");
        int http_status = response.code();
        if (http_status < 300) {
            LogW("Http Status OK", String.valueOf(http_status));
        } else if (http_status >= 300 || http_status < 399) {
            LogE("Http Status", String.valueOf(http_status));
        } else {
            LogE("Http Status Error", String.valueOf(http_status));
        }
    }

    public static void logFunction(Throwable t, String conectDescription) {
        Log.e("Connect " + conectDescription, "Fail");
        Log.e("Error", t.getMessage());
        Log.e("Message", t.getLocalizedMessage());
    }

    public static void showSimpleLoadingDialog(Activity activity) {
        mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    public static void hideSimpleLoadingDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
    }
}
