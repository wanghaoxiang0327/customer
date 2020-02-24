package com.pomelo.searchcustomer.main;

import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.UserInfoBean;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface MainView extends BaseView {
    void getUserInfoSuccess(UserInfoBean userInfoBean);
}
