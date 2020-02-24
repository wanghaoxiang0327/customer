package com.pomelo.searchcustomer.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.MySupplyBean;
import com.pomelo.searchcustomer.utils.GlideUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-11.
 */

public class MySupplyActivity extends BaseMVPActivity<MySupplyPresenter> implements MySupplyView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    BaseQuickAdapter<MySupplyBean.SupplyBaan, BaseViewHolder> adapter;
    String type;

    @Override
    protected MySupplyPresenter createPresenter() {
        return new MySupplyPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_supply;
    }

    @Override
    public void initView() {
        type = getIntent().getStringExtra("type");
        if ("1".equals(type)) {
            tvTitle.setText("我的招标");
        } else {
            tvTitle.setText("我的供应");
        }
        initAdapter();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BaseQuickAdapter<MySupplyBean.SupplyBaan, BaseViewHolder>(R.layout.item_my_supply) {
            @Override
            protected void convert(BaseViewHolder helper, MySupplyBean.SupplyBaan item) {
                ImageView ivShop = helper.getView(R.id.iv_shop_icon);
                TextView tvdelete = helper.getView(R.id.tv_delete);
                helper.setText(R.id.tv_shop_name, item.title);
                helper.setText(R.id.tv_click_count, "点击量：" + item.click);
                helper.setText(R.id.tv_phone_number, item.mobile);
                helper.setText(R.id.tv_data, item.createtime);
                GlideUtils.loadImage(mContext, item.images, ivShop);
                helper.setText(R.id.tv_status, item.statusInfo);
                tvdelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.deleteSupply(item.id);
                    }
                });
            }
        };
        adapter.openLoadAnimation();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getMySupply(type);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void getMySupplySuccess(MySupplyBean mySupplyBean) {
        if (mySupplyBean.datum != null && mySupplyBean.datum.size() > 0) {
            adapter.setNewData(mySupplyBean.datum);
        } else {
            adapter.setEmptyView(getEmptyView());
        }
    }
}
