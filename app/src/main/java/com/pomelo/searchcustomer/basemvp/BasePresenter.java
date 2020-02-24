package com.pomelo.searchcustomer.basemvp;


import com.pomelo.searchcustomer.network.ApiRetrofit;
import com.pomelo.searchcustomer.network.ApiService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wanghaoxiang on 2019-07-20.
 * 控制层基类
 */

public class BasePresenter<V extends BaseView> {
    private CompositeDisposable compositeDisposable;
    public V baseView;
    protected ApiService apiService = null;

    public BasePresenter(V baseView) {
        this.baseView = baseView;
        apiService = ApiRetrofit.getInstance().getApiService();
    }

    /**
     * 解除绑定
     */
    public void detachView() {
        baseView = null;
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    /**
     * 返回baseview
     *
     * @return
     */
    public V getBaseView() {
        return baseView;
    }

    /**
     * 添加到队列中
     *
     * @param observable   被观察者
     * @param baseObserver 观察者
     */
    public void addDisposite(Observable<?> observable, BaseObserver baseObserver) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(baseObserver));
    }
}
