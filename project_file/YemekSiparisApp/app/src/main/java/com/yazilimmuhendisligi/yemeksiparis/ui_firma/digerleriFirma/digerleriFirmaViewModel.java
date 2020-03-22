package com.yazilimmuhendisligi.yemeksiparis.ui_firma.digerleriFirma;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class digerleriFirmaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public digerleriFirmaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}