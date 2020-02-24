package com.pomelo.searchcustomer.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.HomeIndexBean;
import com.pomelo.searchcustomer.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-02-05.
 */

public class SourceActivity extends BaseMVPActivity<SourcePresenter> implements SourceView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    BaseQuickAdapter<HomeIndexBean.Source, BaseViewHolder> adapter;

    @Override
    protected SourcePresenter createPresenter() {
        return new SourcePresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_source;
    }

    @Override
    public void initView() {
        initAdapter();
    }


    @Override
    public void initData() {
        super.initData();
        mPresenter.getsource();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        adapter = new BaseQuickAdapter<HomeIndexBean.Source, BaseViewHolder>(R.layout.item_customer_source) {
            @Override
            protected void convert(BaseViewHolder helper, HomeIndexBean.Source item) {
                ImageView ivSource = helper.getView(R.id.iv_customer);
                helper.setText(R.id.tv_customer_name, item.name);
                GlideUtils.loadImage(mContext, item.image, ivSource);
            }
        };
        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HomeIndexBean.Source source = (HomeIndexBean.Source) adapter.getItem(position);
                Intent intent = new Intent(mContext, CollectionActivity.class);
                intent.putExtra("id", source.id);
                intent.putExtra("name", source.name);
                startActivity(intent);
            }
        });
    }

    @Override
    public void getSourceSuccess(List<HomeIndexBean.Source> sources) {
        if (sources != null && sources.size() > 0) {
            adapter.setNewData(sources);
        } else {
            adapter.setEmptyView(getEmptyView());
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
