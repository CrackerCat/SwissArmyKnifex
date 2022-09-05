package com.wanjian.sak.support;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanjian.sak.R;
import com.wanjian.sak.converter.ISizeConverter;
import com.wanjian.sak.converter.Px2SpSizeConverter;

import java.lang.ref.WeakReference;

public class ViewEditPanel extends LinearLayout {
    private TextView title;
    private EditText width;
    private EditText height;
    private EditText weight;
    private EditText weightSum;
    private EditText gravity;
    private EditText ml;
    private EditText mr;
    private EditText mt;
    private EditText mb;
    private EditText pl;
    private EditText pr;
    private EditText pt;
    private EditText pb;
    private EditText size;
    private EditText text;
    private EditText color;
    private EditText backgroundColor;
    private ViewGroup textRow;
    private ViewGroup sizeRow;
    private ViewGroup colorRow;
    private ViewGroup weightRow;
    private ViewGroup weightSumRow;
    private WeakReference<View> targetViewRef;
    private ISizeConverter sizeConverter;
    private View textTable;
    //
//    private int parseInt(String txt, int defaultValue) {
//        try {
//            return Integer.parseInt(txt);
//        } catch (Exception e) {
//            return defaultValue;
//        }
//    }
    private OnClickListener listener;

    public ViewEditPanel(Context context) {
        super(context);
        setGravity(Gravity.CENTER);
        init(LayoutInflater.from(context).inflate(R.layout.sak_edit_panel_layout, this, true));
    }

    private void init(View view) {
        textTable = findViewById(R.id.textTable);
        title = (TextView) findViewById(R.id.title);
        width = (EditText) findViewById(R.id.width);
        height = (EditText) findViewById(R.id.height);
        gravity = (EditText) findViewById(R.id.gravity);
        weight = (EditText) findViewById(R.id.weight);
        weightSum = (EditText) findViewById(R.id.weightSum);
        ml = (EditText) findViewById(R.id.ml);
        mr = (EditText) findViewById(R.id.mr);
        mt = (EditText) findViewById(R.id.mt);
        mb = (EditText) findViewById(R.id.mb);
        pl = (EditText) findViewById(R.id.pl);
        pr = (EditText) findViewById(R.id.pr);
        pt = (EditText) findViewById(R.id.pt);
        pb = (EditText) findViewById(R.id.pb);
        size = (EditText) findViewById(R.id.size);
        color = (EditText) findViewById(R.id.color);
        text = (EditText) findViewById(R.id.text);
        backgroundColor = (EditText) findViewById(R.id.backgroundColor);
        sizeRow = (ViewGroup) findViewById(R.id.sizeRow);
        colorRow = (ViewGroup) findViewById(R.id.colorRow);
        weightRow = (ViewGroup) findViewById(R.id.weightRow);
        weightSumRow = (ViewGroup) findViewById(R.id.weightSumRow);
        textRow = (ViewGroup) findViewById(R.id.textRow);

        findViewById(R.id.confirm).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                View targetView = targetViewRef.get();
                if (targetView == null) {
                    return;
                }
                Context context = getContext();
                ViewGroup.LayoutParams params = targetView.getLayoutParams();
                setWidth(context, params);

                setHeight(context, params);


                setWeightIfNeeded(params, targetView);

                if (params instanceof MarginLayoutParams) {
                    MarginLayoutParams marginLayoutParams = (MarginLayoutParams) params;
                    setMargin(context, marginLayoutParams);
                }

                setPadding(targetView, context);


                if (targetView instanceof TextView) {
                    TextView textView = (TextView) targetView;
                    setTextInfo(context, textView);
                }

                try {
                    int color = Color.parseColor(backgroundColor.getText().toString());
                    targetView.setBackgroundColor(color);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                targetView.requestLayout();
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });

    }

    private void setWeightIfNeeded(ViewGroup.LayoutParams params, View targetView) {
        if (targetView instanceof LinearLayout) {
            try {
                ((LinearLayout) targetView).setWeightSum(Float.parseFloat(weightSum.getText().toString()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (params instanceof LayoutParams) {
            try {
                ((LayoutParams) params).weight = Float.parseFloat(weight.getText().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void setHeight(Context context, ViewGroup.LayoutParams params) {
        String heightInput = height.getText().toString();
        if (heightInput.equalsIgnoreCase("M")) {
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else if (heightInput.equalsIgnoreCase("W")) {
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            try {
                params.height = sizeConverter.recovery(context, Float.parseFloat(heightInput));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void setWidth(Context context, ViewGroup.LayoutParams params) {
        String widthInput = width.getText().toString();
        if (widthInput.equalsIgnoreCase("M")) {
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else if (widthInput.equalsIgnoreCase("W")) {
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            try {
                params.width = sizeConverter.recovery(context, Float.parseFloat(widthInput));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    private void setMargin(Context context, MarginLayoutParams marginLayoutParams) {
        try {
            marginLayoutParams.leftMargin = sizeConverter.recovery(context, Float.parseFloat(ml.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            marginLayoutParams.rightMargin = sizeConverter.recovery(context, Float.parseFloat(mr.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            marginLayoutParams.topMargin = sizeConverter.recovery(context, Float.parseFloat(mt.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            marginLayoutParams.bottomMargin = sizeConverter.recovery(context, Float.parseFloat(mb.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void setTextInfo(Context context, TextView textView) {
        try {
            try {
                int px = sizeConverter.recovery(context, Float.parseFloat(size.getText().toString()));
                textView.setTextSize(new Px2SpSizeConverter().convert(context, px).getLength());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            textView.setTextColor(Color.parseColor(color.getText().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        textView.setText(text.getText());
    }

    private void setPadding(View targetView, Context context) {
        int paddingLeft = targetView.getPaddingLeft();
        try {
            paddingLeft = sizeConverter.recovery(context, Float.parseFloat(pl.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        int paddingRight = targetView.getPaddingRight();
        try {
            paddingRight = sizeConverter.recovery(context, Float.parseFloat(pr.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        int paddingTop = targetView.getPaddingTop();
        try {
            paddingTop = sizeConverter.recovery(context, Float.parseFloat(pt.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        int paddingBottom = targetView.getPaddingBottom();
        try {
            paddingBottom = sizeConverter.recovery(context, Float.parseFloat(pb.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        targetView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public void attachTargetView(View view) {
        Context context = getContext();
        targetViewRef = new WeakReference<>(view);

        title.setText(view.getClass().getSimpleName());

        ViewGroup.LayoutParams params = view.getLayoutParams();

        getWidthHeight(context, params);

        if (view instanceof LinearLayout) {
            float ws = ((LinearLayout) view).getWeightSum();
            weightSumRow.setVisibility(VISIBLE);
            weightSum.setText(ws + "");
        }

        if (params instanceof LinearLayout.LayoutParams) {
            weightRow.setVisibility(VISIBLE);
            weight.setText(((LayoutParams) params).weight + "");
        }

//        try {
//            Field field = params.getClass().getDeclaredField("gravity");
//            field.setAccessible(true);
//            int gravity = (int) field.get(params);//-1
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        if (params instanceof MarginLayoutParams) {
            getMargin(((MarginLayoutParams) params), context);
        }

        setPadding(context, view);

        if (view instanceof TextView) {
            textTable.setVisibility(VISIBLE);
            TextView textView = (TextView) view;
            sizeRow.setVisibility(VISIBLE);
            colorRow.setVisibility(VISIBLE);
            textRow.setVisibility(VISIBLE);
            size.setText(sizeConverter.convert(context, textView.getTextSize()).getLength() + "");
            color.setText(String.format("#%08x", textView.getCurrentTextColor()));
            text.setText(textView.getText());
        } else {
            textTable.setVisibility(GONE);
        }

        Drawable drawable = view.getBackground();
        if (drawable != null) {
            drawable = drawable.getCurrent();
        }
        if (drawable instanceof ColorDrawable) {
            int color = ((ColorDrawable) drawable).getColor();
            String txt = String.format("#%08x", color);
            backgroundColor.setText(txt);
        }
    }

    private void setPadding(Context context, View view) {
        pl.setText(sizeConverter.convert(context, view.getPaddingLeft()).getLength() + "");
        pr.setText(sizeConverter.convert(context, view.getPaddingRight()).getLength() + "");
        pt.setText(sizeConverter.convert(context, view.getPaddingTop()).getLength() + "");
        pb.setText(sizeConverter.convert(context, view.getPaddingBottom()).getLength() + "");

    }

//    private float parseFloat(String txt, float defaultValue) {
//        try {
//            return Float.parseFloat(txt);
//        } catch (Exception e) {
//            return defaultValue;
//        }
//    }

    private void getMargin(MarginLayoutParams params, Context context) {
        ml.setText(sizeConverter.convert(context, params.leftMargin).getLength() + "");
        mr.setText(sizeConverter.convert(context, params.rightMargin).getLength() + "");
        mt.setText(sizeConverter.convert(context, params.topMargin).getLength() + "");
        mb.setText(sizeConverter.convert(context, params.bottomMargin).getLength() + "");
    }

    private void getWidthHeight(Context context, ViewGroup.LayoutParams params) {
        if (params.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            width.setText("W");
        } else if (params.width == ViewGroup.LayoutParams.MATCH_PARENT) {
            width.setText("M");
        } else {
            width.setText(sizeConverter.convert(context, params.width).getLength() + "");
        }

        if (params.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            height.setText("W");
        } else if (params.height == ViewGroup.LayoutParams.MATCH_PARENT) {
            height.setText("M");
        } else {
            height.setText(sizeConverter.convert(context, params.height).getLength() + "");
        }
    }

    public void setOnConfirmClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setSizeConverter(ISizeConverter sizeConverter) {
        this.sizeConverter = sizeConverter;
    }
}
