package com.pomelo.searchcustomer.bean;

import java.util.List;

/**
 * Created by wanghaoxiang on 2020-01-14.
 */

public class IndestryPickBean extends BaseBean {
    public DataBean data;

    public static class DataBean {
        public List<IndustryBean> industry;
    }
}
