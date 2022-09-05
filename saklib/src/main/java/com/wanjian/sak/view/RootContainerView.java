package com.wanjian.sak.view;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class RootContainerView extends FrameLayout {
    private SAKEntranceView entranceView;

    public RootContainerView(@NonNull Context context) {
        this(context, null);
    }

    public RootContainerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        setFitsSystemWindows(false);
    }



}
