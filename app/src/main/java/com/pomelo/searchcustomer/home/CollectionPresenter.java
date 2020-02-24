package com.pomelo.searchcustomer.home;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.CustomerBean;
import com.pomelo.searchcustomer.bean.LatLonBean;
import com.pomelo.searchcustomer.bean.ShopDetailBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.mine.AboutUsView;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

import java.util.List;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class CollectionPresenter extends BasePresenter<CollectionView> {
    public CollectionPresenter(CollectionView baseView) {
        super(baseView);
    }

    public void collectionData(String searchKey, String address, String id, String page, String length) {
        addDisposite(apiService.collectionData(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), searchKey, address, id, page, "50", length), new BaseObserver<BaseModel<CustomerBean>>(baseView, false) {
            @Override
            public void onSuccess(BaseModel<CustomerBean> o) {
                if (o != null && o.data != null) {
                    baseView.getCollectionData(o.data);
                } else {
                    onError(o.info);
                }
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    public void getLatLon(String word) {
        addDisposite(apiService.getLanLat(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), word), new BaseObserver<BaseModel<LatLonBean>>(baseView, false) {
            @Override
            public void onSuccess(BaseModel<LatLonBean> o) {
                if (o != null && o.data != null) {
                    baseView.getLatLonSuccess(o.data);
                } else {
                    onError(o.info);
                }
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
