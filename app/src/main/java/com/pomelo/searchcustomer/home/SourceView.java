package com.pomelo.searchcustomer.home;

import com.pomelo.searchcustomer.basemvp.BaseView;
import com.pomelo.searchcustomer.bean.HomeIndexBean;

import java.util.List;

/**
 * Created by wanghaoxiang on 2019-07-18.
 */

public interface SourceView extends BaseView {
        void getSourceSuccess(List<HomeIndexBean.Source> sources);
}
