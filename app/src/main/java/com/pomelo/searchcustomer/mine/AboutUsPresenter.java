package com.pomelo.searchcustomer.mine;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.AboutUsBean;
import com.pomelo.searchcustomer.bean.MySupplyDetailBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.main.MainView;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

import java.util.List;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class AboutUsPresenter extends BasePresenter<AboutUsView> {
    public AboutUsPresenter(AboutUsView baseView) {
        super(baseView);
    }

    public void getInfo() {
        addDisposite(apiService.linkUs(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken")), new BaseObserver<BaseModel<List<AboutUsBean>>>(baseView) {
            @Override
            public void onSuccess(BaseModel<List<AboutUsBean>> o) {
                if (o != null && o.data != null) {
                    baseView.linkusSuccess(o.data);
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
