package com.xieyao.movies.adapter;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.squareup.picasso.Picasso;
import com.xieyao.movies.data.bean.MovieItem;
import com.xieyao.movies.data.bean.ReviewItem;
import com.xieyao.movies.data.bean.TrailerItem;

import java.util.List;

/**
 * Created by xieyao on 2019-10-17.
 * data binding adapter
 */
public class BindAdapter {

    //for list
    @SuppressWarnings("unchecked")
    @BindingAdapter("app:movieItems")
    public static void refreshMovieItems(RecyclerView recyclerView, List<MovieItem> movieItems) {
        MovieListAdapter adapter = (MovieListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setData(movieItems);
        }
    }

    @BindingAdapter("app:onRefreshListener")
    public static void setOnRefreshListener(SwipeRefreshLayout refreshLayout, SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        if (null != refreshLayout) {
            refreshLayout.setOnRefreshListener(onRefreshListener);
        }
    }

    @BindingAdapter("app:setRefreshing")
    public static void setRefreshing(SwipeRefreshLayout refreshLayout, boolean refresh) {
        if (null != refreshLayout) {
            refreshLayout.setRefreshing(refresh);
        }
    }

    @BindingAdapter("app:setScrollPosition")
    public static void setScrollPosition(RecyclerView recyclerView, int position) {
        if (position < 0) {
            return;
        }
        if (null != recyclerView && null != recyclerView.getLayoutManager() && recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            ((GridLayoutManager) recyclerView.getLayoutManager()).scrollToPosition(position);
        }
    }

    @BindingAdapter(value = {"picasso:path", "picasso:placeholder", "picasso:error"}, requireAll = false)
    public static void setImageUrl(ImageView imageView, String path, Drawable placeHolderRes, Drawable errorRes) {
        Picasso.get()
                .load(path)
                .placeholder(placeHolderRes)
                .error(errorRes)
                .into(imageView);
    }

    //for detail
    @BindingAdapter("app:backDropImageUrl4ML")
    public static void setBackdropImageTransitionAnimation(final MotionLayout motionLayout, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        motionLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                motionLayout.transitionToEnd();
            }
        }, 800l);
    }

    @BindingAdapter("app:favorite")
    public static void setFavorite(ImageButton view, int favorite) {
        view.setSelected(1 == favorite ? true : false);
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:trailerItems")
    public static void setTrailerItems(RecyclerView recyclerView, List<TrailerItem> trailerItems) {
        DetailListAdapter adapter = (DetailListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setData(trailerItems);
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:reviewItems")
    public static void setReviewItems(RecyclerView recyclerView, List<ReviewItem> reviewItems) {
        ReviewListAdapter adapter = (ReviewListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setData(reviewItems);
        }
    }

}
