package com.yazilimmuhendisligi.yemeksiparis.ui_firma.urunMenu;

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

public class urunMenuFragment extends Fragment {

    private urunMenuViewModel urunMenuViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        urunMenuViewModel =
                ViewModelProviders.of(this).get(urunMenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_urun_menu, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        urunMenuViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
