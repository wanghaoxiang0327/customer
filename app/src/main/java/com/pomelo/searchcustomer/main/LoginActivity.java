package com.pomelo.searchcustomer.main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.utils.PhoneUtils;
import com.pomelo.searchcustomer.utils.ToastUtils;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-11.
 */

public class LoginActivity extends BaseMVPActivity<LoginPresenter> implements LoginView {
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_password)
    EditText etPassword;
    String phonenumber, password;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        requestPermisson();
    }


    private void requestPermisson() {
        //请求读取电话权限
        SoulPermission.getInstance().checkAndRequestPermission(Manifest.permission.READ_PHONE_STATE, new CheckRequestPermissionListener() {
            @Override
            public void onPermissionOk(Permission permission) {
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

    @OnClick({R.id.tv_register, R.id.tv_forget_pwd, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register: {
                Intent intent = new Intent(mContext, RegisterActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.tv_forget_pwd:
                break;
            case R.id.tv_login: {
                login();
            }
            break;
        }
    }

    private void login() {
        phonenumber = etPhoneNumber.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(phonenumber)) {
            ToastUtils.showMessage(mContext, "请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showMessage(mContext, "请输入密码");
            return;
        }
        mPresenter.login(password, phonenumber, PhoneUtils.getIMEI(mContext));
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
