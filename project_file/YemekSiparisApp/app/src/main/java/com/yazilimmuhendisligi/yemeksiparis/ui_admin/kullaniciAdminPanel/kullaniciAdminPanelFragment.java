package com.yazilimmuhendisligi.yemeksiparis.ui_admin.kullaniciAdminPanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.yazilimmuhendisligi.yemeksiparis.R;

public class kullaniciAdminPanelFragment extends Fragment {

    private kullaniciAdminPanelViewModel kullaniciAdminPanelViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        kullaniciAdminPanelViewModel =
                ViewModelProviders.of(this).get(kullaniciAdminPanelViewModel.class);
        View root = inflater.inflate(R.layout.fragment_admin_musteri_panel, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        kullaniciAdminPanelViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
