package myapplication.mediaplayertest.app;

import android.app.Application;
import org.xutils.BuildConfig;
import org.xutils.x;




/**
 * Created by zhouzhou on 2017/5/23.
 */

public class MyAppLiction extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
