package com.yazilimmuhendisligi.yemeksiparis.ui_admin.kullaniciAdminPanel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class kullaniciAdminPanelViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public kullaniciAdminPanelViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}