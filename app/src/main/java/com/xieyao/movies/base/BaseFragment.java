package com.xieyao.movies.base;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    protected void setTitle(@StringRes int resId) {
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(resId);
        } catch (Exception e) {
        }
    }

}
