package com.wanjian.sak.demo;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import me.weishu.reflection.Reflection;

//import com.squareup.leakcanary.LeakCanary;

//import leakcanary.LeakCanary;


/**
 * Created by wanjian on 2018/2/9.
 */

public class App extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Reflection.unseal(base);
    }
}
