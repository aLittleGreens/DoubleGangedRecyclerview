package cyk.com.doublegangedrecyclerview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import cyk.com.doublegangedrecyclerview.R;
import cyk.com.doublegangedrecyclerview.base.RvAdapter;
import cyk.com.doublegangedrecyclerview.base.RvHolder;
import cyk.com.doublegangedrecyclerview.base.RvListener;

import static android.R.attr.type;

/**
 * Created by admin on 2017-7-19.
 */

public class LeftAdapter extends RvAdapter<String>{

    private int checkedPosition;

    public LeftAdapter(Context context, List<String> list, RvListener listener) {
        super(context, list, listener);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.left_listitem_layout;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new SortHolder(view,type,listener);
    }

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }
    private class SortHolder extends RvHolder<String>{

        private TextView sortText;
        private View mView;
        public SortHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);
            mView = itemView;
            sortText = (TextView) itemView.findViewById(R.id.sortText);
        }

        @Override
        public void bindHolder(String string, int position) {
            sortText.setText(string);
            if (position == checkedPosition) {
                mView.setBackgroundColor(Color.parseColor("#f3f3f3"));
                sortText.setTextColor(Color.parseColor("#0068cf"));
            } else {
                mView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                sortText.setTextColor(Color.parseColor("#1e1d1d"));
            }
        }
    }
}
