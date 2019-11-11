package com.xieyao.movies.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public MutableLiveData<Integer> mTabSelectPosition = new MutableLiveData<>();
}
