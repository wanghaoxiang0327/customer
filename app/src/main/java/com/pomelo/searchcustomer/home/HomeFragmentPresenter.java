package com.pomelo.searchcustomer.home;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.HomeIndexBean;
import com.pomelo.searchcustomer.contract.Contanst;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class HomeFragmentPresenter extends BasePresenter<HomeFragmentView> {
    public HomeFragmentPresenter(HomeFragmentView baseView) {
        super(baseView);
    }

    public void getIndexData() {
        addDisposite(apiService.getIndex(Contanst.TOKEN, Contanst.VERSION), new BaseObserver<BaseModel<HomeIndexBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<HomeIndexBean> o) {
                if (o != null && o.data != null) {
                    baseView.getIndexSuccess(o.data);
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

    public void searchCustomer() {
        addDisposite(apiService.searchCustomer(Contanst.TOKEN, Contanst.VERSION, "2"), new BaseObserver<BaseModel<HomeIndexBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<HomeIndexBean> o) {
                if (o != null && o.data != null) {
                    baseView.getIndexSuccess(o.data);
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
