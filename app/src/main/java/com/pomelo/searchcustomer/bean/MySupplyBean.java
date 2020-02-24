package com.pomelo.searchcustomer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanghaoxiang on 2020-01-12.
 */

public class MySupplyBean implements Serializable {
    public List<SupplyBaan> datum;

    public class SupplyBaan implements Serializable {
        public String id;
        public String title;
        public String content;
        public String images;
        public String createtime;
        public String click;
        public String mobile;
        public String status;
        public String statusInfo;
    }
}
