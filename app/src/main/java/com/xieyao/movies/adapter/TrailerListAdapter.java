package com.xieyao.movies.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.xieyao.movies.R;
import com.xieyao.movies.data.bean.TrailerItem;
import com.xieyao.movies.databinding.ItemTrailerListBinding;
import com.xieyao.movies.detail.DetailItemClickListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieyao on 2019-10-10.
 * recyclerview adapter for movie detail trailers
 */
public class TrailerListAdapter extends RecyclerView.Adapter<TrailerListAdapter.ViewHolder> {

    private WeakReference<Fragment> mFragmentRef;
    private DetailItemClickListener mListener;
    private List<TrailerItem> mData = new ArrayList<>();

    public TrailerListAdapter(Fragment context) {
        this.mFragmentRef = new WeakReference<>(context);
        this.mListener = new DetailItemClickListener(context.getActivity());
    }

    public void setData(List<TrailerItem> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTrailerListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mFragmentRef.get().getContext()), R.layout.item_trailer_list, parent, false);
        binding.setListener(mListener);
        return new TrailerListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            final TrailerItem trailerItem = position < mData.size() ? mData.get(position) : null;
            holder.mBinding.setTrailer(trailerItem);
            holder.mBinding.executePendingBindings();
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return null != mData ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ItemTrailerListBinding mBinding;

        public ViewHolder(ItemTrailerListBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }
}
