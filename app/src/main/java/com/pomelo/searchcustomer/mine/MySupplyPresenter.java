package com.pomelo.searchcustomer.mine;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.MySupplyBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class MySupplyPresenter extends BasePresenter<MySupplyView> {
    public MySupplyPresenter(MySupplyView baseView) {
        super(baseView);
    }

    public void getMySupply(String type) {
        addDisposite(apiService.getMySupplyList(Contanst.TOKEN, Contanst.VERSION, type, PreferencesUtil.getString("utoken")), new BaseObserver<BaseModel<MySupplyBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<MySupplyBean> o) {
                if (o != null && o.data != null) {
                    baseView.getMySupplySuccess(o.data);
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

    public void deleteSupply(String id) {
        addDisposite(apiService.deleteMySupply(Contanst.TOKEN, Contanst.VERSION, id, PreferencesUtil.getString("utoken")), new BaseObserver<BaseModel<MySupplyBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<MySupplyBean> o) {
                if (o != null && o.data != null) {
                    baseView.getMySupplySuccess(o.data);
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
