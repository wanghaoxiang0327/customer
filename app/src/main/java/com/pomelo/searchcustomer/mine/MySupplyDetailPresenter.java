package com.pomelo.searchcustomer.mine;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.MySupplyDetailBean;
import com.pomelo.searchcustomer.bean.ShopDetailBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.home.ShopDetailView;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class MySupplyDetailPresenter extends BasePresenter<MySupplyDetailView> {
    public MySupplyDetailPresenter(MySupplyDetailView baseView) {
        super(baseView);
    }

    public void getShopDetail(String id) {
        addDisposite(apiService.getSupplierDetail(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), id, "2"), new BaseObserver<BaseModel<MySupplyDetailBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<MySupplyDetailBean> o) {
                if (o != null && o.data != null) {
                    baseView.getShopDetailSuccess(o.data);
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
