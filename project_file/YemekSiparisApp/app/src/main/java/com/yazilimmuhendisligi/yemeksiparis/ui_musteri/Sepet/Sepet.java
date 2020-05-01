package com.yazilimmuhendisligi.yemeksiparis.ui_musteri.Sepet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.yazilimmuhendisligi.yemeksiparis.R;

import java.util.ArrayList;

public class Sepet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sepet);
        ArrayList<String> sepet_urunler  = getIntent().getStringArrayListExtra("sepetteki_urunler");
        ArrayList<String> sepet_fiyatlar = getIntent().getStringArrayListExtra("sepetteki_fiyatlar");
        System.out.println(sepet_urunler + " " + sepet_fiyatlar);
    }
}
