package com.pomelo.searchcustomer.home;

import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.CustomerBean;
import com.pomelo.searchcustomer.bean.LatLonBean;

import java.util.List;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface CollectionView extends BaseView {
    void getCollectionData(CustomerBean customerBean);

    void getLatLonSuccess(LatLonBean latLonBean);
}
