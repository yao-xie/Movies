package com.xieyao.movies.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xieyao.movies.MainBinding;
import com.xieyao.movies.R;
import com.xieyao.movies.adapter.ListFragmentAdapter;
import com.xieyao.movies.base.BaseFragment;
import com.xieyao.movies.utils.ConfigUtils;

public class MainFragment extends BaseFragment {

    private MainViewModel mViewModel;
    private ViewPager2 mViewPager;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = initView(inflater, container, getResources().getConfiguration().orientation);
        return mRootView;
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, int orientation) {
        MainBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        if (null == mViewModel) {
            mViewModel = (MainViewModel) obtainViewModel(MainViewModel.class);
        }
        binding.setLifecycleOwner(getActivity());
        binding.setViewModel(mViewModel);
        View view = binding.getRoot();

        mViewPager = view.findViewById(R.id.viewpager);
        ListFragmentAdapter adapter = new ListFragmentAdapter(this);
        mViewPager.setAdapter(adapter);
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        new TabLayoutMediator(tabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(ConfigUtils.getTabTitle(position));
            }
        }).attach();
        return view;
    }

    private void addOnTabSelectedListener(View root) {
        TabLayout tabLayout = root.findViewById(R.id.tablayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                try {
//                    mTabPosition = tab.getPosition();
//                    ConfigUtils.setListMode(mTabPosition);
//                    mViewModel.refreshMovies();
//                    if (null != mRecyclerView) {
//                        mRecyclerView.scrollToPosition(0);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
