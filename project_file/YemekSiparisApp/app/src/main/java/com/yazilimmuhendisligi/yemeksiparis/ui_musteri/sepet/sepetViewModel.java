package com.yazilimmuhendisligi.yemeksiparis.ui_musteri.sepet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class sepetViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public sepetViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}