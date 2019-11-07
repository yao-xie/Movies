package com.xieyao.movies.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.xieyao.movies.MainActivity;
import com.xieyao.movies.R;
import com.xieyao.movies.data.bean.MovieItem;
import com.xieyao.movies.databinding.ItemMovieBinding;
import com.xieyao.movies.detail.DetailFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieyao on 2019-10-10.
 * recyclerview adapter for movie list
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private WeakReference<Fragment> mFragmentRef;
    private List<MovieItem> mData = new ArrayList<>();
    private MovieItemClickListener mListener;

    public MovieListAdapter(Fragment fragment) {
        this.mFragmentRef = new WeakReference<>(fragment);
        mListener = new MovieItemClickListener(fragment.getActivity());
    }

    public void setData(List<MovieItem> data) {
        if (null != data) {
            this.mData = data;
        }
        notifyDataSetChanged();
    }

    public List<MovieItem> getData() {
        return mData;
    }

    @NonNull
    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mFragmentRef.get().getContext()), R.layout.item_movie, parent, false);
        binding.setListener(mListener);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            MovieItem movieItem = mData.get(position);
            holder.mBinding.setMovie(movieItem);
            holder.mBinding.executePendingBindings();
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return null != mData ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ItemMovieBinding mBinding;

        public ViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }


    public static class MovieItemClickListener {

        private WeakReference<Activity> mActivityRef;

        public MovieItemClickListener(Activity activity) {
            this.mActivityRef = new WeakReference<>(activity);
        }

        public void onMovieItemClicked(View imageView, MovieItem movieItem) {
            try {
                DetailFragment detailFragment = DetailFragment.newInstance(movieItem.getId());
                ((MainActivity) mActivityRef.get()).replaceFragment(detailFragment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
