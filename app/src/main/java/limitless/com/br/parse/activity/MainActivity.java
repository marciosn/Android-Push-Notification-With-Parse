package limitless.com.br.parse.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import limitless.com.br.parse.R;
import limitless.com.br.parse.adapter.Adapter;
import limitless.com.br.parse.helper.ParseUtils;
import limitless.com.br.parse.helper.PrefManager;
import limitless.com.br.parse.model.Message;
import limitless.com.br.parse.model.NotificationObject;
import limitless.com.br.parse.sqlite.ControllerBD;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private ListView listView;
    private List<Message> listMessages = new ArrayList<>();
    private MessageAdapter adapter;
    private PrefManager pref;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterNotification;
    private RecyclerView.LayoutManager layoutManager;

    private List<NotificationObject> notificationObjects = new ArrayList<NotificationObject>();

    private ControllerBD controllerBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Using Parse.com");

        controllerBD = new ControllerBD(this);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        notificationObjects = getNotifications();

        adapterNotification = new Adapter(notificationObjects);
        recyclerView.setAdapter(adapterNotification);


        /*listView = (ListView) findViewById(R.id.list_view);
        adapter = new MessageAdapter(this);
        listView.setAdapter(adapter);*/

        pref = new PrefManager(getApplicationContext());
        Intent intent = getIntent();

        String email = intent.getStringExtra("email");

        if (email != null) {
            ParseUtils.subscribeWithEmail(pref.getEmail());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String message = intent.getStringExtra("message");
        Log.e(TAG, "MainActivity: " + message);
        Message m = new Message(message, System.currentTimeMillis());
        CharSequence ago = DateUtils.getRelativeTimeSpanString(m.getTimestamp(), System.currentTimeMillis(), 0L, DateUtils.FORMAT_ABBREV_ALL);
        //notificationObjects.add(0, new NotificationObject(null, m.getMessage(), String.valueOf(System.currentTimeMillis())));
        notificationObjects.add(0, new NotificationObject(null, m.getMessage(), String.valueOf(ago)));
        adapterNotification.notifyDataSetChanged();
    }

    private class MessageAdapter extends BaseAdapter {

        LayoutInflater inflater;

        public MessageAdapter(Activity activity) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return listMessages.size();
        }

        @Override
        public Object getItem(int position) {
            return listMessages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.list_row, null);
            }

            TextView txtMessage = (TextView) view.findViewById(R.id.message);
            TextView txtTimestamp = (TextView) view.findViewById(R.id.timestamp);

            Message message = listMessages.get(position);
            txtMessage.setText(message.getMessage());

            CharSequence ago = DateUtils.getRelativeTimeSpanString(message.getTimestamp(), System.currentTimeMillis(),
                    0L, DateUtils.FORMAT_ABBREV_ALL);

            txtTimestamp.setText(String.valueOf(ago));

            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            pref.logout();
            Intent intent = new Intent(MainActivity.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<NotificationObject> getNotifications() {
        List<NotificationObject> result = new ArrayList<>();

        Cursor cursor = controllerBD.list();
        if(cursor != null) {

            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                NotificationObject notificationObject = controllerBD.cursorToOject(cursor);
                result.add(notificationObject);
                cursor.moveToNext();
            }
            cursor.close();
            Collections.reverse(result);
        }

        return  result;
    }
}
