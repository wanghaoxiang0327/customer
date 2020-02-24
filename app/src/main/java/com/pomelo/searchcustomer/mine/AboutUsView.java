package com.pomelo.searchcustomer.mine;

import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.AboutUsBean;

import java.util.List;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface AboutUsView extends BaseView {
    void linkusSuccess(List<AboutUsBean> list);
}
