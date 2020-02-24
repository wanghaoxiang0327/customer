package com.pomelo.searchcustomer.home;

import android.webkit.WebView;
import android.widget.TextView;

import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.bean.HelpCenterBean;
import com.pomelo.searchcustomer.bean.HelpDetailBean;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-13.
 */

public class HelpDetailActivity extends BaseMVPActivity<HelpDetailPresenter> implements HelpDetailView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.webview)
    WebView webview;
    HelpCenterBean helpcenter;

    @Override
    protected HelpDetailPresenter createPresenter() {
        return new HelpDetailPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_help_detail;
    }

    @Override
    public void initView() {
        helpcenter = (HelpCenterBean) getIntent().getSerializableExtra("helpcenter");
        tvTitle.setText(helpcenter.title);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getHelpDetailData(helpcenter.id);
    }

    @Override
    public void getHelpDetailSuccess(HelpDetailBean helpCenterBeans) {
        webview.loadData(helpCenterBeans.content, "text/html", "UTF-8");
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
