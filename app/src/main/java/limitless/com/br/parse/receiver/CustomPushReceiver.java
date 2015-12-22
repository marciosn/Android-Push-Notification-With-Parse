package limitless.com.br.parse.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import limitless.com.br.parse.activity.MainActivity;
import limitless.com.br.parse.helper.NotificationUtils;
import limitless.com.br.parse.model.NotificationObject;
import limitless.com.br.parse.sqlite.ControllerBD;

/**
 * Created by Márcio Sn on 22/12/2015.
 */
public class CustomPushReceiver extends ParsePushBroadcastReceiver {

    private final String TAG = CustomPushReceiver.class.getSimpleName();

    private NotificationUtils notificationUtils;

    private Intent parseIntent;

    private ControllerBD controllerBD;

    public CustomPushReceiver() {
        super();
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);

        if (intent == null)
            return;

        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

            //Log.e(TAG, "Push received json: " + json);

            parseIntent = intent;

            parsePushJson(context, json);

        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
    }

    private void parsePushJson(Context context, JSONObject json) {
        controllerBD = new ControllerBD(context);
        try {
            JSONObject data = json.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = json.getBoolean("is_background");

            //Log.e(TAG, " isBackground " + json.getBoolean("is_background"));

            if (!isBackground) {
                Intent resultIntent = new Intent(context, MainActivity.class);
                showNotificationMessage(context, title, message, resultIntent);

                NotificationObject notif = new NotificationObject();
                notif.setMessage(message);
                notif.setTimestamp(getDateCustom() + "\n" + getTimeCustom());

                controllerBD.saveNota(notif);
            }

        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception parsePushJson: " + e.getMessage());
        }
    }


    private void showNotificationMessage(Context context, String title, String message, Intent intent) {

        notificationUtils = new NotificationUtils(context);

        intent.putExtras(parseIntent.getExtras());

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        notificationUtils.showNotificationMessage(title, message, intent);
    }

    private String getDateCustom() {
        //DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getTimeCustom() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
