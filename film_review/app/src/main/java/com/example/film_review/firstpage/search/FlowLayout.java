package com.example.film_review.firstpage.search;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {

    /*
    public FlowLayout(Context context, Attributes attributes, int defStyle){
        super(context, (AttributeSet) attributes,defStyle);
    }

    public FlowLayout(Context context, Attributes attributes){this(context,attributes,0);
    }

    public FlowLayout(Context context){this(context,null);}
    */

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public FlowLayout(Context context, AttributeSet attributes, int defStyleAttr) {
        super(context, attributes, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //在wrap_content情况下，记录宽和高
        int width = 0;
        int height = 0;
        //记录每一行的高度和宽度
        int lineWidth = 0;
        int lineHeight = 0;
        //得到内部元素的个数
        int mCount = getChildCount();


        if (getChildCount() == 0)
            setMeasuredDimension(0, 0);
        for (int i = 0; i < mCount; i++) {

            //通过索引拿到子view
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

            if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                width = Math.max(width, lineWidth);//对比得到最大的宽度
                lineWidth = childWidth;//重置
                height += lineHeight;
                lineHeight = childHeight;//记录行高
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }//不换行

            if (i == mCount - 1) {
                width = Math.max(lineHeight, width);
                height += lineHeight;
            }//特殊情况，指最后一个控件
            setMeasuredDimension(
                    modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                    modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()
            );
        }
    }

    private List<List<View>> mAllViews = new ArrayList<List<View>>();//储存所有的view
    private List<Integer> mLineHeight = new ArrayList<Integer>();//每一行的高度

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();

        int width = getWidth();//当前ViewGroup的宽度

        int lineWidth = 0;
        int lineHeight = 0;
        List<View> lineView = new ArrayList<View>();//存放每一行的子View

        int mCount = getChildCount();

        for (int i = 0; i < mCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //如果要换行
            if (childWidth + lineWidth + layoutParams.leftMargin + layoutParams.rightMargin > width - getPaddingRight() - getPaddingLeft()) {
                mLineHeight.add(lineHeight);
                mAllViews.add(lineView);

                lineWidth = 0;
                lineHeight = childHeight + layoutParams.topMargin + layoutParams.bottomMargin;
                lineView = new ArrayList<View>();
            }
            lineWidth += childWidth + layoutParams.leftMargin + layoutParams.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + layoutParams.topMargin + layoutParams.bottomMargin);
            lineView.add(child);
        }
        mLineHeight.add(lineHeight);
        mAllViews.add(lineView);//处理最后一行

        int left = getPaddingLeft();
        int top = getPaddingTop();
        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; i++) {
            lineView = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            for (int j = 0; j < lineView.size(); j++) {
                View child = lineView.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
                int lc = left + layoutParams.leftMargin;
                int tc = top + layoutParams.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

}
