package com.yazilimmuhendisligi.yemeksiparis.ui_musteri.anaMenu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class anaMenuViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public anaMenuViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}