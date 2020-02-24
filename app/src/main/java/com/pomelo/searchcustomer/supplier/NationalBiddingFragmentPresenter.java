package com.pomelo.searchcustomer.supplier;

import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.SupplierClassBean;
import com.pomelo.searchcustomer.contract.Contanst;

/**
 * Created by wanghaoxiang on 2020-01-07.
 */

public class NationalBiddingFragmentPresenter extends BasePresenter<NationalBiddingFragmentView> {
    public NationalBiddingFragmentPresenter(NationalBiddingFragmentView baseView) {
        super(baseView);
    }

    //获取供需库分类
    public void getSupplierClass() {
        addDisposite(apiService.getSpplierClass(Contanst.TOKEN, Contanst.VERSION), new BaseObserver<BaseModel<SupplierClassBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<SupplierClassBean> o) {
                if (o != null && o.data != null) {
                    baseView.getSupplierClassSuccess(o.data);
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
