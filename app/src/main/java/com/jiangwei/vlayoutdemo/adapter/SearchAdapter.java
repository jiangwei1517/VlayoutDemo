package com.jiangwei.vlayoutdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.jiangwei.vlayoutdemo.R;

/**
 * author:  jiangwei18 on 17/5/9 10:36
 * email:  jiangwei18@baidu.com
 * Hi:   jwill金牛
 */

public abstract class SearchAdapter extends DelegateAdapter.Adapter<SearchAdapter.SearchViewHolder> {
    private LayoutHelper mLayoutHelper;
    private Context mContext;

    public SearchAdapter(Context context, LayoutHelper layoutHelper) {
        mLayoutHelper = layoutHelper;
        mContext = context;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.search_item, null);
        return new SearchViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return Types.SEARCH_TYPE;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        public SearchViewHolder(View itemView) {
            super(itemView);
        }
    }
}
