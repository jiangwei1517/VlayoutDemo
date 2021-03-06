package com.jiangwei.vlayoutdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.FloatLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.bluelinelabs.logansquare.LoganSquare;
import com.bumptech.glide.Glide;
import com.jiangwei.vlayoutdemo.R;
import com.jiangwei.vlayoutdemo.adapter.BinnerAdapter;
import com.jiangwei.vlayoutdemo.adapter.CommonSticky;
import com.jiangwei.vlayoutdemo.adapter.FloatAdapter;
import com.jiangwei.vlayoutdemo.adapter.HotPointsAdapter;
import com.jiangwei.vlayoutdemo.adapter.SearchAdapter;
import com.jiangwei.vlayoutdemo.adapter.StaggeredAdapter;
import com.jiangwei.vlayoutdemo.adapter.TodayHotAdapter;
import com.jiangwei.vlayoutdemo.bean.TianMao;
import com.jiangwei.vlayoutdemo.utils.ToastUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static android.view.Window.FEATURE_NO_TITLE;

public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private TianMao mTianMao;
    private MyHandler mHandler = new MyHandler(this);
    private static int BinnerMessage_Loop = 1;
    public ViewPager mVp;
    private int newPosition;
    private int mSearchViewHeight;
    private StickyLayoutHelper mStickyLayoutHelperHot;
    private StickyLayoutHelper mStickyLayoutHelperToday;
    private StickyLayoutHelper mStickyLayoutHelperHistory;
    private WindowManager mWindowManager;

    public class MyHandler extends Handler {
        private MainActivity mActivity;

        public MyHandler(MainActivity activity) {
            mActivity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mVp.setCurrentItem(mVp.getCurrentItem() + 1);
                    sendEmptyMessageDelayed(BinnerMessage_Loop, 4000);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        getDataFromJson("index_json.json");
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        VirtualLayoutManager vlm = new VirtualLayoutManager(this);
        mRecyclerView.setLayoutManager(vlm);

        // 设置Adapter列表
        List<DelegateAdapter.Adapter> adapters = new ArrayList<>();

        // 设置CommonSticky布局
        mStickyLayoutHelperHot = new StickyLayoutHelper();
        mStickyLayoutHelperHot.setStickyStart(true);

        mStickyLayoutHelperToday = new StickyLayoutHelper();
        mStickyLayoutHelperToday.setStickyStart(true);

        mStickyLayoutHelperHistory = new StickyLayoutHelper();
        mStickyLayoutHelperHistory.setStickyStart(true);

        tianMaoShopping(adapters);

        tianMaoSearchView(adapters);

        tianMaoBinnerView(adapters);

        tianMaoHotView(adapters);

        tianMaoTodayHots(adapters);

        tianMaoHistory(adapters);

        // 绑定delegateAdapter
        DelegateAdapter delegateAdapter = new DelegateAdapter(vlm);
        delegateAdapter.setAdapters(adapters);
        mRecyclerView.setAdapter(delegateAdapter);
    }

    /*
     * 天猫购物车 有bug,必须放在adapters的最前面,放后面不能滑动
     */
    private void tianMaoShopping(List<DelegateAdapter.Adapter> adapters) {
        // 设置浮动布局
        FloatLayoutHelper floatLayoutHelper = new FloatLayoutHelper();
        // 设置初始位置
        floatLayoutHelper.setDefaultLocation(20, 250);

        adapters.add(new FloatAdapter(this, floatLayoutHelper) {
            @Override
            public void onBindViewHolder(FloatViewHolder holder, int position) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showToast(MainActivity.this, "购物车");
                    }
                });
            }
        });
    }

    /*
     * 天猫历史 有问题StaggeredGridLayoutHelper上面套上一个StickyLayout时会UI卡顿
     */
    private void tianMaoHistory(List<DelegateAdapter.Adapter> adapters) {
        /*
            折叠卡顿
         */
//         mStickyLayoutHelperHistory.setMarginBottom(20);
//         adapters.add(new CommonSticky(this, mStickyLayoutHelperHistory) {
//         @Override
//         public void onBindViewHolder(CommonStickyViewHolder holder, int position) {
//         holder.tv.setText("历史纪录");
//         }
//         });

        // 设置瀑布流布局
        StaggeredGridLayoutHelper staggeredGridLayoutHelper = new StaggeredGridLayoutHelper(2, 10);
        staggeredGridLayoutHelper.setMarginBottom(30);
        adapters.add(new StaggeredAdapter(this, staggeredGridLayoutHelper, mTianMao.history.size()) {
            @Override
            public void onBindViewHolder(final StaggeredViewHolder holder, int position) {
                int display = mWindowManager.getDefaultDisplay().getWidth();
                holder.llMain.getLayoutParams().height = mTianMao.history.get(position).height;
                holder.llMain.getLayoutParams().width = (display - 60) / 2;
                holder.itemView.setTag(position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = (int) holder.itemView.getTag();
                        ToastUtils.showToast(MainActivity.this, mTianMao.history.get(i).name);
                    }
                });
                Glide.with(MainActivity.this).load(mTianMao.history.get(position).imgUrl).dontAnimate().into(holder.iv);
            }
        });
    }

    /*
     * 天猫今日超值
     */
    private void tianMaoTodayHots(List<DelegateAdapter.Adapter> adapters) {
        mStickyLayoutHelperToday.setMarginBottom(20);
        adapters.add(new CommonSticky(this, mStickyLayoutHelperToday) {
            @Override
            public void onBindViewHolder(CommonStickyViewHolder holder, int position) {
                holder.tv.setText("今日超值");
            }
        });

        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setGap(10);
        gridLayoutHelper.setMarginBottom(30);
        adapters.add(new TodayHotAdapter(this, gridLayoutHelper, mTianMao.todayHots.size()) {
            @Override
            public void onBindViewHolder(final TodayHotViewHolder holder, int position) {
                // Glide不能设置tag
                holder.ivBig.setTag(R.id.imageidbig, position);
                holder.ivSmall1.setTag(R.id.imageidsmall, position);
                holder.ivSmall2.setTag(R.id.imageidsmall2, position);
                holder.ivBig.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = (int) holder.ivBig.getTag(R.id.imageidbig);
                        ToastUtils.showToast(MainActivity.this, mTianMao.todayHots.get(i).nameBig);
                    }
                });
                holder.ivSmall1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = (int) holder.ivSmall1.getTag(R.id.imageidsmall);
                        ToastUtils.showToast(MainActivity.this, mTianMao.todayHots.get(i).nameSmall);
                    }
                });
                holder.ivSmall2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i = (int) holder.ivSmall2.getTag(R.id.imageidsmall2);
                        ToastUtils.showToast(MainActivity.this, mTianMao.todayHots.get(i).nameSmall2);
                    }
                });
                Glide.with(MainActivity.this).load(mTianMao.todayHots.get(position).imgUrlBig).into(holder.ivBig);
                Glide.with(MainActivity.this).load(mTianMao.todayHots.get(position).imgUrlSmall).into(holder.ivSmall1);
                Glide.with(MainActivity.this).load(mTianMao.todayHots.get(position).imgUrlSmall2).into(holder.ivSmall2);
            }
        });
    }

    /*
     * 天猫搜索
     */
    private void tianMaoSearchView(List<DelegateAdapter.Adapter> adapters) {
        // 设置Sticky布局
        StickyLayoutHelper stickyLayoutHelper = new StickyLayoutHelper();
        stickyLayoutHelper.setStickyStart(true);

        adapters.add(new SearchAdapter(this, stickyLayoutHelper) {
            @Override
            public void onBindViewHolder(final SearchViewHolder holder, int position) {
                holder.itemView.getViewTreeObserver()
                        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                mSearchViewHeight = holder.itemView.getMeasuredHeight();
                                mStickyLayoutHelperHot.setOffset(mSearchViewHeight);
                                mStickyLayoutHelperToday.setOffset(mSearchViewHeight);
                                mStickyLayoutHelperHistory.setOffset(mSearchViewHeight);
                            }
                        });

            }
        });
    }

    /*
     * 天猫热门
     */
    private void tianMaoHotView(List<DelegateAdapter.Adapter> adapters) {
        mStickyLayoutHelperHot.setMarginBottom(20);
        adapters.add(new CommonSticky(this, mStickyLayoutHelperHot) {
            @Override
            public void onBindViewHolder(CommonStickyViewHolder holder, int position) {
                holder.tv.setText("热点推荐");
            }
        });
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(5, mTianMao.hotPoints.size(), 10, 10);
        gridLayoutHelper.setMarginBottom(30);
        adapters.add(new HotPointsAdapter(this, gridLayoutHelper, mTianMao.hotPoints.size()) {

            @Override
            public void onBindViewHolder(HotPointsHolder holder, final int position) {
                holder.tv.setText(mTianMao.hotPoints.get(position).name);
                Glide.with(MainActivity.this).load(mTianMao.hotPoints.get(position).imgUrl).into(holder.iv);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showToast(MainActivity.this, mTianMao.hotPoints.get(position).name);
                    }
                });
            }
        });
    }

    /*
     * 广告栏
     */
    private void tianMaoBinnerView(List<DelegateAdapter.Adapter> adapters) {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        // viewpager添加View
        final List<View> viewLists = binnerViews();
        adapters.add(new BinnerAdapter(this, singleLayoutHelper) {
            @Override
            public void onBindViewHolder(final BinnerViewHolder holder, int position) {
                mHandler.removeMessages(BinnerMessage_Loop);
                mVp = holder.vp;
                holder.vp.setAdapter(new PagerAdapter() {
                    @Override
                    public int getCount() {
                        return Integer.MAX_VALUE;
                    }

                    @Override
                    public boolean isViewFromObject(View view, Object object) {
                        return view == object;
                    }

                    @Override
                    public void destroyItem(ViewGroup container, int position, Object object) {
                        newPosition = position % mTianMao.loopData.items.size();
                        container.removeView(viewLists.get(newPosition));
                    }

                    @Override
                    public Object instantiateItem(ViewGroup container, int position) {
                        newPosition = position % mTianMao.loopData.items.size();
                        if (newPosition < 0) {
                            newPosition = mTianMao.loopData.items.size() + position;
                        }
                        container.addView(viewLists.get(newPosition));
                        viewLists.get(newPosition).setTag(newPosition);
                        viewLists.get(newPosition).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int i = (int) v.getTag();
                                ToastUtils.showToast(MainActivity.this, mTianMao.loopData.items.get(i).id);
                            }
                        });
                        return viewLists.get(newPosition);
                    }
                });
                setViewPagerScroller();
                holder.vp.setCurrentItem(Integer.MAX_VALUE / 2);
                mHandler.sendEmptyMessageDelayed(BinnerMessage_Loop, 4000);
                holder.vp.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                break;

                            case MotionEvent.ACTION_MOVE:
                                mHandler.removeMessages(BinnerMessage_Loop);
                                break;

                            case MotionEvent.ACTION_UP:
                                mHandler.removeMessages(BinnerMessage_Loop);
                                mHandler.sendEmptyMessageDelayed(BinnerMessage_Loop, 4000);
                                break;

                            case MotionEvent.ACTION_CANCEL:
                                mHandler.removeMessages(1);
                                mHandler.sendEmptyMessageDelayed(BinnerMessage_Loop, 4000);
                                break;
                        }
                        return false;
                    }
                });
            }
        });
    }

    /*
     * 广告栏的动画view
     */
    @NonNull
    private List<View> binnerViews() {
        final List<View> viewLists = new ArrayList<>();
        for (int i = 0; i < mTianMao.loopData.items.size(); i++) {
            View vpItem = View.inflate(MainActivity.this, R.layout.vp_item, null);
            ImageView iv = (ImageView) vpItem.findViewById(R.id.iv);
            TextView tv = (TextView) vpItem.findViewById(R.id.tv);
            Glide.with(MainActivity.this).load(mTianMao.loopData.items.get(i).imgUrl).into(iv);
            tv.setText(mTianMao.loopData.items.get(i).id);
            viewLists.add(vpItem);
        }
        return viewLists;
    }

    /*
     * 从assets文件中获取json数据
     */
    private void getDataFromJson(String path) {
        InputStream is = null;
        try {
            is = getResources().getAssets().open(path);
            mTianMao = LoganSquare.parse(is, TianMao.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * 修改viewPager的滚动动画
     */
    private void setViewPagerScroller() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolator = ViewPager.class.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            Scroller scroller = new Scroller(this, (Interpolator) interpolator.get(null)) {
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    super.startScroll(startX, startY, dx, dy, duration * 7); // 这里是关键，将duration变长或变短
                }
            };
            scrollerField.set(mVp, scroller);
        } catch (NoSuchFieldException e) {
            // Do nothing.
        } catch (IllegalAccessException e) {
            // Do nothing.
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
