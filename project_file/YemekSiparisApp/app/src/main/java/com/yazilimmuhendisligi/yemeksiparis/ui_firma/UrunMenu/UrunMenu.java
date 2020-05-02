package com.yazilimmuhendisligi.yemeksiparis.ui_firma.UrunMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yazilimmuhendisligi.yemeksiparis.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UrunMenu extends AppCompatActivity {
EditText urunIsmi, fiyat;
FirebaseFirestore firebaseFirestore;
FirebaseAuth mAuth;
private RecyclerView recyclerView;
UrunMenuAdapter urunMenuAdapter;
ArrayList<String> urun_isimleri, urun_fiyatlari, doc_ids;
    String UIDCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Menü Güncelleme Ekranı");
        setContentView(R.layout.activity_urun_menu);
        urunIsmi = findViewById(R.id.urun_ismi_ekle);
        fiyat = findViewById(R.id.urun_fiyat_ekle);
        urun_fiyatlari = new ArrayList<>();
        urun_isimleri = new ArrayList<>();
        doc_ids = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerView_menu_guncelle);
        firebaseFirestore = FirebaseFirestore.getInstance();
        UIDCurrent = mAuth.getUid();
        DBveriAl();

    }

    public void dbUrunEkle(View view)
    {
        if(bosMuKontrol())
        {
            Toast.makeText(this, "Gerekli alanları doldurun", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String urunIsmiStr = urunIsmi.getText().toString();
            String fiyatStr = fiyat.getText().toString();

            Map<String,Object> urun = new HashMap<>();

            urun.put("urun_ismi",urunIsmiStr);
            urun.put("fiyat",fiyatStr);

            firebaseFirestore.collection("kullanici_bilgileri/"+UIDCurrent+"/menu")
                    .add(urun).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(UrunMenu.this, "Ürün menüye eklendi", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UrunMenu.this, "HATA: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public boolean bosMuKontrol()
    {
        return urunIsmi.getText().toString().equals("") || fiyat.getText().toString().equals("");
    }

    public void DBveriAl()
    {

        CollectionReference collectionReference = firebaseFirestore.collection("kullanici_bilgileri/"+ UIDCurrent +"/menu");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {

            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                urun_isimleri.clear();
                urun_fiyatlari.clear();
                doc_ids.clear();
                for(DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
                {
                    urun_isimleri.add((String) document.get("urun_ismi"));
                    urun_fiyatlari.add((String) document.get("fiyat"));
                    doc_ids.add(document.getId());
                }
                urunMenuAdapter = new UrunMenuAdapter(urun_isimleri,urun_fiyatlari,doc_ids);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(urunMenuAdapter);
            }
        });

    }
}

//firebaseFirestore.collection("kullanici_bilgileri/"+UIDCurrent+"/menu").get()
