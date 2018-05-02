/*
 * Created by IT小蔡 on 17-7-19 下午4:14
 * Copyright (c) 2017. All right reserved
 *
 * Last modified 17-7-19 下午4:14
 */

package cyk.com.doublegangedrecyclerview.itemdecoration;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import cyk.com.doublegangedrecyclerview.R;

/**
 * Created by admin on 2017-7-19.
 */

public class StickyItemDescoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "PinnedSectionDecoration";

    private DecorationCallback callback;
    private TextPaint textPaint;
    private Paint paint;
    private int topGap;


    public StickyItemDescoration(Context context, DecorationCallback decorationCallback) {
        Resources res = context.getResources();
        this.callback = decorationCallback;

        paint = new Paint();
        paint.setColor(res.getColor(R.color.colorAccent));

        textPaint = new TextPaint();
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(50);
        textPaint.setColor(Color.BLACK);

        textPaint.setTextAlign(Paint.Align.LEFT);

        topGap = res.getDimensionPixelSize(R.dimen.sectioned_top);


    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        long groupId = callback.getGroupId(pos);
        if (groupId < 0) return;
        if (pos == 0 || isFirstInGroup(pos)) {
            outRect.top = topGap;
        } else {
            outRect.top = 0;
        }
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        Log.e(TAG, "onDrawOver: " );
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount(); //获得可见的item数量
        int itemCount = parent.getAdapter().getItemCount();//获得所有item数量
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        long preGroupId, groupId = -1;
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);

            preGroupId = groupId;
            groupId = callback.getGroupId(position);
            if (groupId < 0 || groupId == preGroupId) continue;

            String textLine = callback.getGroupFirstLine(position).toUpperCase();
            if (TextUtils.isEmpty(textLine)) continue;

           int viewBottom = view.getBottom();//此时view底部的距离
            float textY = Math.max(topGap, view.getTop());
            if (position + 1 < itemCount) { //下一个和当前不一样移动当前
                long nextGroupId = callback.getGroupId(position + 1);
                if (nextGroupId != groupId && viewBottom < textY ) {//组内最后一个view进入了header
                    textY = viewBottom;
                }
            }
            c.drawRect(left, textY - topGap, right, textY, paint);

            Rect rect = new Rect();
            textPaint.getTextBounds(textLine,0,textLine.length(),rect);
            c.drawText(textLine, view.getWidth()/2-rect.width()/2, textY-(topGap-rect.height())/2, textPaint);//绘制居中标题
        }

    }

    private boolean isFirstInGroup(int pos) {
        if (pos == 0) {
            return true;
        } else {
            long prevGroupId = callback.getGroupId(pos - 1);
            long groupId = callback.getGroupId(pos);
            return prevGroupId != groupId;
        }
    }


    public interface DecorationCallback {

        long getGroupId(int position);

        String getGroupFirstLine(int position);
    }
}
