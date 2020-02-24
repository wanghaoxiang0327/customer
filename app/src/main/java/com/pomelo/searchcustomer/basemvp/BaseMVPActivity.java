package com.pomelo.searchcustomer.basemvp;

/**
 * Created by wanghaoxiang on 2019-07-20.
 * mvp的Activity基类
 */

public abstract class BaseMVPActivity<P extends BasePresenter> extends BaseActivity implements BaseView {

    protected P mPresenter;

    //创建Presenter
    protected abstract P createPresenter();

    @Override
    public void initData() {
        mPresenter = createPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }


    @Override
    public void hideLoading() {
        closeLoadingDialog();
    }


    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
