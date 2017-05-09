package com.jiangwei.vlayoutdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.jiangwei.vlayoutdemo.R;

/**
 * author:  jiangwei18 on 17/5/9 13:01
 * email:  jiangwei18@baidu.com
 * Hi:   jwill金牛
 */

public abstract class CommonSticky extends DelegateAdapter.Adapter<CommonSticky.CommonStickyViewHolder> {
    private Context mContext;
    private LayoutHelper mLayoutHelper;

    public CommonSticky(Context context, LayoutHelper layoutHelper) {
        mContext = context;
        mLayoutHelper = layoutHelper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public CommonStickyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(mContext, R.layout.common_sticky_item, null);
        return new CommonStickyViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        return Types.COMMON_STICKY_TYPE;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class CommonStickyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;

        public CommonStickyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
