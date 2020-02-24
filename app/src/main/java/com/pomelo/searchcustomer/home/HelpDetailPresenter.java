package com.pomelo.searchcustomer.home;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.HelpCenterBean;
import com.pomelo.searchcustomer.bean.HelpDetailBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

import java.util.List;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class HelpDetailPresenter extends BasePresenter<HelpDetailView> {
    public HelpDetailPresenter(HelpDetailView baseView) {
        super(baseView);
    }

    public void getHelpDetailData(String id) {
        addDisposite(apiService.getHelpDetail(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), id), new BaseObserver<BaseModel<HelpDetailBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<HelpDetailBean> o) {
                if (o != null && o.data != null) {
                    baseView.getHelpDetailSuccess(o.data);
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
