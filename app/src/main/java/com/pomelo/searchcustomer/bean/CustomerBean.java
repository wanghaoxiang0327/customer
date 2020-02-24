package com.pomelo.searchcustomer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanghaoxiang on 2020-01-16.
 */

public class CustomerBean implements Serializable {
    public List<Markers> markers;
    public int count;

    public class Markers implements Serializable {
        public String id;
        public double longitude;
        public double latitude;
        public String title;
        public String name;
        public String address;
        public String tel;
    }

}
