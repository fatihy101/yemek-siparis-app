package com.yazilimmuhendisligi.yemeksiparis.ui_musteri.Sepet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.yazilimmuhendisligi.yemeksiparis.R;
import com.yazilimmuhendisligi.yemeksiparis.ui_musteri.SiparisVerActivity.UrunListRecyclerAdapterM;

import java.util.ArrayList;

public class Sepet extends AppCompatActivity {
    ArrayList<String> sepet_urunler;
    ArrayList<String> sepet_fiyatlar;
    RecyclerView recyclerView;
    UrunListRecyclerAdapterM adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sepet);
        setTitle("Sepet");
         sepet_urunler  = getIntent().getStringArrayListExtra("sepetteki_urunler");
         sepet_fiyatlar = getIntent().getStringArrayListExtra("sepetteki_fiyatlar");
        System.out.println(sepet_urunler + " " + sepet_fiyatlar);

        adapter = new UrunListRecyclerAdapterM(sepet_urunler,sepet_fiyatlar);
        recyclerView = findViewById(R.id.siparis_musteri_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }


}
