package com.pomelo.searchcustomer.supplier;

import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.SullierBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

/**
 * Created by wanghaoxiang on 2020-01-07.
 */

public class ContentFragmentPresenter extends BasePresenter<ContentFragmentView> {
    public ContentFragmentPresenter(ContentFragmentView baseView) {
        super(baseView);
    }

    public void getSupplierList(String type, String page, String needType, String secondNavAction) {
        addDisposite(apiService.getSupplierList(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), type, page, "10", needType, secondNavAction), new BaseObserver<BaseModel<SullierBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<SullierBean> o) {
                if (o != null && o.data != null) {
                    baseView.getSuppliersListSuccess(o.data);
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

    public void complaint(String id, String content) {
        addDisposite(apiService.complaint(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), id, content), new BaseObserver<BaseModel<SullierBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<SullierBean> o) {
                if (o != null && o.data != null) {
                    baseView.compliantSuccess(o.info);
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
