package com.firstblh.qqsidemenu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.firstblh.qqsidemenu.R;

/**
 * Created by firstblh on 15/12/3.
 */
public class SlidingMenu extends HorizontalScrollView {
    private LinearLayout mWrapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private int menuWidth;
    private boolean isInit = true;
    private int screenWidth;
    private int mRightPadding;
    private boolean isOpen;


    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, -1);

    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defStyleAttr, 0);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.SlidingMenu_rightPadding:
                    mRightPadding = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()));
                    break;
            }

        }

        a.recycle();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        //把TypedValue.COMPLEX_UNIT_DIP(dip)转化成px
//        mRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 66, context.getResources().getDisplayMetrics());
    }

    public SlidingMenu(Context context) {
        this(context, null);
    }

    /**
     * 设置子View的宽和高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isInit) {
            mWrapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWrapper.getChildAt(0);
            mContent = (ViewGroup) mWrapper.getChildAt(1);
            menuWidth = mMenu.getLayoutParams().width = screenWidth - mRightPadding;
            mContent.getLayoutParams().width = screenWidth;
            isInit = false;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 通过设置偏移量,将menu隐藏
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            this.scrollTo(menuWidth, 0);
        }
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                //scrollX 是隐藏在左边的宽度
                int scrollX = getScrollX();
                Log.e("SlidingMenu onLayout", "scrollX --> " + scrollX);
                if (scrollX >= menuWidth / 2) {
                    this.smoothScrollTo(menuWidth, 0);
                    isOpen = true;
                } else {
                    this.smoothScrollTo(0, 0);
                    isOpen = false;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * open side menu
     */
    public void openMenu() {
        if (isOpen) return;
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    /**
     * close side menu
     */
    public void closeMenu() {
        if (!isOpen) return;
        this.smoothScrollTo(menuWidth, 0);
        isOpen = false;
    }

    /**
     * change the side menu status
     */
    public void toggle() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = 1 - 0.7f * l / menuWidth;
        mMenu.setTranslationX(menuWidth - menuWidth * scale);
        mContent.setScaleX(mContent.getWidth() * (l / menuWidth ));
        mContent.setScaleY(mContent.getHeight() * (l / menuWidth));

    }
}
