package com.xieyao.movies.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.xieyao.movies.R;
import com.xieyao.movies.data.bean.ReviewItem;
import com.xieyao.movies.databinding.ItemReviewListBinding;
import com.xieyao.movies.detail.DetailItemClickListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieyao on 2019-10-10.
 * recyclerview adapter for movie detail reviews
 */
public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {

    private WeakReference<Fragment> mFragmentRef;
    private DetailItemClickListener mListener;
    private List<ReviewItem> mData = new ArrayList<>();

    public ReviewListAdapter(Fragment context) {
        this.mFragmentRef = new WeakReference<>(context);
        this.mListener = new DetailItemClickListener(context.getActivity());
    }

    public void setData(List<ReviewItem> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReviewListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mFragmentRef.get().getContext()), R.layout.item_review_list, parent, false);
        binding.setListener(mListener);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        try {
            final ReviewItem reviewItem = position < mData.size() ? mData.get(position) : null;
            holder.mBinding.setReview(reviewItem);
            holder.mBinding.executePendingBindings();
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return null != mData ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ItemReviewListBinding mBinding;

        public ViewHolder(ItemReviewListBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

}
