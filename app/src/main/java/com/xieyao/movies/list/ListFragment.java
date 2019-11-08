package com.xieyao.movies.list;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.xieyao.movies.ListBinding;
import com.xieyao.movies.R;
import com.xieyao.movies.ViewModelFactory;
import com.xieyao.movies.adapter.MovieListAdapter;
import com.xieyao.movies.base.BaseFragment;
import com.xieyao.movies.utils.ConfigUtils;

import java.lang.ref.WeakReference;

/**
 * Created by xieyao on 2019-10-10.
 * fragment for list
 */
public class ListFragment extends BaseFragment {

    private ListViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private FlexboxLayoutManager mLayoutManager;

    public static ListFragment newInstance() {
        return new ListFragment();
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
            mViewModel.retrieveFavoriteMovies(getActivity());
            mViewModel.refreshMovies();
        }
        return mRootView;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, int orientation) {
        ListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        if (null == mViewModel) {
            mViewModel = obtainViewModel();
        }
        binding.setLifecycleOwner(getActivity());
        binding.setViewModel(mViewModel);
        View view = binding.getRoot();
        initRecyclerView(view);
        return view;
    }

    public ListViewModel obtainViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(getActivity(), factory).get(ListViewModel.class);
    }

    private void initRecyclerView(View root) {
        mRecyclerView = root.findViewById(R.id.movies_recyclerview);
        mLayoutManager = new FlexboxLayoutManager(getContext());
        mLayoutManager.setFlexWrap(FlexWrap.WRAP);
        mLayoutManager.setFlexDirection(FlexDirection.ROW);
        mLayoutManager.setAlignItems(AlignItems.STRETCH);
        mLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        mRecyclerView.setLayoutManager(mLayoutManager);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels;
        float xdpi = displayMetrics.xdpi;
        int divide = Math.round(screenWidth / xdpi);
        int width = Math.round(screenWidth / divide);

        MovieListAdapter mAdapter = new MovieListAdapter(this, width);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new OnScrollListener(mViewModel));
    }

    private int getWidth(int x, int y, int orientation) {
        return orientation == Configuration.ORIENTATION_PORTRAIT ? x : y;
    }

    private int getHeight(int x, int y, int orientation) {
        return orientation == Configuration.ORIENTATION_PORTRAIT ? y : x;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateTitle();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular_movies:
            case R.id.action_top_rated_movies:
            case R.id.action_favorite_movies:
                ConfigUtils.setListMode(item.getItemId());
                updateTitle();
                mViewModel.refreshMovies();
                if (null != mRecyclerView) {
                    mRecyclerView.scrollToPosition(0);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateTitle() {
        int id = ConfigUtils.getListMode();
        switch (id) {
            case R.id.action_popular_movies:
                setTitle(R.string.action_popular_movies);
                break;
            case R.id.action_top_rated_movies:
                setTitle(R.string.action_top_rated_movies);
                break;
            case R.id.action_favorite_movies:
                setTitle(R.string.action_favorite_movies);
                break;
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
