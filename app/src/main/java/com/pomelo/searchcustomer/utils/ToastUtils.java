package com.pomelo.searchcustomer.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtils {

    private static Toast toast = null;
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static Object synObj = new Object();

    /**
     * 在屏幕中间显示Toast的方法
     *
     * @param context 上下文对象
     * @param content 显示的内容
     */
    public static void showMiddleToast(Context context, String content) {
        if (null == toast) {
            toast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * 在屏幕中间显示Toast的方法
     *
     * @param context  上下文对象
     * @param stringId 显示的内容
     */
    public static void showMiddleToast(Context context, int stringId) {
        if (null == toast) {
            toast = Toast.makeText(context.getApplicationContext(), stringId, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(stringId);
        }
        toast.show();
    }

    /**
     * 显示Toast方法  -- 长时间
     *
     * @param context
     * @param content
     */
    public static void showMiddleLengthToast(Context context, String content) {
        if (null == toast) {
            toast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * 共用的显示Toast的方法--一般时间
     *
     * @param context 上下文对象
     * @param content 显示的内容
     */
    public static void showNormalToast(Context context, String content) {
        if (null == toast) {
            toast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * 共用的显示Toast的方法--长时间
     *
     * @param context 上下文对象
     * @param content 显示的内容
     */
    public static void showLongToast(Context context, String content) {
        if (null == toast) {
            toast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_LONG);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    /**
     * 共用的显示Toast的方法--一般时间
     *
     * @param context 上下文对象
     * @param content 显示的内容
     */
    public static void showBottomToast(Context context, String content) {
        if (null == toast) {
            toast = Toast.makeText(context.getApplicationContext(), content, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    public static void showMessage(Context context, final String msg) {
        showMessage(context, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 根据设置的文本显示
     *
     * @param msg
     */
    public static void showMessage(Context context, final int msg) {
        showMessage(context, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示一个文本并且设置时长
     *
     * @param msg
     * @param len
     */
    public static void showMessage(Context context, final CharSequence msg, final int len) {
        if (msg == null || msg.equals("")) {
            Log.i("ToastUtil", "[ToastUtil] response message is null.");
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (synObj) { //加上同步是为了每个toast只要有机会显示出来
                    if (toast != null) {
                        //toast.cancel();
                        toast.setText(msg);
                        toast.setDuration(len);
                    } else {
                        toast = Toast.makeText(context, msg, len);
                    }
                    toast.show();
                }
            }
        });
    }

    /**
     * 资源文件方式显示文本
     *
     * @param msg
     * @param len
     */
    public static void showMessage(Context context, final int msg, final int len) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (synObj) {
                    if (toast != null) {
                        //toast.cancel();
                        toast.setText(msg);
                        toast.setDuration(len);
                    } else {
                        toast = Toast.makeText(context, msg, len);
                    }
                    toast.show();
                }
            }
        });
    }
}
