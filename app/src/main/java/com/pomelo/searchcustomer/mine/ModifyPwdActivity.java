package com.pomelo.searchcustomer.mine;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pomelo.searchcustomer.R;
import com.pomelo.searchcustomer.basemvp.BaseMVPActivity;
import com.pomelo.searchcustomer.utils.PhoneUtils;
import com.pomelo.searchcustomer.utils.ToastUtils;
import com.pomelo.searchcustomer.utils.VerificationCountDownTimer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wanghaoxiang on 2020-01-08.
 */

public class ModifyPwdActivity extends BaseMVPActivity<ModifyPwdPresenter> implements ModifyPwdView {
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_confirm_new_pwd)
    EditText etConfirmNewPwd;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.tv_send_message_code)
    TextView tvSendMessageCode;
    @BindView(R.id.et_message_code)
    EditText etMessageCode;
    String pwd, truPwd, phone, code;
    VerificationCountDownTimer verificationCountDownTimer;

    @Override
    protected ModifyPwdPresenter createPresenter() {
        return new ModifyPwdPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_pwd;
    }

    @Override
    public void initView() {
        initCountDownTimer();
    }


    private void initCountDownTimer() {
        if (!VerificationCountDownTimer.FLAG_FIRST_IN && VerificationCountDownTimer.curMillis + 60000 > System.currentTimeMillis()) {
            setCountDownTimer(VerificationCountDownTimer.curMillis + 60000 - System.currentTimeMillis());
            verificationCountDownTimer.timerStart(false);
        } else {
            setCountDownTimer(60000);
        }
    }

    private void setCountDownTimer(final long countDonwTimer) {
        verificationCountDownTimer = new VerificationCountDownTimer(countDonwTimer, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                super.onTick(millisUntilFinished);
                if (tvSendMessageCode != null) {
                    tvSendMessageCode.setEnabled(false);
                    tvSendMessageCode.setText((millisUntilFinished / 1000) + "s");
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (tvSendMessageCode != null) {
                    tvSendMessageCode.setEnabled(true);
                    tvSendMessageCode.setText("获取验证码");
                }
                if (countDonwTimer != 60000) {
                    setCountDownTimer(60000);
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        verificationCountDownTimer.cancel();
        verificationCountDownTimer = null;
    }

    @OnClick({R.id.iv_back, R.id.tv_send_message_code, R.id.tv_modify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back: {
                finish();
            }
            break;
            case R.id.tv_send_message_code: {
                phone = etPhoneNumber.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showMessage(mContext, "请输入手机号");
                    return;
                }
                verificationCountDownTimer.timerStart(true);
                mPresenter.getMessageCode(phone);
            }
            break;
            case R.id.tv_modify: {
                if (isCheck()) {
                    mPresenter.modifyPwd(pwd, truPwd,code, phone, PhoneUtils.getIMEI(mContext));
                }
            }
            break;
        }
    }

    private boolean isCheck() {
        pwd = etNewPwd.getText().toString().trim();
        truPwd = etConfirmNewPwd.getText().toString().trim();
        phone = etPhoneNumber.getText().toString().trim();
        code = etMessageCode.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showMessage(mContext, "请输入密码");
            return false;
        }
        if (TextUtils.isEmpty(truPwd)) {
            ToastUtils.showMessage(mContext, "请确认密码");
            return false;
        }
        if (!pwd.equals(truPwd)) {
            ToastUtils.showMessage(mContext, "两次密码不一致");
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showMessage(mContext, "请输入手机号");
            return false;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtils.showMessage(mContext, "请输入验证码");
            return false;
        }
        return true;
    }

    @Override
    public void modifySuccess() {
        finish();
    }

    @Override
    public void sendSuccess() {
        ToastUtils.showMessage(mContext, "验证码发送成功");
    }
}
