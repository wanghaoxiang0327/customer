package com.pomelo.searchcustomer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanghaoxiang on 2020-01-16.
 */

public class MyCollectionBean implements Serializable {
    public List<Data> data;
    public int count;

    public class Data implements Serializable {
        public String company_name;
        public String company_address;
        public String company_phone;
    }
}
