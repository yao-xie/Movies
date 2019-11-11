package com.xieyao.movies.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xieyao.movies.ListBinding;
import com.xieyao.movies.R;
import com.xieyao.movies.ViewModelFactory;
import com.xieyao.movies.adapter.MovieListAdapter;
import com.xieyao.movies.base.BaseFragment;
import com.xieyao.movies.utils.ConfigUtils;
import com.xieyao.movies.utils.UIUtils;

import java.lang.ref.WeakReference;

/**
 * Created by xieyao on 2019-10-10.
 * fragment for list
 */
public class ListFragment extends BaseFragment {

    private ListViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private int mListMode;

    public ListFragment(int position) {
        super();
        this.mListMode = position;
    }

    public static ListFragment newInstance(int position) {
        return new ListFragment(position);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = initView(inflater, container, getResources().getConfiguration().orientation);
        if (mViewModel.isEmpty()) {
            if (mListMode == ConfigUtils.MODE_FAVORITE_MOVIES) {
                mViewModel.retrieveFavoriteMovies(getActivity());
            }
            mViewModel.refreshMovies();
            mViewModel.setListMode(mListMode);
        }
        return mRootView;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, int orientation) {
        ListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        if (null == mViewModel) {
            mViewModel = (ListViewModel) obtainViewModel(ListViewModel.class);
        }
        mViewModel.setListMode(mListMode);
        binding.setLifecycleOwner(getActivity());
        binding.setViewModel(mViewModel);
        View view = binding.getRoot();
        initRecyclerView(view);
        return view;
    }

    @Override
    protected ViewModel obtainViewModel(Class clazz) {
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(clazz);
    }

    private void initRecyclerView(View root) {
        mRecyclerView = root.findViewById(R.id.movies_recyclerview);
        int spanCount = UIUtils.getSpanCount();
        mLayoutManager = new GridLayoutManager(getContext(), spanCount);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        MovieListAdapter mAdapter = new MovieListAdapter(this, 0);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new OnScrollListener(mViewModel));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            if (null != mViewModel) {
                mViewModel.saveListPosition(mLayoutManager.findFirstVisibleItemPosition());
            }
        } catch (Exception e) {
        }
    }

    /**
     * OnScrollListener to trigger loadMore action
     */
    private static class OnScrollListener extends RecyclerView.OnScrollListener {

        private WeakReference<ListViewModel> mViewModelRef;

        public OnScrollListener(ListViewModel viewModel) {
            this.mViewModelRef = new WeakReference<>(viewModel);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            try {
                if (dy > 8) {
                    if (null != mViewModelRef && null != mViewModelRef.get()) {
                        mViewModelRef.get().saveListPosition(-1);
                    }
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                    RecyclerView.Adapter adapter = recyclerView.getAdapter();
                    if (null != gridLayoutManager && null != adapter) {
                        int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
                        int lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
                        int totalItemCount = adapter.getItemCount();
                        //when the remaining items cannot fill the whole screen, request more data
                        if ((totalItemCount - lastVisibleItemPosition) < (lastVisibleItemPosition - firstVisibleItemPosition)) {
                            if (null != mViewModelRef && null != mViewModelRef.get()) {
                                mViewModelRef.get().loadMoreMovies();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
