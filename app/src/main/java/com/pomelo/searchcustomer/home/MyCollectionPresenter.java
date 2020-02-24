package com.pomelo.searchcustomer.home;


import com.pomelo.searchcustomer.basemvp.BaseModel;
import com.pomelo.searchcustomer.basemvp.BaseObserver;
import com.pomelo.searchcustomer.basemvp.BasePresenter;
import com.pomelo.searchcustomer.bean.CardSquareBean;
import com.pomelo.searchcustomer.bean.MyCollectionBean;
import com.pomelo.searchcustomer.bean.MyCollectionInfoBean;
import com.pomelo.searchcustomer.contract.Contanst;
import com.pomelo.searchcustomer.mine.AboutUsView;
import com.pomelo.searchcustomer.utils.PreferencesUtil;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public class MyCollectionPresenter extends BasePresenter<MyCollectionView> {
    public MyCollectionPresenter(MyCollectionView baseView) {
        super(baseView);
    }

    public void getCollectionInfo() {
        addDisposite(apiService.getMyCollectionInfo(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken")), new BaseObserver<BaseModel<MyCollectionInfoBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<MyCollectionInfoBean> o) {
                if (o != null && o.data != null) {
                    baseView.getMyCollectionInfo(o.data);
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

    public void seachCollection(String type, String content, String page) {
        addDisposite(apiService.getMyCollectionData(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), type, content, page, "10"), new BaseObserver<BaseModel<MyCollectionBean>>(baseView) {
            @Override
            public void onSuccess(BaseModel<MyCollectionBean> o) {
                if (o != null && o.data != null) {
                    baseView.getCollectionData(o.data);
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

    public void deleteCollectionData(String type, String content) {
        addDisposite(apiService.deleteCollectionData(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), type, content), new BaseObserver<BaseModel>(baseView) {
            @Override
            public void onSuccess(BaseModel o) {
                baseView.deleteSuccess();
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }

    public void exportData(String type, String content) {
        addDisposite(apiService.exportCollectionData(Contanst.TOKEN, Contanst.VERSION, PreferencesUtil.getString("utoken"), type, content), new BaseObserver<BaseModel<String>>(baseView) {
            @Override
            public void onSuccess(BaseModel<String> o) {
                baseView.exportDatSuccess(o.data);
            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);
            }
        });
    }
}
