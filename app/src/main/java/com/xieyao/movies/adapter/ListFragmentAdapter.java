package com.xieyao.movies.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.xieyao.movies.list.ListFragment;

public class ListFragmentAdapter extends FragmentStateAdapter {

    public ListFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ListFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
