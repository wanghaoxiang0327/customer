package com.pomelo.searchcustomer.main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.gyf.immersionbar.ImmersionBar;
import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wanghaoxiang on 2020-01-18.
 */

public class WelcomeActiviy extends BaseMVPActivity<WelcomePresenter> implements WelcomeView {
    @Override
    protected WelcomePresenter createPresenter() {
        return new WelcomePresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
        ImmersionBar.with(this).reset().fitsSystemWindows(false).init();
        setImmersionBar(false);
    }

    @Override
    public void initData() {
        super.initData();
        //先进行倒计时
        countDownTimer();
    }

    private void countDownTimer() {
        Flowable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(aLong -> {
                    //倒计时完成请求权限
                    requestPermisson();
                });
    }

    private void requestPermisson() {
        //请求读取电话权限
        SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.READ_PHONE_STATE, new CheckRequestPermissionListener() {
            @Override
            public void onPermissionOk(Permission permission) {
                if (isLogin) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }

            @Override
            public void onPermissionDenied(Permission permission) {
                if (permission.shouldRationale) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("提示")
                            .setMessage("如果你拒绝了权限，你将无法使用该软件，请点击授予权限")
                            .setPositiveButton("授予", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    requestPermisson();
                                }
                            }).create().show();
                } else {
                    String permissionDesc = permission.getPermissionNameDesc();
                    new AlertDialog.Builder(mContext)
                            .setTitle("提示")
                            .setMessage(permissionDesc + "异常，请前往设置－>权限管理，打开" + permissionDesc + "。")
                            .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //去设置页
                                    SoulPermission.getInstance().goPermissionSettings();
                                }
                            }).create().show();
                }
            }
        });
    }
}
