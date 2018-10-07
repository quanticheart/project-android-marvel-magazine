package qunaticheart.com.marvelmagazine.Conexao.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import qunaticheart.com.marvelmagazine.Conexao.Model.MagazineData;

public class MagazineSQLite extends Dao {

    private CreateDatabase createDataBase;

    public MagazineSQLite(Context context) {
        super(context);
        createDataBase = new CreateDatabase(context);
    }

    public void insertMagazine(MagazineData magazine) {

        createDataBase.createDataBase();

        openDataBase();
        //
        db.beginTransaction();
        //
        try {

            ContentValues cv = new ContentValues();

            cv.clear();
            cv.put(createDataBase.Constant_Magazine_ID_Magazine, magazine.getId());
            cv.put(createDataBase.Constant_Magazine_Title, magazine.getTitle());
            cv.put(createDataBase.Constant_Magazine_Description, magazine.getDescription());
            cv.put(createDataBase.Constant_Magazine_Number, magazine.getIssueNumber());
            cv.put(createDataBase.Constant_Magazine_Price, magazine.getPrices().get(0).getPrice());
            cv.put(createDataBase.Constant_Magazine_Like, "0");
            cv.put(createDataBase.Constant_Magazine_CoverLink, magazine.getThumbnail().getPath());
            cv.put(createDataBase.Constant_Magazine_CoverExtencion, magazine.getThumbnail().getExtension());
            cv.put(createDataBase.Constant_Magazine_Date, magazine.getDates().get(0).getDate());
            cv.put(createDataBase.Constant_Magazine_LinkWeb, magazine.getUrls().get(0).getUrl());
            cv.put(createDataBase.Constant_Magazine_NumberPages, magazine.getPageCount());
            cv.put(createDataBase.Constant_Magazine_Type, magazine.getFormat());

            //
            db.insert(createDataBase.TABLE_MAGAZINE, null, cv);

            db.setTransactionSuccessful();
        } catch (Exception ignored) {
        } finally {
            db.endTransaction();
        }
        //
        closeDataBase();
    }

    public List<MagazineData> getOfflineList() {
        List<MagazineData> dados = new ArrayList<>();
        //
        openDataBase();
        //
        Cursor cursor = null;
        //
        try {
            String comando = " select * from " + createDataBase.TABLE_MAGAZINE;

            cursor = db.rawQuery(comando.toLowerCase(), null);

            while (cursor.moveToNext()) {

                MagazineData magazine = new MagazineData();
                List<MagazineData.prices> pricesList = new ArrayList<>();
                List<MagazineData.dates> datesList = new ArrayList<>();
                List<MagazineData.urls> urlsList = new ArrayList<>();
                //
                MagazineData.thumbnail thumbnail = new MagazineData.thumbnail();
                MagazineData.prices prices = new MagazineData.prices();
                MagazineData.dates dates = new MagazineData.dates();
                MagazineData.urls urls = new MagazineData.urls();

//                magazine.setId(cursor.getInt(cursor.getColumnIndex(createDataBase.Constant_Magazine_ID)));
                magazine.setId(cursor.getInt(cursor.getColumnIndex(createDataBase.Constant_Magazine_ID_Magazine)));
                magazine.setTitle(cursor.getString(cursor.getColumnIndex(createDataBase.Constant_Magazine_Title)));
                magazine.setDescription(cursor.getString(cursor.getColumnIndex(createDataBase.Constant_Magazine_Description)));
                magazine.setIssueNumber(cursor.getInt(cursor.getColumnIndex(createDataBase.Constant_Magazine_Number)));
                magazine.setLike(cursor.getString(cursor.getColumnIndex(createDataBase.Constant_Magazine_Like)));
                magazine.setPageCount(cursor.getInt(cursor.getColumnIndex(createDataBase.Constant_Magazine_NumberPages)));
                magazine.setFormat(cursor.getString(cursor.getColumnIndex(createDataBase.Constant_Magazine_Type)));
                //
                thumbnail.setPath(cursor.getString(cursor.getColumnIndex(createDataBase.Constant_Magazine_CoverLink)));
                thumbnail.setExtension(cursor.getString(cursor.getColumnIndex(createDataBase.Constant_Magazine_CoverExtencion)));
                //
                prices.setPrice(cursor.getFloat(cursor.getColumnIndex(createDataBase.Constant_Magazine_Price)));
                //
                urls.setUrl(cursor.getString(cursor.getColumnIndex(createDataBase.Constant_Magazine_LinkWeb)));
                //
                dates.setDate(cursor.getString(cursor.getColumnIndex(createDataBase.Constant_Magazine_Date)));
                //
                magazine.setThumbnail(thumbnail);
                //
                pricesList.add(prices);
                magazine.setPrices(pricesList);
                //
                urlsList.add(urls);
                magazine.setUrls(urlsList);
                //
                datesList.add(dates);
                magazine.setDates(datesList);
                //
                dados.add(magazine);
            }

            cursor.close();
            cursor = null;

        } catch (Exception ignored) {
        }
        //
        closeDataBase();
        //
        return dados;
    }

    public List<MagazineData> getListLike() {
        List<MagazineData> dados = new ArrayList<>();

        //
        openDataBase();
        //
        Cursor cursor = null;
        //
        try {
            String comando = " select * from " + createDataBase.TABLE_MAGAZINE + " where " + createDataBase.Constant_Magazine_Like + " = 1";

            cursor = db.rawQuery(comando.toLowerCase(), null);

            while (cursor.moveToNext()) {
                MagazineData magazine = new MagazineData();
                List<MagazineData.prices> pricesList = new ArrayList<>();
                List<MagazineData.dates> datesList = new ArrayList<>();
                List<MagazineData.urls> urlsList = new ArrayList<>();
                //
                MagazineData.thumbnail thumbnail = new MagazineData.thumbnail();
                MagazineData.prices prices = new MagazineData.prices();
                MagazineData.dates dates = new MagazineData.dates();
                MagazineData.urls urls = new MagazineData.urls();

                // magazine.setId(cursor.getInt(cursor.getColumnIndex(createDataBase.Constant_Magazine_ID)));
                magazine.setId(cursor.getInt(cursor.getColumnIndex(createDataBase.Constant_Magazine_ID_Magazine)));
                magazine.setTitle(cursor.getString(cursor.getColumnIndex(createDataBase.Constant_Magazine_Title)));
                magazine.setDescription(cursor.getString(cursor.getColumnIndex(createDataBase.Constant_Magazine_Description)));
                magazine.setIssueNumber(cursor.getInt(cursor.getColumnIndex(createDataBase.Constant_Magazine_Number)));
                magazine.setLike(cursor.getString(cursor.getColumnIndex(createDataBase.Constant_Magazine_Like)));
                magazine.setPageCount(cursor.getInt(cursor.getColumnIndex(createDataBase.Constant_Magazine_NumberPages)));
                magazine.setFormat(cursor.getString(cursor.getColumnIndex(createDataBase.Constant_Magazine_Type)));
                //
                thumbnail.setPath(cursor.getString(cursor.getColumnIndex(createDataBase.Constant_Magazine_CoverLink)));
                thumbnail.setExtension(cursor.getString(cursor.getColumnIndex(createDataBase.Constant_Magazine_CoverExtencion)));
                //
                prices.setPrice(cursor.getFloat(cursor.getColumnIndex(createDataBase.Constant_Magazine_Price)));
                //
                urls.setUrl(cursor.getString(cursor.getColumnIndex(createDataBase.Constant_Magazine_LinkWeb)));
                //
                dates.setDate(cursor.getString(cursor.getColumnIndex(createDataBase.Constant_Magazine_Date)));
                //
                magazine.setThumbnail(thumbnail);
                //
                pricesList.add(prices);
                magazine.setPrices(pricesList);
                //
                urlsList.add(urls);
                magazine.setUrls(urlsList);
                //
                datesList.add(dates);
                magazine.setDates(datesList);
                //
                dados.add(magazine);
            }

            cursor.close();
            cursor = null;

        } catch (Exception ignored) {
        }
        //
        closeDataBase();
        //
        return dados;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void deleteTableMagazine() {

        openDataBase();
        try {
            StringBuilder sb = new StringBuilder();
            //
            sb.append("DROP TABLE IF EXISTS " + createDataBase.TABLE_MAGAZINE + ";");

            //   sb.append("CREATE TABLE teste ( 'Field1' INTEGER );");

            String[] comandos = sb.toString().split(";");

            for (int i = 0; i < comandos.length; i++) {
                db.execSQL(comandos[i].toLowerCase());
            }
        } catch (Exception ignored) {
        }
        closeDataBase();
    }


//    private void Funcao_InsereSQlite(String texto) {
//        openDataBase();
//        //
//
//        db.beginTransaction();
//        //
//        try {
//
//            ContentValues cv = new ContentValues();
//
//            cv.clear();
//            cv.put(texto, texto);
//            //
//            db.insert("DB_" + texto, null, cv);
//
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//        } finally {
//            db.endTransaction();
//        }
//        //
//        closeDataBase();
//    }

    public void updateLikeStatus(MagazineData magazine) {
        openDataBase();

        ContentValues cv = new ContentValues();
        //
        String filtro = createDataBase.Constant_Magazine_ID_Magazine + " = ? ";
        String[] argumentos = {String.valueOf(magazine.getId())};
        //
        cv.put(createDataBase.Constant_Magazine_Like, magazine.getLike());

        db.update(createDataBase.TABLE_MAGAZINE, cv, filtro, argumentos);

        closeDataBase();
    }

}
