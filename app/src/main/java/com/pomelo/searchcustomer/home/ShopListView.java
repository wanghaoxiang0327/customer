package com.pomelo.searchcustomer.home;

import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.ShopListBean;

import java.util.List;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface ShopListView extends BaseView {
    void getShopListSuccess(ShopListBean listBeans);
}
