package limitless.com.br.parse.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import limitless.com.br.parse.app.AppConfig;
import limitless.com.br.parse.model.NotificationObject;

/**
 * Created by Márcio Sn on 22/11/2015.
 */
public class ControllerBD {

    private SQLiteDatabase db;
    private CreateBD createBD;
    private Context context;
    private AppConfig config;

    public ControllerBD(Context context) {
        this.createBD = new CreateBD(context);
        this.context = context;
    }

    public boolean saveNota(NotificationObject notification) {
        boolean isSalvo = false;
        ContentValues values;
        long resultadoInsercao;

        db = createBD.getWritableDatabase();
        values = new ContentValues();

        values.put(config._MESSAGE, notification.getMessage());
        values.put(config._TIMESTAMP, notification.getTimestamp());

        resultadoInsercao = db.insert(AppConfig.TABLE_NAME, null, values);
        db.close();

        if (resultadoInsercao == -1) {
            isSalvo = false;
        } else {
            isSalvo = true;
        }
        return isSalvo;
    }

    public Cursor list() {
        Cursor cursor;

        String[] values = {config._ID, config._MESSAGE, config._TIMESTAMP};
        db = createBD.getReadableDatabase();
        cursor = db.query(config.TABLE_NAME, values, null, null,null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor findById(Long id) {
        Cursor cursor;

        String[] values = {config._ID, config._MESSAGE, config._TIMESTAMP};
        String WHERE = CreateBD._ID + " = " + id;
        db = createBD.getReadableDatabase();
        cursor = db.query(config.TABLE_NAME, values, WHERE, null,null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void update(NotificationObject notification) {

        ContentValues values;
        String WHERE;
        db = createBD.getWritableDatabase();

        WHERE = "id = " + notification.getId();

        values = new ContentValues();
        values.put(config._MESSAGE, notification.getMessage());
        values.put(config._TIMESTAMP, notification.getTimestamp());

        db.update(config.TABLE_NAME, values, WHERE, null);
        db.close();
    }

    public void remove(Long id) {
        String WHERE = CreateBD._ID + " = " + id;
        db = createBD.getReadableDatabase();
        db.delete(config.TABLE_NAME, WHERE, null);
        db.close();
    }

    public NotificationObject cursorToOject(Cursor cursor){
        NotificationObject notificationObject = new NotificationObject();
        notificationObject.setId(cursor.getLong(cursor.getColumnIndex("id")));
        notificationObject.setMessage(cursor.getString(cursor.getColumnIndex("message")));
        notificationObject.setTimestamp(cursor.getString(cursor.getColumnIndex("timestamp")));
        return notificationObject;
    }
}











