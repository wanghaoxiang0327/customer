package com.pomelo.searchcustomer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanghaoxiang on 2020-01-15.
 */

public class MyMessageBean implements Serializable {
    public List<Datum> datum;
    public int count;

    public class Datum implements Serializable {
        public String id;
        public String title;
        public String content;
        public String createtime;
        public String status;
        public String merchant_id;
        public String uid;

    }
}
