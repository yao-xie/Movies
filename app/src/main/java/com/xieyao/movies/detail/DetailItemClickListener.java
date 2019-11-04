package com.xieyao.movies.detail;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.xieyao.movies.data.bean.ReviewItem;
import com.xieyao.movies.data.bean.TrailerItem;
import com.xieyao.movies.utils.ApiUtils;

import java.lang.ref.WeakReference;

public class DetailItemClickListener {

    private WeakReference<Activity> activityWeakReference;

    public DetailItemClickListener(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }

    public void onItemClick(@Nullable Object item) {
        try {
            if (item == null) {
                return;
            }
            String path = null;
            if (item instanceof TrailerItem) {
                path = ApiUtils.getYoutube(((TrailerItem) item).getKey());
            } else if (item instanceof ReviewItem) {
                path = ((ReviewItem) item).getUrl();
            }
            if (TextUtils.isEmpty(path)) {
                return;
            }
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
            activityWeakReference.get().startActivity(webIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
