package com.xieyao.movies.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.xieyao.movies.data.bean.MovieItem;
import com.xieyao.movies.data.bean.ReviewItem;
import com.xieyao.movies.data.bean.TrailerItem;
import com.xieyao.movies.widget.GradientTransformation;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

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

    @BindingAdapter("app:selectTabPosition")
    public static void setSelectTabPosition(TabLayout tabLayout, int tabSelectPosition) {
        try {
            if (null != tabLayout) {
                TabLayout.Tab tab = tabLayout.getTabAt(tabSelectPosition);
                if (null != tab) {
                    tab.select();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    @BindingAdapter(value = {"picasso:path", "picasso:placeholder", "picasso:error", "picasso:gardientTransform"}, requireAll = false)
    public static void setImageUrl(ImageView imageView, String path, Drawable placeHolderRes, Drawable errorRes, boolean transformFlag) {
        RequestCreator creator = Picasso.get()
                .load(path)
                .config(Bitmap.Config.RGB_565)//to reduce memory usage
                .placeholder(placeHolderRes)
                .error(errorRes);
        if (transformFlag) {
            creator = creator.transform(new GradientTransformation());
        }
        creator.into(imageView);
    }

    @BindingAdapter("app:dynamicWidth")
    public static void setDynamicWidth(View view, Integer width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        view.setLayoutParams(layoutParams);
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
    public static void setFavorite(ExtendedFloatingActionButton button, int favorite) {
        button.setSelected(1 == favorite ? true : false);
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:trailerItems")
    public static void setTrailerItems(RecyclerView recyclerView, List<TrailerItem> trailerItems) {
        DetailListAdapter adapter = (DetailListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setTrailerData(trailerItems);
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:reviewItems")
    public static void setReviewItems(RecyclerView recyclerView, List<ReviewItem> reviewItems) {
        DetailListAdapter adapter = (DetailListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setReviewData(reviewItems);
        }
    }

}
