package com.jiangwei.vlayoutdemo.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.jiangwei.vlayoutdemo.R;

/**
 * author:  jiangwei18 on 17/5/9 13:32
 * email:  jiangwei18@baidu.com
 * Hi:   jwill金牛
 */

public abstract class StaggeredAdapter extends DelegateAdapter.Adapter<StaggeredAdapter.StaggeredViewHolder> {
    private Context mContext;
    private int mCount;
    private LayoutHelper mLayoutHelper;

    public StaggeredAdapter(Context mContext, LayoutHelper layoutHelper, int count) {
        this.mCount = count;
        this.mContext = mContext;
        this.mLayoutHelper = layoutHelper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public StaggeredViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(mContext, R.layout.staggered_item, null);
        return new StaggeredViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    public static class StaggeredViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public ConstraintLayout llMain;
        public CardView cardView;

        public StaggeredViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            llMain = (ConstraintLayout) itemView.findViewById(R.id.ll_main);
//            cardView = (CardView) itemView.findViewById(R.id.card);
        }
    }
}
