package com.hbrb.jiyun.mvp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "跳转react-native页面成功", Toast.LENGTH_SHORT).show();
        abortBroadcast();
    }
}
