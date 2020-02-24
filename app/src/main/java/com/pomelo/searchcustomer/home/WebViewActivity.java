package com.pomelo.searchcustomer.home;

import android.webkit.WebView;

import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;

import butterknife.BindView;

/**
 * Created by wanghaoxiang on 2020-01-18.
 */

public class WebViewActivity extends BaseMVPActivity<WebViewPresenter> implements WebViewView {
    @BindView(R.id.webview)
    WebView webview;
    String loadUrl;

    @Override
    protected WebViewPresenter createPresenter() {
        return new WebViewPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_webview;
    }

    @Override
    public void initView() {
        loadUrl = getIntent().getStringExtra("url");
        webview.loadUrl(loadUrl);
    }
}
