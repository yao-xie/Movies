package com.xieyao.movies.base;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.xieyao.movies.ViewModelFactory;

public abstract class BaseFragment extends Fragment {

    protected View mRootView;

    /**
     * create ViewModel, init views, execute data binding
     * call this method when onCreateView on onConfigurationChanged
     *
     * @param inflater
     * @param container
     * @param orientation
     * @return
     */
    protected abstract View initView(LayoutInflater inflater, ViewGroup container, int orientation);

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        parent.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = initView(inflater, parent, newConfig.orientation);
        parent.addView(mRootView);
    }

    protected void setTitle(@StringRes int resId) {
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(resId);
        } catch (Exception e) {
        }
    }

    protected void enableBackBtn(boolean b) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (null != actionBar) {
            actionBar.setHomeButtonEnabled(b);
            actionBar.setDisplayHomeAsUpEnabled(b);
        }
    }

    protected ViewModel obtainViewModel(Class clazz) {
        ViewModelFactory factory = ViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(getActivity(), factory).get(clazz);
    }

}
