package com.yazilimmuhendisligi.yemeksiparis.ui_firma.digerleriFirma;

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

public class digerleriFirmaFragment extends Fragment {

    private digerleriFirmaViewModel digerleriFirmaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        digerleriFirmaViewModel =
                ViewModelProviders.of(this).get(digerleriFirmaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_digerleri_firma, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        digerleriFirmaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
