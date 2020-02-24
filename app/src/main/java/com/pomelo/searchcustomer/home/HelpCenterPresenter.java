package com.pomelo.searchcustomer.home;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.HelpCenterBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

import java.util.List;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class HelpCenterPresenter extends BasePresenter<HelpCenterView> {
    public HelpCenterPresenter(HelpCenterView baseView) {
        super(baseView);
    }

    public void getHelpCenterData() {
        addDisposite(apiService.getHelpCenterList(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken")), new BaseObserver<BaseModel<List<HelpCenterBean>>>(baseView) {
            @Override
            public void onSuccess(BaseModel<List<HelpCenterBean>> o) {
                if (o != null && o.data != null) {
                    baseView.getHelpcenterSuccess(o.data);
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
