package com.pomelo.searchcustomer.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.HomeIndexBean;
import com.pomelo.searchcustomer.bean.ShopListBean;
import com.pomelo.searchcustomer.utils.GlideUtils;
import com.pomelo.searchcustomer.utils.NumberUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-09.
 */

public class ShopListActivity extends BaseMVPActivity<ShopListPresenter> implements ShopListView {
    @BindView(R.id.et_keyword)
    EditText etKeyword;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    BaseQuickAdapter<ShopListBean.Shop, BaseViewHolder> adapter;
    int page = 1;
    String searchvale;
    List<ShopListBean.Shop> shopListBeans = new ArrayList<>();

    @Override
    protected ShopListPresenter createPresenter() {
        return new ShopListPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shop_list;
    }

    @Override
    public void initView() {
        initAdapter();
        initListener();
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getShopList(page + "", searchvale);
    }

    public void initListener() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.getShopList(page + "", searchvale);
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                shopListBeans.clear();
                refreshLayout.setNoMoreData(false);
                mPresenter.getShopList(page + "", searchvale);
            }
        });
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BaseQuickAdapter<ShopListBean.Shop, BaseViewHolder>(R.layout.item_shop) {
            @Override
            protected void convert(BaseViewHolder helper, ShopListBean.Shop item) {
                ImageView ivShop = helper.getView(R.id.iv_shop);
                helper.setText(R.id.tv_shop_name, item.store_name);
                helper.setText(R.id.tv_shop_description, item.store_desc);
                helper.setText(R.id.tv_phone_number, "tel：" + item.mobile);
                GlideUtils.loadImage(mContext, item.store_images, ivShop);
            }
        };
        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ShopListBean.Shop shopListBean = (ShopListBean.Shop) adapter.getItem(position);
                if (shopListBean != null) {
                    Intent intent = new Intent(mContext, ShopDetailActivity.class);
                    intent.putExtra("id", shopListBean.id);
                    startActivity(intent);
                }
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back: {
                finish();
            }
            break;
            case R.id.tv_search: {
                searchvale = etKeyword.getText().toString().trim();
                page = 1;
                shopListBeans.clear();
                mPresenter.getShopList(page + "", searchvale);
            }
            break;
        }
    }

    @Override
    public void getShopListSuccess(ShopListBean listBeans) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        int allPage = NumberUtils.upCeil(listBeans.count);
        if (listBeans != null && listBeans.list != null && listBeans.list.size() > 0) {
            //计算出总页码数
            //表示还有更多数据 可以加载更多
            if (page < allPage) {
            } else {//没有更多数据
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            shopListBeans.addAll(listBeans.list);
        }
        if (shopListBeans != null && shopListBeans.size() > 0) {
            adapter.setNewData(shopListBeans);
        } else {
            adapter.setEmptyView(getEmptyView());
        }
    }
}
