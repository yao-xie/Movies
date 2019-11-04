package com.xieyao.movies.utils;

import android.widget.Toast;

import com.xieyao.movies.App;

/**
 * Created by xieyao on 2019-10-12.
 */
public class ToastUtils {

    public static void toast(Object res) {
        if (res instanceof CharSequence) {
            Toast.makeText(App.getInstance(), (CharSequence) res, Toast.LENGTH_LONG).show();
        } else if (res instanceof Integer) {
            Toast.makeText(App.getInstance(), (Integer) res, Toast.LENGTH_LONG).show();
        }
    }

}
