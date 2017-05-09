# VLayoutDemo

## 介绍
基于阿里开源的VLayout构建了淘宝的模拟首页。很方便，但是也存在一些问题。（Json数据全部来自网页，存在了assets文件夹下）

## 参考
* vLayout <https://github.com/alibaba/vlayout>
*  阿里开源库VLayout的使用笔记 <http://blog.csdn.net/totond/article/details/70172775>

## 问题

### FloatLayoutHelper必须放在adapters的第一个位置。放在后面会引起FloatLayout不滑动的效果。

	/*
        天猫购物车 有bug,必须放在adapters的最前面,放后面不能滑动
     */
    private void tianMaoShopping(List<DelegateAdapter.Adapter> adapters) {
        //设置浮动布局
        FloatLayoutHelper floatLayoutHelper = new FloatLayoutHelper();
        //设置初始位置
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
    
### StaggeredGridLayout上面套了一个StickyLayout时候，会引起imageView复用出现UI卡顿的效果

	/*
        天猫历史
        有问题StaggeredGridLayoutHelper上面套上一个StickyLayout时会UI卡顿
     */
    private void tianMaoHistory(List<DelegateAdapter.Adapter> adapters) {

	//        mStickyLayoutHelperHistory.setMarginBottom(20);
	//        adapters.add(new CommonSticky(this, mStickyLayoutHelperHistory) {
	//            @Override
	//            public void onBindViewHolder(CommonStickyViewHolder holder, int position) {
	//                holder.tv.setText("历史纪录");
	//            }
	//        });

        //设置瀑布流布局
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



