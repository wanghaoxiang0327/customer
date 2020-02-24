package com.pomelo.searchcustomer.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;


import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.pomelo.searchcustomer.R;

/**
 * Created by wanghaoxiang on 2019-07-21.
 */

public class CommonLoadingDialog extends Dialog {
    private SpinKitView spin_kit;
    public static CommonLoadingDialog loadingDialog = null;

    public CommonLoadingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common_loading);
        spin_kit = findViewById(R.id.spin_kit);
        Sprite doubleBounce = new WanderingCubes();
        spin_kit.setIndeterminateDrawable(doubleBounce);
    }

//    public static CommonLoadingDialog getInstance(Context context) {
//        if (!((Activity) context).isFinishing()) {
//            if (loadingDialog == null) {
//                synchronized (CommonLoadingDialog.class) {
//                    if (loadingDialog == null) {
//                        loadingDialog = new CommonLoadingDialog(context);
//                    }
//                }
//            }
//            return loadingDialog;
//        }
//        return null;
//    }
}
