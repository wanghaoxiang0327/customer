package com.pomelo.searchcustomer.mine;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.MessageModelBean;
import com.pomelo.searchcustomer.bean.MyMessageBean;
import com.pomelo.searchcustomer.utils.LogUtils;
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
 * Created by wanghaoxiang on 2020-01-08.
 */

public class MyMessageActivity extends BaseMVPActivity<MyMessagePresenter> implements MyMessageView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    int page = 1;
    BaseQuickAdapter<MyMessageBean.Datum, BaseViewHolder> adapter;
    List<MyMessageBean.Datum> list = new ArrayList<>();

    @Override
    protected MyMessagePresenter createPresenter() {
        return new MyMessagePresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_message;
    }

    @Override
    public void initView() {
        initAdapter();
        initListener();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BaseQuickAdapter<MyMessageBean.Datum, BaseViewHolder>(R.layout.item_my_message) {
            @Override
            protected void convert(BaseViewHolder helper, MyMessageBean.Datum item) {
                helper.setText(R.id.tv_title, item.title);
                helper.setText(R.id.tv_description, item.content);
                helper.setText(R.id.tv_create_time, item.createtime);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public void initListener() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.getMyMessage(page + "");
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                refreshLayout.setNoMoreData(false);
                list.clear();
                mPresenter.getMyMessage(page + "");
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getMyMessage(page + "");
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void getMyMessageSuccess(MyMessageBean myMessageBean) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        int allPage = NumberUtils.upCeil(myMessageBean.count);
        if (myMessageBean != null && myMessageBean.datum != null && myMessageBean.datum.size() > 0) {
            //计算出总页码数
            //表示还有更多数据 可以加载更多
            if (page < allPage) {
            } else {//没有更多数据
//                customerEntity.setLoadingMoreEnabled(false);
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            list.addAll(myMessageBean.datum);
        }
        if (list.size() > 0) {
            adapter.setNewData(list);
        } else {
            adapter.setEmptyView(getEmptyView());
        }
    }
}
