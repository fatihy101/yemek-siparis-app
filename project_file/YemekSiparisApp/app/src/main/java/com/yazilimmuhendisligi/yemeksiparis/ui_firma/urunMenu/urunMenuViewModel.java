package com.yazilimmuhendisligi.yemeksiparis.ui_firma.urunMenu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class urunMenuViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public urunMenuViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}