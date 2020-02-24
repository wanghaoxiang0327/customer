package com.pomelo.searchcustomer.home;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.HomeIndexBean;
import com.pomelo.searchcustomer.bean.ShopListBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

import java.util.List;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class SourcePresenter extends BasePresenter<SourceView> {
    public SourcePresenter(SourceView baseView) {
        super(baseView);
    }

    public void getsource() {
        addDisposite(apiService.getSourceList(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken")), new BaseObserver<BaseModel<List<HomeIndexBean.Source>>>(baseView) {
            @Override
            public void onSuccess(BaseModel<List<HomeIndexBean.Source>> o) {
                if (o != null && o.data != null) {
                    baseView.getSourceSuccess(o.data);
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
