package com.xieyao.movies.detail;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xieyao.movies.DetailBinding;
import com.xieyao.movies.DetailBindingLand;
import com.xieyao.movies.R;
import com.xieyao.movies.ViewModelFactory;
import com.xieyao.movies.adapter.DetailListAdapter;
import com.xieyao.movies.base.BaseFragment;
import com.xieyao.movies.widget.TrailerItemDecoration;

/**
 * Created by xieyao on 2019-10-20.
 * fragment for detail
 */
public class DetailFragment extends BaseFragment {

    private static final String MOVIEID = "movieId";

    private DetailViewModel mViewModel;

    private int mMovieId;

    private NestedScrollView mScrollView;
    private RecyclerView mRecyclerView;
    private DetailListAdapter mAdapter;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(int movieId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(MOVIEID, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (null == savedInstanceState) {
            mMovieId = getArguments().getInt(MOVIEID);
        } else {
            mMovieId = savedInstanceState.getInt(MOVIEID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = initView(inflater, container, getResources().getConfiguration().orientation);
        if (null == savedInstanceState) {
            mViewModel.start(mMovieId);
            mViewModel.fetchRemoteData();
        }
        return mRootView;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, int orientation) {
        View root = null;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            DetailBindingLand binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_land, container, false);
            binding.setLifecycleOwner(getActivity());
            if (null == mViewModel) {
                mViewModel = obtainViewModel();
            }
            binding.setViewModel(mViewModel);
            root = binding.getRoot();
        } else {
            DetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
            binding.setLifecycleOwner(getActivity());
            if (null == mViewModel) {
                mViewModel = obtainViewModel();
            }
            binding.setViewModel(mViewModel);
            root = binding.getRoot();
        }
        setTitle(R.string.movie_detail);
        initAnchorViews(root);
        initDetailList(root);
        return root;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        enableBackBtn(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        enableBackBtn(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        enableBackBtn(false);
    }

    /**
     * init the anchors
     * to scroll to the position of trailers and reviews
     *
     * @param root
     */
    private void initAnchorViews(View root) {
        mScrollView = root.findViewById(R.id.scrollview);
    }

    public DetailViewModel obtainViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(getActivity(), factory).get(DetailViewModel.class);
    }

    private void initDetailList(View rootView) {
        mRecyclerView = rootView.findViewById(R.id.detail_recyclerview);
        setLayoutManager(mRecyclerView);
        mAdapter = new DetailListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new TrailerItemDecoration(
                getContext().getDrawable(R.drawable.divider_light),
                getResources().getDimensionPixelSize(R.dimen.trailers_divider_height)));
    }

    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            switch (item.getItemId()) {
                case R.id.action_trailers:
                    if (null != mScrollView && null != mRecyclerView) {
                        mScrollView.smoothScrollTo(0, mRecyclerView.getTop());
                    }
                    return true;
                case R.id.action_reviews:
                    if (null != mScrollView && null != mRecyclerView && null != mAdapter) {
                        int position = mAdapter.getReviewStartPosition();
                        View itemView = mRecyclerView.getChildAt(position);
                        if (null != itemView) {
                            mScrollView.smoothScrollTo(0, itemView.getTop());
                        }
                    }
                    return true;
                default:
                    return super.onOptionsItemSelected(item);

            }
        } catch (Exception e) {
            return super.onOptionsItemSelected(item);
        }
    }

}
