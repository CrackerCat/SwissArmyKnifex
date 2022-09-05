package com.wanjian.sak.converter;

import android.content.Context;

/**
 * Created by wanjian on 2017/3/9.
 */

public class Px2SpSizeConverter extends ISizeConverter {
    @Override
    public String desc() {
        return "Sp";
    }

    @Override
    public Size convert(Context context, float length) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return Size.obtain().setLength((int) (length / fontScale + 0.5f)).setUnit("sp");
    }

    @Override
    public int recovery(Context context, float length) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (length * fontScale + 0.5f);
    }
}
