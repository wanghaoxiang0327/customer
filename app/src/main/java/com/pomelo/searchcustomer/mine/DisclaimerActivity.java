package com.pomelo.searchcustomer.mine;

import android.text.TextUtils;
import android.webkit.WebView;

import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.DisclaimerBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-08.
 */

public class DisclaimerActivity extends BaseMVPActivity<DisclaimerPresenter> implements DisclaimerView {
    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected DisclaimerPresenter createPresenter() {
        return new DisclaimerPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_disclaimer;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getAgreement();
    }

    @Override
    public void getAgreementSuccess(DisclaimerBean disclaimerBean) {
        if (!TextUtils.isEmpty(disclaimerBean.content)) {
            webview.loadData(disclaimerBean.content, "text/html", "UTF-8");
        }

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
