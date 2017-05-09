package com.jiangwei.vlayoutdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.jiangwei.vlayoutdemo.R;

/**
 * author:  jiangwei18 on 17/5/9 11:11
 * email:  jiangwei18@baidu.com
 * Hi:   jwill金牛
 */

public abstract class TodayHotAdapter extends DelegateAdapter.Adapter<TodayHotAdapter.TodayHotViewHolder> {
    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private int mCount;

    public TodayHotAdapter(Context context, LayoutHelper layoutHelper, int count) {
        mContext = context;
        mLayoutHelper = layoutHelper;
        mCount = count;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public TodayHotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.today_hot_item, null);
        return new TodayHotViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    public static class TodayHotViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivBig;
        public ImageView ivSmall1;
        public ImageView ivSmall2;

        public TodayHotViewHolder(View itemView) {
            super(itemView);
            ivBig = (ImageView) itemView.findViewById(R.id.iv_big);
            ivSmall1 = (ImageView) itemView.findViewById(R.id.iv_small1);
            ivSmall2 = (ImageView) itemView.findViewById(R.id.iv_small2);
        }
    }
}
