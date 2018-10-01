package qunaticheart.com.marvelmagazine.Conexao.Dao;

import android.content.Context;

public class CreateDatabase extends Dao {

    /**
     * Constants Magazine SQLite
     */

    public final String TABLE_MAGAZINE = "magazine_data"; // Table Name

    public String Constant_Magazine_ID = "_id";
    public String Constant_Magazine_ID_Magazine = "id_magazine";
    public String Constant_Magazine_Title = "title";
    public String Constant_Magazine_Description = "description";
    public String Constant_Magazine_Number = "number";
    public String Constant_Magazine_Price = "price";
    public String Constant_Magazine_Like = "like";
    public String Constant_Magazine_CoverLink = "coverlink";
    public String Constant_Magazine_CoverExtencion = "coverextencion";
    public String Constant_Magazine_Date = "date";
    public String Constant_Magazine_LinkWeb = "web";
    public String Constant_Magazine_NumberPages = "pages";
    public String Constant_Magazine_Type = "type";


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    CreateDatabase(Context context) {
        super(context);
    }

    public void createDataBase() {

        openDataBase();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append("CREATE TABLE IF NOT EXISTS " + TABLE_MAGAZINE + " (\n" +
                    "  [" + Constant_Magazine_ID + "] INTEGER, \n" +
                    "  [" + Constant_Magazine_ID_Magazine + "] INTEGER, \n" +
                    "  [" + Constant_Magazine_Title + "] INT , \n" +
                    "  [" + Constant_Magazine_Description + "] INT , \n" +
                    "  [" + Constant_Magazine_Number + "] INT , \n" +
                    "  [" + Constant_Magazine_Price + "] INT , \n" +
                    "  [" + Constant_Magazine_Like + "] INT , \n" +
                    "  [" + Constant_Magazine_CoverLink + "] INT , \n" +
                    "  [" + Constant_Magazine_CoverExtencion + "] INT , \n" +
                    "  [" + Constant_Magazine_Date + "] INT , \n" +
                    "  [" + Constant_Magazine_LinkWeb + "] INT , \n" +
                    "  [" + Constant_Magazine_NumberPages + "] INT , \n" +
                    "  [" + Constant_Magazine_Type + "] INT , \n" +
                    "  CONSTRAINT [] PRIMARY KEY([" + Constant_Magazine_ID + "]));");

            //   sb.append("CREATE TABLE teste ( 'Field1' INTEGER );");

            String[] comandos = sb.toString().split(";");

            for (int i = 0; i < comandos.length; i++) {
                db.execSQL(comandos[i].toLowerCase());
            }
        } catch (Exception ignored) {
        }
        closeDataBase();
    }

    public void DeleteDatabase() {

        openDataBase();
        try {
            StringBuilder sb = new StringBuilder();
            //
            sb.append("DROP TABLE IF EXISTS " + TABLE_MAGAZINE + ";");

            //   sb.append("CREATE TABLE teste ( 'Field1' INTEGER );");

            String[] comandos = sb.toString().split(";");

            for (int i = 0; i < comandos.length; i++) {
                db.execSQL(comandos[i].toLowerCase());
            }
        } catch (Exception ignored) {
        }
        closeDataBase();
    }

}
