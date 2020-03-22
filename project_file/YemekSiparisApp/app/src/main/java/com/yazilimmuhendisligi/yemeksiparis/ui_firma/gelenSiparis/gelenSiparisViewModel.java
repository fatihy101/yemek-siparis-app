package com.yazilimmuhendisligi.yemeksiparis.ui_firma.gelenSiparis;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class gelenSiparisViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public gelenSiparisViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}