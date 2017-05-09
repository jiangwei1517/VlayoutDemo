package com.jiangwei.vlayoutdemo.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.jiangwei.vlayoutdemo.R;

/**
 * author:  jiangwei18 on 17/5/8 16:52
 * email:  jiangwei18@baidu.com
 * Hi:   jwill金牛
 */

public abstract class BinnerAdapter extends DelegateAdapter.Adapter<BinnerAdapter.BinnerViewHolder> {
    private Context mContext;
    private LayoutHelper mLayoutHelper;

    public BinnerAdapter(Context context, LayoutHelper layoutHelper) {
        mContext = context;
        mLayoutHelper = layoutHelper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public BinnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.binner_item, null);
        return new BinnerViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class BinnerViewHolder extends RecyclerView.ViewHolder {
        public ViewPager vp;

        public BinnerViewHolder(View itemView) {
            super(itemView);
            vp = (ViewPager) itemView.findViewById(R.id.vp);
        }
    }
}
