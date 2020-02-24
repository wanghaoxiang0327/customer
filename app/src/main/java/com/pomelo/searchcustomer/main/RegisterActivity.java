package com.pomelo.searchcustomer.main;

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
 * Created by wanghaoxiang on 2020-01-11.
 */

public class RegisterActivity extends BaseMVPActivity<RegisterPresenter> implements RegisterView {
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.tv_confirm_pwd)
    EditText tvConfirmPwd;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.tv_send_message)
    TextView tvSendMessage;
    @BindView(R.id.et_message_code)
    EditText etMessageCode;
    String password, confirmPassword, messagecode, mobileNumber;
    VerificationCountDownTimer verificationCountDownTimer;

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        initCountDownTimer();
    }

    @OnClick({R.id.iv_back, R.id.tv_send_message, R.id.tv_modify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back: {
                finish();
            }
            break;
            case R.id.tv_send_message: {
                mobileNumber = etPhoneNumber.getText().toString().trim();
                if (TextUtils.isEmpty(mobileNumber)) {
                    ToastUtils.showMessage(mContext, "请输入手机号");
                    return;
                }
                verificationCountDownTimer.timerStart(true);
                mPresenter.getMessageCode(mobileNumber);
            }
            break;
            case R.id.tv_modify: {
                register();
            }
            break;
        }
    }

    private void register() {
        password = etPwd.getText().toString().trim();
        confirmPassword = tvConfirmPwd.getText().toString().trim();
        mobileNumber = etPhoneNumber.getText().toString().trim();
        messagecode = etMessageCode.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showMessage(mContext, "请输入密码");
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            ToastUtils.showMessage(mContext, "请输入确认密码");
            return;
        }

        if (TextUtils.isEmpty(mobileNumber)) {
            ToastUtils.showMessage(mContext, "请输入手机号");
            return;
        }

        if (TextUtils.isEmpty(messagecode)) {
            ToastUtils.showMessage(mContext, "请输入验证码");
            return;
        }
        if (!password.equals(confirmPassword)) {
            ToastUtils.showMessage(mContext, "输入的两次密码不一致");
            return;
        }
        mPresenter.register(password, mobileNumber, confirmPassword, messagecode, PhoneUtils.getIMEI(mContext));
    }

    @Override
    public void registerSuccess() {
        finish();
    }

    @Override
    public void getMessageCodeSuccess() {
        ToastUtils.showMessage(mContext, "验证码发送成功");
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
                if (tvSendMessage != null) {
                    tvSendMessage.setEnabled(false);
                    tvSendMessage.setText((millisUntilFinished / 1000) + "s");
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (tvSendMessage != null) {
                    tvSendMessage.setEnabled(true);
                    tvSendMessage.setText("获取验证码");
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
}
