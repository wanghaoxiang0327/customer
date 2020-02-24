package com.pomelo.searchcustomer.mine;

import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.MySupplyBean;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface MySupplyView extends BaseView {
    void getMySupplySuccess(MySupplyBean mySupplyBean);
}
