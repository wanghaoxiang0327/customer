package com.pomelo.searchcustomer.supplier;


import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.SullierBean;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface ContentFragmentView extends BaseView {
    void getSuppliersListSuccess(SullierBean sullierBean);

    void compliantSuccess(String s);
}
