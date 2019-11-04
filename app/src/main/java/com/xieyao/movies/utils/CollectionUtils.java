package com.xieyao.movies.utils;

import androidx.annotation.Nullable;

import java.util.Collection;

/**
 * Created by xieyao on 2019-10-20.
 */
public class CollectionUtils {

    public static boolean isEmpty(@Nullable Collection collection) {
        return (null == collection || collection.size() == 0) ? true : false;
    }

}
