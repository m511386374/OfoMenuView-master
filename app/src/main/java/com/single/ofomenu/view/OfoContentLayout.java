package com.single.ofomenu.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangcheng on 17/9/20.
 */

public class OfoContentLayout extends LinearLayout {
    private static final String TAG = OfoContentLayout.class.getSimpleName();
    List<Float> endOffset = new ArrayList<>();

    public boolean isAnimationing() {
        return isAnimationing;
    }

    private boolean isAnimationing;
    private boolean hasListener;

    public OfoContentLayout(Context context) {
        super(context);
    }

    public OfoContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OfoContentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void open() {
        for (int i = 0; i < getChildCount(); i++) {
            ObjectAnimator oa = ObjectAnimator.ofFloat(getChildAt(i), "translationY", endOffset.get(i), 0);
            oa.setDuration(700);
            if (!hasListener) {
                hasListener = true;
                oa.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        isAnimationing = true;

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isAnimationing = false;
                        hasListener = false;
                    }
                });

            }
            oa.start();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            child.setTag(i);
            child.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    child.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    endOffset.add(child.getTop() + ((int) child.getTag()) *
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getContext().getResources().getDisplayMetrics()));
                }
            });
        }

    }

}
