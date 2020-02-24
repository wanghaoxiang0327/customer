package com.pomelo.searchcustomer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanghaoxiang on 2020-01-13.
 */

public class ShopListBean implements Serializable {
    public List<Shop> list;
    public int count;

    public class Shop implements Serializable {
        public String id;
        public String store_images;
        public String store_name;
        public String mobile;
        public String store_desc;
        public String uid;
    }

}
