package com.pomelo.searchcustomer.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanghaoxiang on 2020-01-13.
 */

public class ShopDetailBean implements Serializable {
    public String mobile;
    public String store_name;
    public String store_desc;
    public String store_content;
    public String uid;
    public List<String> store_images;
}
