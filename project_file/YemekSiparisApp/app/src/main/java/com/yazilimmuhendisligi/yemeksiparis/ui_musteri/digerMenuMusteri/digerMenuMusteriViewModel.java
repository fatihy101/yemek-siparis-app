package com.yazilimmuhendisligi.yemeksiparis.ui_musteri.digerMenuMusteri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class digerMenuMusteriViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public digerMenuMusteriViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}