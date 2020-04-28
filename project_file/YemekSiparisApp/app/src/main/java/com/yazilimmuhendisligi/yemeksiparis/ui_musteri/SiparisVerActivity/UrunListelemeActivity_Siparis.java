package com.yazilimmuhendisligi.yemeksiparis.ui_musteri.SiparisVerActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yazilimmuhendisligi.yemeksiparis.R;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class UrunListelemeActivity_Siparis extends AppCompatActivity {
    FirebaseFirestore db;
    String firmaUID;
    ArrayList<String> urun_isimleri;
    ArrayList<String> urun_fiyatlari;
    RecyclerView recyclerView;
    UrunListRecyclerAdapterM adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_listeleme__siparis);
        firmaUID = getIntent().getStringExtra("firmaUID");
        urun_isimleri = new ArrayList<>();
        urun_fiyatlari = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        DBVeriAL();
        recyclerView = findViewById(R.id.urunler_musteri_recyclerview);
        adapter = new UrunListRecyclerAdapterM(urun_isimleri,urun_fiyatlari);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void DBVeriAL()
    {
        CollectionReference collectionReference  = db.collection("kullanici_bilgileri/"+firmaUID+"/menu");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null ) Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
                {

                    urun_isimleri.add((String) document.get("urun_ismi"));
                    urun_fiyatlari.add((String) document.get("fiyat"));
                }

            }
        });
    }
}
