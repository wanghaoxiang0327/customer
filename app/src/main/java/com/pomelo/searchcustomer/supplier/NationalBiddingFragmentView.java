package com.pomelo.searchcustomer.supplier;


import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.SupplierClassBean;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface NationalBiddingFragmentView extends BaseView {
    void getSupplierClassSuccess(SupplierClassBean supplierClassBean);
}
