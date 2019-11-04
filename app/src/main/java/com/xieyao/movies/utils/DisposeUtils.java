package com.xieyao.movies.utils;

import io.reactivex.disposables.Disposable;

/**
 * Created by xieyao on 2019-10-12.
 */
public class DisposeUtils {

    public static void dispose(Disposable... disposables) {
        if (null == disposables) {
            return;
        }
        for (Disposable disposable : disposables) {
            if (null != disposable && !disposable.isDisposed()) {
                try {
                    disposable.dispose();
                } catch (Exception e) {
                }
            }
        }
    }

}
