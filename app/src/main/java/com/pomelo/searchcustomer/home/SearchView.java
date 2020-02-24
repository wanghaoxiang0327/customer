package com.pomelo.searchcustomer.home;

import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.CustomerBean;

import java.util.List;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface SearchView extends BaseView {
    void getSearchClassSuccess(List<String> list);

    void getcollectSuccess(CustomerBean customerBean);
}
