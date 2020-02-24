package com.pomelo.searchcustomer.home;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.MyVisitinCardBean;
import com.pomelo.searchcustomer.bean.ShopListBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.mine.AboutUsView;

import java.util.List;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class ShopListPresenter extends BasePresenter<ShopListView> {
    public ShopListPresenter(ShopListView baseView) {
        super(baseView);
    }

    public void getShopList(String page, String search) {
        addDisposite(apiService.getShopList(Contanst.TOKEN, Contanst.VERSION, page, "10", search), new BaseObserver<BaseModel<ShopListBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<ShopListBean> o) {
                if (o != null && o.data != null) {
                    baseView.getShopListSuccess(o.data);
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
