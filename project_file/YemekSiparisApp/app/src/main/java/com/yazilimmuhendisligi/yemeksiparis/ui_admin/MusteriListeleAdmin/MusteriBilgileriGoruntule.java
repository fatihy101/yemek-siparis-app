package com.yazilimmuhendisligi.yemeksiparis.ui_admin.MusteriListeleAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yazilimmuhendisligi.yemeksiparis.R;

public class MusteriBilgileriGoruntule extends AppCompatActivity {
    TextView textView6 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteri_bilgileri_goruntule);
        String musteri_uid = getIntent().getStringExtra("musteri_uid");
        System.out.println(musteri_uid);
    }
}
