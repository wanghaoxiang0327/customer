package com.pomelo.searchcustomer.home;

import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.MyCollectionBean;
import com.pomelo.searchcustomer.bean.MyCollectionInfoBean;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface MyCollectionView extends BaseView {
    void getMyCollectionInfo(MyCollectionInfoBean myCollectionInfoBean);

    void getCollectionData(MyCollectionBean myCollectionBean);
    void deleteSuccess();

    void exportDatSuccess(String url);
}
