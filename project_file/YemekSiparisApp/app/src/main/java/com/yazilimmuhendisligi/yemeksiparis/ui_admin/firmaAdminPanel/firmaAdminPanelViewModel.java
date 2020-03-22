package com.yazilimmuhendisligi.yemeksiparis.ui_admin.firmaAdminPanel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class firmaAdminPanelViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public firmaAdminPanelViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}