package com.xieyao.movies.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.xieyao.movies.App;
import com.xieyao.movies.R;
import com.xieyao.movies.data.bean.ReviewItem;
import com.xieyao.movies.data.bean.TrailerItem;
import com.xieyao.movies.databinding.ItemReviewBinding;
import com.xieyao.movies.databinding.ItemTitleBinding;
import com.xieyao.movies.databinding.ItemTrailerBinding;
import com.xieyao.movies.detail.DetailItemClickListener;
import com.xieyao.movies.utils.CollectionUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieyao on 2019-10-10.
 * recyclerview adapter for movie detail trailers
 */
public class DetailListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TITLE = 1;
    private static final int TYPE_TRAILER = TYPE_TITLE + 1;
    private static final int TYPE_REVIEW = TYPE_TRAILER + 1;

    private WeakReference<Fragment> mFragmentRef;
    private DetailItemClickListener mListener;
    private List<TrailerItem> mTrailerData = new ArrayList<>();
    private List<ReviewItem> mReviewData = new ArrayList<>();
    private List<Object> mData = new ArrayList<>();

    public DetailListAdapter(Fragment context) {
        this.mFragmentRef = new WeakReference<>(context);
        this.mListener = new DetailItemClickListener(context.getActivity());
    }

    public void setTrailerData(List<TrailerItem> data) {
        if (null == data) {
            data = new ArrayList<>();
        }
        this.mTrailerData = data;
        setUpData();
    }

    public void setReviewData(List<ReviewItem> data) {
        if (null == data) {
            data = new ArrayList<>();
        }
        this.mReviewData = data;
        setUpData();
    }

    private void setUpData() {
        mData = new ArrayList<>();
        if (!mTrailerData.isEmpty()) {
            mData.add(App.getInstance().getApplicationContext().getString(R.string.trailers));
            mData.addAll(mTrailerData);
        }
        if (!mReviewData.isEmpty()) {
            mData.add(App.getInstance().getApplicationContext().getString(R.string.reviews));
            mData.addAll(mReviewData);
        }
        notifyDataSetChanged();
    }

    public int getReviewStartPosition() {
        if (CollectionUtils.isEmpty(mData)) {
            return 0;
        }
        for (int i = 0; i < mData.size(); i++) {
            Object obj = mData.get(i);
            if (obj instanceof ReviewItem) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        Object obj = !CollectionUtils.isEmpty(mData) && mData.size() > position ? mData.get(position) : null;
        if (null != obj) {
            if (obj instanceof TrailerItem) {
                return TYPE_TRAILER;
            } else if (obj instanceof ReviewItem) {
                return TYPE_REVIEW;
            } else {
                return TYPE_TITLE;
            }
        } else {
            return super.getItemViewType(position);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TRAILER: {
                ItemTrailerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mFragmentRef.get().getContext()), R.layout.item_trailer, parent, false);
                binding.setListener(mListener);
                return new DetailListAdapter.TrailerViewHolder(binding);
            }
            case TYPE_REVIEW: {
                ItemReviewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mFragmentRef.get().getContext()), R.layout.item_review, parent, false);
                binding.setListener(mListener);
                return new DetailListAdapter.ReviewViewHolder(binding);
            }
            default: {
                ItemTitleBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mFragmentRef.get().getContext()), R.layout.item_title, parent, false);
                return new DetailListAdapter.TitleViewHolder(binding);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = position < mData.size() ? mData.get(position) : null;
        if (null != item) {
            if (item instanceof String && holder instanceof DetailListAdapter.TitleViewHolder) {
                ((TitleViewHolder) holder).mBinding.setTitle((String) item);
                ((TitleViewHolder) holder).mBinding.executePendingBindings();
            } else if (item instanceof TrailerItem && holder instanceof DetailListAdapter.TrailerViewHolder) {
                ((TrailerViewHolder) holder).mBinding.setTrailer((TrailerItem) item);
                ((TrailerViewHolder) holder).mBinding.executePendingBindings();
            } else if (item instanceof ReviewItem && holder instanceof DetailListAdapter.ReviewViewHolder) {
                ((ReviewViewHolder) holder).mBinding.setReview((ReviewItem) item);
                ((ReviewViewHolder) holder).mBinding.executePendingBindings();
            }
        }
    }

    @Override
    public int getItemCount() {
        return null != mData ? mData.size() : 0;
    }

    public static class TitleViewHolder extends RecyclerView.ViewHolder {

        ItemTitleBinding mBinding;

        public TitleViewHolder(ItemTitleBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    public static class TrailerViewHolder extends RecyclerView.ViewHolder {

        ItemTrailerBinding mBinding;

        public TrailerViewHolder(ItemTrailerBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }


    public static class ReviewViewHolder extends RecyclerView.ViewHolder {

        public ItemReviewBinding mBinding;

        public ReviewViewHolder(ItemReviewBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

}
