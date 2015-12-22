package limitless.com.br.parse.app;

import android.app.Application;

import limitless.com.br.parse.helper.ParseUtils;

/**
 * Created by Márcio Sn on 22/12/2015.
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ParseUtils.registerParse(this);
    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
}
