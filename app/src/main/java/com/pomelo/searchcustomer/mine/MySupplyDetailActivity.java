package com.pomelo.searchcustomer.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.MySupplyDetailBean;
import com.pomelo.searchcustomer.bean.ShopDetailBean;
import com.pomelo.searchcustomer.home.ShopDetailPresenter;
import com.pomelo.searchcustomer.home.ShopDetailView;
import com.pomelo.searchcustomer.utils.GlideUtils;
import com.pomelo.searchcustomer.weight.NoScrollRecyclerView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-09.
 */

public class MySupplyDetailActivity extends BaseMVPActivity<MySupplyDetailPresenter> implements MySupplyDetailView {
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_content)
    TextView tvcontent;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_description)
    WebView tvDescription;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.recyclerView)
    NoScrollRecyclerView recyclerView;
    String id, title;
    BaseQuickAdapter<String, BaseViewHolder> adapter;

    @Override
    protected MySupplyDetailPresenter createPresenter() {
        return new MySupplyDetailPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shop_detail;
    }

    @Override
    public void initView() {
        title = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        initAdapter();
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getShopDetail(id);
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_shop_detail) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                ImageView imageView = helper.getView(R.id.iv_shop);
                GlideUtils.loadImage(mContext, item, imageView);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.iv_back, R.id.tv_share_wechat, R.id.tv_share_wechat_circle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back: {
                finish();
            }
            break;
            case R.id.tv_share_wechat:
                break;
            case R.id.tv_share_wechat_circle:
                break;
        }
    }

    @Override
    public void getShopDetailSuccess(MySupplyDetailBean shopDetailBean) {
        tvShopName.setText(shopDetailBean.title);
        tvPhoneNumber.setText(shopDetailBean.mobile);
        tvCreateTime.setVisibility(View.VISIBLE);
        tvCreateTime.setText(shopDetailBean.createtime);
        tvDescription.setVisibility(View.GONE);
        tvcontent.setText(shopDetailBean.content);
        adapter.setNewData(shopDetailBean.images);
    }
}
