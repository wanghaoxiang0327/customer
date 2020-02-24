package com.pomelo.searchcustomer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanghaoxiang on 2020-01-12.
 */

public class SupplierClassBean implements Serializable {
    public List<SupplierClass> demand_nav;
    public List<SupplierClass> supply_arr;

    public class SupplierClass implements Serializable {
        public String id;
        public String name;
        public String createtime;
        public String image;
        public String type;
    }
}
