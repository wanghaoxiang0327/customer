package com.pomelo.searchcustomer.mine;

import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.MySupplyDetailBean;
import com.pomelo.searchcustomer.bean.ShopDetailBean;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface MySupplyDetailView extends BaseView {
    void getShopDetailSuccess(MySupplyDetailBean shopDetailBean);
}
