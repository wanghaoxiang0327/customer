package com.pomelo.searchcustomer.home;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.CardSquareBean;
import com.pomelo.searchcustomer.bean.CustomerBean;
import com.pomelo.searchcustomer.bean.ShopDetailBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.mine.AboutUsView;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

import java.util.List;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class SearchPresenter extends BasePresenter<SearchView> {
    public SearchPresenter(SearchView baseView) {
        super(baseView);
    }

    public void getSearchType() {
        addDisposite(apiService.getSearchType(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken")), new BaseObserver<BaseModel<List<String>>>(baseView) {
            @Override
            public void onSuccess(BaseModel<List<String>> o) {
                if (o != null && o.data != null) {
                    baseView.getSearchClassSuccess(o.data);
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

    public void search(String searchKey, String address, String id, String page, String length) {
        addDisposite(apiService.collectionData(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), searchKey, address, id, page, "50", length), new BaseObserver<BaseModel<CustomerBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<CustomerBean> o) {
                if (o != null && o.data != null) {
                    baseView.getcollectSuccess(o.data);
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
