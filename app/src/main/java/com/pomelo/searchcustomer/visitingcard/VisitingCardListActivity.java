package com.pomelo.searchcustomer.visitingcard;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.VistingCardBean;
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

public class VisitingCardListActivity extends BaseMVPActivity<VisitingCardListPresenter> implements VisitingCardListView {
    @BindView(R.id.et_keyword)
    EditText etKeyword;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_card_count)
    TextView tvCardCount;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    String keyWord;
    private int page = 1;
    List<VistingCardBean.VistingCard> vistingCards = new ArrayList<>();
    BaseQuickAdapter<VistingCardBean.VistingCard, BaseViewHolder> adapter;

    @Override
    protected VisitingCardListPresenter createPresenter() {
        return new VisitingCardListPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_visiting_card_list;
    }

    @Override
    public void initView() {
        initAdapter();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BaseQuickAdapter<VistingCardBean.VistingCard, BaseViewHolder>(R.layout.item_visiting_card) {
            @Override
            protected void convert(BaseViewHolder helper, VistingCardBean.VistingCard item) {
                helper.setText(R.id.tv_name, item.card_info.name);
                helper.setText(R.id.tv_create_time, item.createtime);
                helper.setText(R.id.tv_post, item.card_info.position);
                helper.setText(R.id.tv_company_name, item.card_info.company_name);
                ImageView ivHead = helper.getView(R.id.iv_head);
                GlideUtils.loadImage(mContext, item.image, ivHead);
            }
        };
        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getVistingCardList(keyWord, page + "");
    }


    public void initListener() {
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                mPresenter.getVistingCardList(keyWord, page + "");
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                vistingCards.clear();
                refreshLayout.setNoMoreData(false);
                mPresenter.getVistingCardList(keyWord, page + "");
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
                keyWord = etKeyword.getText().toString().trim();
                page = 1;
                mPresenter.getVistingCardList(keyWord, page + "");
            }
            break;
        }
    }

    @Override
    public void getVisitingCardListSuccess(VistingCardBean vistingCardBean) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        tvCardCount.setText("名片（" + vistingCardBean.count + ")");
        int allPage = NumberUtils.upCeil(vistingCardBean.count);
        if (vistingCardBean.list != null && vistingCardBean.list.size() > 0) {
            //计算出总页码数
            //表示还有更多数据 可以加载更多
            if (page < allPage) {
            } else {//没有更多数据
//                customerEntity.setLoadingMoreEnabled(false);
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            vistingCards.addAll(vistingCardBean.list);
        }
        if (vistingCards.size() > 0) {
            adapter.setNewData(vistingCards);
        } else {
            adapter.setEmptyView(getEmptyView());
        }
    }
}
