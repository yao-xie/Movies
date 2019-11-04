package com.xieyao.movies.detail;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xieyao.movies.DetailBinding;
import com.xieyao.movies.DetailBindingLand;
import com.xieyao.movies.R;
import com.xieyao.movies.ViewModelFactory;
import com.xieyao.movies.adapter.ReviewListAdapter;
import com.xieyao.movies.adapter.TrailerListAdapter;
import com.xieyao.movies.base.BaseFragment;
import com.xieyao.movies.utils.ToastUtils;
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
    private View mTrailersDivider, mReviewsDivider;
    private ReviewListAdapter mReviewAdapter;

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
        View root = null;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            DetailBindingLand binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_land, container, false);
            binding.setLifecycleOwner(getActivity());
            mViewModel = obtainViewModel();
            binding.setViewModel(mViewModel);
            root = binding.getRoot();
        } else {
            DetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
            binding.setLifecycleOwner(getActivity());
            mViewModel = obtainViewModel();
            binding.setViewModel(mViewModel);
            root = binding.getRoot();
        }

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.movie_detail);

        initAnchorViews(root);
        initTrailerList(root);
        initReviewList(root);

        if (null == savedInstanceState) {
            mViewModel.start(mMovieId);
            mViewModel.fetchRemoteData();
        }
        return root;
    }

    /**
     * init the anchors
     * to scroll to the position of trailers and reviews
     *
     * @param root
     */
    private void initAnchorViews(View root) {
        mScrollView = root.findViewById(R.id.scrollview);
        mTrailersDivider = root.findViewById(R.id.divider1);
        mReviewsDivider = root.findViewById(R.id.divider2);
    }

    public DetailViewModel obtainViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(getActivity(), factory).get(DetailViewModel.class);
    }

    private void initTrailerList(View rootView) {
        RecyclerView trailersRecyclerView = rootView.findViewById(R.id.trailers_recyclerview);
        setLayoutManager(trailersRecyclerView);
        TrailerListAdapter adapter = new TrailerListAdapter(this);
        trailersRecyclerView.setAdapter(adapter);
        trailersRecyclerView.addItemDecoration(new TrailerItemDecoration(
                getContext().getDrawable(R.drawable.divider_light),
                getResources().getDimensionPixelSize(R.dimen.trailers_divider_height)));
    }

    private void initReviewList(View rootView) {
        RecyclerView reviewsRecyclerView = rootView.findViewById(R.id.reviews_recyclerview);
        setLayoutManager(reviewsRecyclerView);
        mReviewAdapter = new ReviewListAdapter(this);
        reviewsRecyclerView.setAdapter(mReviewAdapter);
        reviewsRecyclerView.addItemDecoration(new TrailerItemDecoration(
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
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case R.id.action_trailers:
                if (null != mScrollView && null != mTrailersDivider) {
                    mScrollView.smoothScrollTo(0, mTrailersDivider.getBottom());
                }
                return true;
            case R.id.action_reviews:
                if (null != mReviewAdapter && mReviewAdapter.getItemCount() == 0) {
                    ToastUtils.toast(R.string.error_no_reviews);
                }
                if (null != mScrollView && null != mReviewsDivider) {
                    mScrollView.smoothScrollTo(0, mReviewsDivider.getBottom());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
