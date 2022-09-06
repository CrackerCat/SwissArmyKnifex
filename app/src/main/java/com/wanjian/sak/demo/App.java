package com.wanjian.sak.demo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.wanjian.sak.SAK;
import com.wanjian.sak.config.Config;
import com.wanjian.sak.layer.impl.ActivityNameLayerView;
import com.wanjian.sak.layer.impl.FragmentNameLayer;


//import com.squareup.leakcanary.LeakCanary;

//import leakcanary.LeakCanary;


/**
 * Created by wanjian on 2018/2/9.
 */

public class App extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        SAK.preInitOnAttachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        viewActivityAndFragments(this);
    }

    public static void viewActivityAndFragments(Application application){
        SAK.installNoConsole(application,ActivityNameLayerView.class,FragmentNameLayer.class);
    }

}
