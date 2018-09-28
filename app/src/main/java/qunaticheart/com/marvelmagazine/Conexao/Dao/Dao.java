package qunaticheart.com.marvelmagazine.Conexao.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by nalmir on 24/11/2017.
 */

class Dao {

    private Context context;
    SQLiteDatabase db;

    private static final String DB_NAME = "jobs_db";
    private static final int DB_VERSION = 1;

    Dao(Context context) {
        this.context = context;
    }

    void openDataBase(){
        SQLiteHelper helper = new SQLiteHelper(
                context,
                DB_NAME,
                null,
                DB_VERSION
        );

        this.db = helper.getWritableDatabase();

    }

    void closeDataBase(){
        if (db !=  null){
            db.close();
        }
    }
}
