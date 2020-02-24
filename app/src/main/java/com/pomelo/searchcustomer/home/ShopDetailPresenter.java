package com.pomelo.searchcustomer.home;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.ShopDetailBean;
import com.pomelo.searchcustomer.bean.SupplierClassBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.mine.AboutUsView;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class ShopDetailPresenter extends BasePresenter<ShopDetailView> {
    public ShopDetailPresenter(ShopDetailView baseView) {
        super(baseView);
    }

    public void getShopDetail(String id) {
        addDisposite(apiService.getShopDetail(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), id), new BaseObserver<BaseModel<ShopDetailBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<ShopDetailBean> o) {
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
