package com.wanjian.sak.utils;

import android.app.Activity;
import android.view.View;
import android.view.ViewRootImpl;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static ViewRootImpl[] diff(ViewRootImpl[] views1, ViewRootImpl[] views2) {
        List<ViewRootImpl> list1 = views1 == null ? new ArrayList<ViewRootImpl>() : Arrays.asList(views1);
        List<ViewRootImpl> list2 = views2 == null ? new ArrayList<ViewRootImpl>() : Arrays.asList(views2);
        List<ViewRootImpl> result = new ArrayList<>(32);

        for (ViewRootImpl view : list1) {
            if (list2.contains(view) == false) {
                result.add(view);
            }
        }
        ViewRootImpl[] array = new ViewRootImpl[result.size()];
        result.toArray(array);
        return array;
    }

    public static Activity findAct(View view) {
        List<WeakReference<Activity>> references = RunningActivityFetcher.fetch();
        if (references == null) {
            return null;
        }
        for (WeakReference<Activity> act : references) {
            Activity activity = act.get();
            if (activity == null) {
                continue;
            }
            if (view == activity.getWindow().getDecorView().getRootView()) {
                return activity;
            }
        }
        return null;
    }

}
