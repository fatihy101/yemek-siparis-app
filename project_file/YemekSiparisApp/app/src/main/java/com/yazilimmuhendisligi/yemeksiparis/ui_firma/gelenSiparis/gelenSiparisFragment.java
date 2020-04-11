package com.yazilimmuhendisligi.yemeksiparis.ui_firma.gelenSiparis;

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

public class gelenSiparisFragment extends Fragment {

    private gelenSiparisViewModel gelenSiparisViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gelenSiparisViewModel =
                ViewModelProviders.of(this).get(gelenSiparisViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gelen_siparis, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        gelenSiparisViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}