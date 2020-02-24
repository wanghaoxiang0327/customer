package com.pomelo.searchcustomer.home;

import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.HelpCenterBean;

import java.util.List;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface HelpCenterView extends BaseView {
    void getHelpcenterSuccess(List<HelpCenterBean> helpCenterBeans);
}
