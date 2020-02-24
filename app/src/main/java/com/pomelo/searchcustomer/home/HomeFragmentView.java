package com.pomelo.searchcustomer.home;


import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.HomeIndexBean;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface HomeFragmentView extends BaseView {
    void getIndexSuccess(HomeIndexBean homeIndexBean);
}
