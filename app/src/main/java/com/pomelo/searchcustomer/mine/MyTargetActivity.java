package com.pomelo.searchcustomer.mine;

import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;

/**
 * Created by wanghaoxiang on 2020-01-11.
 */

public class MyTargetActivity extends BaseMVPActivity<MyTargetPresenter> implements MyTargetView {
    @Override
    protected MyTargetPresenter createPresenter() {
        return new MyTargetPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_target;
    }

    @Override
    public void initView() {

    }
}
