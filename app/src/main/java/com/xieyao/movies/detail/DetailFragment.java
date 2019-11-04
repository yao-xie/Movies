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
import com.xieyao.movies.data.bean.MovieItem;
import com.xieyao.movies.utils.ToastUtils;
import com.xieyao.movies.widget.TrailerItemDecoration;

/**
 * Created by xieyao on 2019-10-20.
 * fragment for detail
 */
public class DetailFragment extends BaseFragment {

    private static final String MOVIE = "movie";

    private DetailViewModel mViewModel;

    private MovieItem mMovie;

    public DetailFragment() {
    }

    public static DetailFragment newInstance(MovieItem movieItem) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOVIE, movieItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (null == savedInstanceState) {
            mMovie = getArguments().getParcelable(MOVIE);
        } else {
            mMovie = savedInstanceState.getParcelable(MOVIE);
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

        if (null == savedInstanceState) {
            mViewModel.start(mMovie);
            mViewModel.fetchRemoteData();
        }
        return root;
    }

    public DetailViewModel obtainViewModel() {
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(getActivity(), factory).get(DetailViewModel.class);
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
                // TODO
                return true;
            case R.id.action_reviews:
                // TODO
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
