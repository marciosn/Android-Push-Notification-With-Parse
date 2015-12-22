package limitless.com.br.parse.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Márcio Sn on 22/11/2015.
 */
public class CreateBD extends SQLiteOpenHelper {

    public static final String TABLE_NAME = " news ";
    public static final String _ID = " id ";
    public static final String _MESSAGE = " message ";
    public static final String _TIMESTAMP = " timestamp ";


    private static final String DATABASE_NAME = "news.db";
    private static final int DATABASE_VERSION = 2;

    private static final String SQL_CREATE_TABLE = " CREATE TABLE "+ TABLE_NAME +
            "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            _MESSAGE + " TEXT NOT NULL, " +
            _TIMESTAMP + " TEXT NOT NULL " +
            ")";


    public CreateBD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public CreateBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(CreateBD.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
