package com.pomelo.searchcustomer.utils;

/**
 * Created by wanghaoxiang on 2020-01-14.
 */

public class NumberUtils {
    /**
     * 向上取整
     */
    public static int upCeil(int page) {
        int c = page % 10 == 0 ? (page / 10) : (page / 10) + 1;
        return c;
    }
}
