package com.jiangwei.vlayoutdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.jiangwei.vlayoutdemo.R;

/**
 * author:  jiangwei18 on 17/5/8 18:56
 * email:  jiangwei18@baidu.com
 * Hi:   jwill金牛
 */

public abstract class HotPointsAdapter extends DelegateAdapter.Adapter<HotPointsAdapter.HotPointsHolder> {
    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private int mCount;

    public HotPointsAdapter(Context context, LayoutHelper layoutHelper, int count) {
        mContext = context;
        mLayoutHelper = layoutHelper;
        mCount = count;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public HotPointsAdapter.HotPointsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(mContext, R.layout.hot_points_item, null);
        return new HotPointsHolder(v);
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    public static class HotPointsHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public TextView tv;

        public HotPointsHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
