package com.yazilimmuhendisligi.yemeksiparis.ui_admin.FirmaListeleAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yazilimmuhendisligi.yemeksiparis.R;

public class firma_bilgileri_goruntule extends AppCompatActivity {
    TextView textView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firma_bilgileri_goruntule);
        String firma_uid = getIntent().getStringExtra("firma_uid");
        System.out.println(firma_uid);

    }
}
