package com.jiangwei.vlayoutdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.jiangwei.vlayoutdemo.R;

/**
 * author:  jiangwei18 on 17/5/9 18:03
 * email:  jiangwei18@baidu.com
 * Hi:   jwill金牛
 */

public abstract class FloatAdapter extends DelegateAdapter.Adapter<FloatAdapter.FloatViewHolder> {
    private Context mContext;
    private LayoutHelper mLayoutHelper;

    public FloatAdapter(Context context, LayoutHelper layoutHelper) {
        mContext = context;
        mLayoutHelper = layoutHelper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public FloatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(mContext, R.layout.float_item, null);
        return new FloatViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class FloatViewHolder extends RecyclerView.ViewHolder {

        public FloatViewHolder(View itemView) {
            super(itemView);
        }
    }
}
