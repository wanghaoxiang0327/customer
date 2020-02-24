package com.pomelo.searchcustomer.home;

import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.ShopDetailBean;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface ShopDetailView extends BaseView {
    void getShopDetailSuccess(ShopDetailBean shopDetailBean);
}
