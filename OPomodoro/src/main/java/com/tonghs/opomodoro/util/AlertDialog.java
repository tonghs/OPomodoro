package com.tonghs.opomodoro.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.tonghs.opomodoro.R;

/**
 * Created by Administrator on 13-12-19.
 */
public class AlertDialog extends Dialog {
    int layoutRes;//布局文件
    Context context;
    public AlertDialog(Context context) {
        super(context);
        this.context = context;
        this.layoutRes = R.layout.alert_dialog;
        setTitle(context.getString(R.string.welldone));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layoutRes);
    }
}
