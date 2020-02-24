package com.pomelo.searchcustomer.basemvp;

import java.io.Serializable;

/**
 * Created by wanghaoxiang on 2019-07-20.
 * 所有的数据模型基类
 */

public class BaseModel<T> implements Serializable {
    public int code;
    public String info;
    public String utoken;
    public T data;
}
