package com.wanjian.sak.demo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.wanjian.sak.SAK;
import com.wanjian.sak.config.Config;
import com.wanjian.sak.layer.impl.ActivityNameLayerView;
import com.wanjian.sak.layer.impl.FragmentNameLayer;

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

    @Override
    public void onCreate() {
        super.onCreate();
        viewActivityAndFragments(this);
    }

    public static void viewActivityAndFragments(Application application){
        @SuppressLint("UseCompatLoadingForDrawables")
        Config config=new Config.Build(application.getApplicationContext())
//                    .addLayer(TestLayer.class)
//                .addLayer(BorderLayer.class, getIcon(R.drawable.sak_border_icon), getString(R.string.sak_border))
//                .addLayer(GridLayer.class, getIcon(R.drawable.sak_grid_icon), getString(R.string.sak_grid))
//                .addLayer(PaddingLayer.class, getIcon(R.drawable.sak_padding_icon), getString(R.string.sak_padding))
//                .addLayer(MarginLayer.class, getIcon(R.drawable.sak_margin_icon), getString(R.string.sak_margin))
//                .addLayer(WidthHeightLayer.class, getIcon(R.drawable.sak_width_height_icon), getString(R.string.sak_width_height))
//                .addLayer(TextColorLayer.class, getIcon(R.drawable.sak_text_color_icon), getString(R.string.sak_txt_color))
//                .addLayer(TextSizeLayer.class, getIcon(R.drawable.sak_text_size_icon), getString(R.string.sak_txt_size))
                .addLayer(ActivityNameLayerView.class, application.getResources().getDrawable(R.drawable.sak_page_name_icon), application.getString(R.string.sak_activity_name))
                .addLayer(FragmentNameLayer.class, application.getResources().getDrawable(R.drawable.sak_page_name_icon), application.getString(R.string.sak_fragment_name))
//                .addLayer(HorizontalMeasureView.class, getIcon(R.drawable.sak_hori_measure_icon), getString(R.string.sak_horizontal_measure))
//                .addLayer(VerticalMeasureView.class, getIcon(R.drawable.sak_ver_measure_icon), getString(R.string.sak_vertical_measure))
//                .addLayer(TakeColorLayer.class, getIcon(R.drawable.sak_color_picker_icon), getString(R.string.sak_take_color))
//                .addLayer(ViewClassLayer.class, getIcon(R.drawable.sak_controller_type_icon), getString(R.string.sak_view_name))
//                .addLayer(TreeView.class, getIcon(R.drawable.sak_layout_tree_icon), getString(R.string.sak_layout_tree))
//                .addLayer(RelativeLayerView.class, getIcon(R.drawable.sak_relative_distance_icon), getString(R.string.sak_relative_distance))
//                .addLayer(TranslationLayerView.class, getIcon(R.drawable.sak_drag_icon), getString(R.string.sak_translation_view))
                .build();
        try{
            SAK.init(application, config);
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }

    }

}
