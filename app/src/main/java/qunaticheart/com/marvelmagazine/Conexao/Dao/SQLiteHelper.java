package qunaticheart.com.marvelmagazine.Conexao.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "job";
    private static final String ID = "_id";
    public static final String COLLUM = "title";

    SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            StringBuilder sb = new StringBuilder();

            sb.append("CREATE TABLE IF NOT EXISTS [" + TABLE_NAME + "] (\n" +
                    "  [" + ID + "] TEXT, \n" +
                    "  [" + COLLUM + "] TEXT, \n" +
                    "  CONSTRAINT [] PRIMARY KEY ([" + ID + "]));");

//            sb.append(""); // for more Tables ;)

            String[] comandos = sb.toString().split(";");

            for (String comando : comandos) {
                db.execSQL(comando.toLowerCase());
            }
        } catch (Exception ignored) {
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
