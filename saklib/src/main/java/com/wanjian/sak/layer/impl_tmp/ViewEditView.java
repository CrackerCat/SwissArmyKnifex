package com.wanjian.sak.layer.impl_tmp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.view.GestureDetectorCompat;
import android.view.ContextThemeWrapper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.wanjian.sak.R;
import com.wanjian.sak.layer.AbsLayer;
import com.wanjian.sak.support.ViewEditPanel;
import com.wanjian.sak.view.RootContainerView;

public class ViewEditView extends AbsLayer {
    private View mTarget;
    private int[] mLocation = new int[2];
    private View mTouchTarget;

    public ViewEditView(Context context) {
        super(context);
        init();
    }

    @Override
    public Drawable icon() {
        return getResources().getDrawable(R.drawable.sak_edit_icon);
    }

    @Override
    public String description() {
        return getContext().getString(R.string.sak_edit_view);
    }

    private void init() {
        final GestureDetectorCompat detectorCompat = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View target = findPressView((int) e.getRawX(), (int) e.getRawY());
                final ViewEditPanel editPanel = new ViewEditPanel(new ContextThemeWrapper(getContext(), R.style.SAK_Theme));
                editPanel.setSizeConverter(getSizeConverter());
                editPanel.attachTargetView(target);
                WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                showWindow(editPanel, params);
                editPanel.setOnConfirmClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeWindow(editPanel);
                    }
                });
                if (mTouchTarget != null) {
                    e.setAction(MotionEvent.ACTION_CANCEL);
                    View root = getRootView();
                    e.offsetLocation(-mTouchTarget.getX() + root.getPaddingLeft(), -mTouchTarget.getY() + root.getPaddingTop());
                    mTouchTarget.dispatchTouchEvent(e);
                    mTouchTarget = null;
                }
            }
        });
        super.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detectorCompat.onTouchEvent(event);
            }
        });
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        super.dispatchTouchEvent(event);
        ViewGroup decorView = ((ViewGroup) getRootView());
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            mTouchTarget = null;
            int curX = (int) event.getRawX();
            int curY = (int) event.getRawY();

            for (int i = decorView.getChildCount() - 1; i > -1; i--) {
                View child = decorView.getChildAt(i);
                if (child instanceof RootContainerView || child.getVisibility() != VISIBLE) {
                    continue;
                }
                if (inRange(child, curX, curY) == false) {
                    continue;
                }
                event.offsetLocation(-child.getX() + decorView.getPaddingLeft(), -child.getY() + decorView.getPaddingTop());
                if (child.dispatchTouchEvent(event)) {
                    mTouchTarget = child;
                    return true;
                }
            }
        }
        if (mTouchTarget != null) {
            event.offsetLocation(-mTouchTarget.getX() + decorView.getPaddingLeft(), -mTouchTarget.getY() + decorView.getPaddingTop());
            mTouchTarget.dispatchTouchEvent(event);
        }
        return true;
    }

    protected View findPressView(int x, int y) {
        mTarget = getRootView();
        traversal(mTarget, x, y);
        return mTarget;
    }

    private void traversal(View view, int x, int y) {
        if (getViewFilter().filter(view) == false) {
            return;
        }
        if (view.getVisibility() != VISIBLE) {
            return;

        }
        if (inRange(view, x, y) == false) {
            return;
        }

        mTarget = view;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = ((ViewGroup) view);
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                traversal(child, x, y);
            }
        }
    }

    private boolean inRange(View view, int x, int y) {
        view.getLocationOnScreen(mLocation);
        return (mLocation[0] <= x
                && mLocation[1] <= y
                && mLocation[0] + view.getWidth() >= x
                && mLocation[1] + view.getHeight() >= y);
    }

    @Override
    public void onAttached(View view) {
//        Toast.makeText(getContext(), "长按编辑控件", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDetached(View view) {
        mTouchTarget = mTarget = null;
    }
}
