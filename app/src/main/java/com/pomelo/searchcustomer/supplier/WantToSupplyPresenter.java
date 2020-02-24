package com.pomelo.searchcustomer.supplier;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.SupplierClassBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.mine.AboutUsView;
import com.pomelo.searchcustomer.utils.ToastUtils;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class WantToSupplyPresenter extends BasePresenter<WantToSupplyView> {
    public WantToSupplyPresenter(WantToSupplyView baseView) {
        super(baseView);
    }

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

    public void addSupply(String title, String content, String mobile, Map<String, RequestBody> file) {
        addDisposite(apiService.addSupplier(Contanst.TOKEN, Contanst.VERSION, title, content, mobile, "1", "1", file), new BaseObserver<BaseModel>(baseView) {
            @Override
            public void onSuccess(BaseModel o) {
                baseView.addSupplySuccess();
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
