/*
 * Created by IT小蔡 on 17-7-19 下午1:39
 * Copyright (c) 2017. All right reserved
 *
 * Last modified 17-7-19 下午1:39
 */

package cyk.com.doublegangedrecyclerview.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import cyk.com.doublegangedrecyclerview.R;

/**
 * Created by admin on 2017-7-19.
 */

public class LeftAndRightTagDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "LeftAndRightTagDecorati";
    private int tagWidth;
    private Paint leftPaint;
    private Paint rightPaint;
    private Rect leftBound;
    private Rect rightBound;
    private Paint mPaint;
    private String leftString = "热卖";
    private String rightString = "售罄";
    private final float textSize;

    public LeftAndRightTagDecoration(Context context) {
        leftPaint = new Paint();
        leftPaint.setColor(context.getResources().getColor(R.color.colorAccent));
        rightPaint = new Paint();
        rightPaint.setColor(context.getResources().getColor(R.color.colorPrimary));
        tagWidth = context.getResources().getDimensionPixelSize(R.dimen.divider_height);
        Log.e(TAG, "tagWidth:"+tagWidth );
        textSize = context.getResources().getDimensionPixelSize(R.dimen.textSize);
        Log.e(TAG,"textSize"+textSize);
        initPaint();

    }

    private void initPaint() {
        rightBound = new Rect();
        leftBound = new Rect();
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(textSize);
        mPaint.getTextBounds(leftString, 0, leftString.length(), leftBound);
        mPaint.getTextBounds(rightString, 0, rightString.length(), rightBound);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        c.save();

        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int pos = parent.getChildAdapterPosition(child);
            boolean isLeft = pos % 2 == 0;
            if (isLeft) {
                int left = child.getLeft();
                int right = left + tagWidth;
                int top = child.getTop();
                int bottom = child.getBottom()-child.getHeight()/2;

                Rect rect = new Rect(left,top,right,bottom);
                c.drawRect(rect, leftPaint);
                c.drawText(leftString,left,rect.height()/2+top+leftBound.height()/2,mPaint);
            } else {
                int right = child.getRight();
                int left = right - tagWidth;
                int top = child.getTop();
                int bottom = child.getBottom()-child.getHeight()/2;
                Rect rect = new Rect(left,top,right,bottom);
                c.drawRect(rect, rightPaint);
                c.drawText(rightString,left,rect.height()/2+top+rightBound.height()/2,mPaint);

            }
        }
        c.restore();
    }
}

