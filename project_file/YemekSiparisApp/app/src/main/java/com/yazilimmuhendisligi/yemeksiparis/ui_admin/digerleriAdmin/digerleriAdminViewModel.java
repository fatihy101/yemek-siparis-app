package com.yazilimmuhendisligi.yemeksiparis.ui_admin.digerleriAdmin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class digerleriAdminViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public digerleriAdminViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}