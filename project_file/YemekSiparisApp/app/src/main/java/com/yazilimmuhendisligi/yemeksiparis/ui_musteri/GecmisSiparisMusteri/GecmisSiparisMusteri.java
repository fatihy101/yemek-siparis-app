package com.yazilimmuhendisligi.yemeksiparis.ui_musteri.GecmisSiparisMusteri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yazilimmuhendisligi.yemeksiparis.R;
import com.yazilimmuhendisligi.yemeksiparis.ui_musteri.SiparisVerActivity.SiparisVerActivity;

import java.util.ArrayList;
import java.util.Collections;

public class GecmisSiparisMusteri extends AppCompatActivity {
    ArrayList<String> icerik_gecmis, email_firma_gecmis, durum_gecmis;
    ArrayList<String> tutar_gecmis;
    FirebaseFirestore db;
    RecyclerView recyclerView;
    GecmisSiparisAdapter gecmisSiparisAdapter;
    String tel_no_musteri;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gecmis_siparis_musteri);
        icerik_gecmis = new ArrayList<>();
        tutar_gecmis = new ArrayList<>();
        email_firma_gecmis = new ArrayList<>();
        durum_gecmis = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        musteriBilgisiAl();


    }

    public void siparisiAl()
    {
        CollectionReference siparisler = db.collection("siparis");
        siparisler.whereEqualTo("musteri_tel_no",tel_no_musteri)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null ) Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        icerik_gecmis.clear();
                        email_firma_gecmis.clear();
                        durum_gecmis.clear();
                        tutar_gecmis.clear();
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
                        {
                            icerik_gecmis.add((String) document.get("urunler"));
                            email_firma_gecmis.add((String) document.get("firma_email"));
                            durum_gecmis.add((String) document.get("siparis_durum"));
                            tutar_gecmis.add(Long.toString((Long) document.get("toplam_fiyat")));
                        }
                        //recyclerview
                        gecmisSiparisAdapter = new GecmisSiparisAdapter(icerik_gecmis,tutar_gecmis,email_firma_gecmis,durum_gecmis);
                        recyclerView = findViewById(R.id.gecmis_siparis_recycler);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(gecmisSiparisAdapter);
                    }
                });
    }

    public void musteriBilgisiAl()
    {
        DocumentReference musteriRef = db.collection("kullanici_bilgileri").document(mAuth.getCurrentUser().getUid());
        musteriRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                tel_no_musteri = (String) documentSnapshot.get("tel_no");
                siparisiAl();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GecmisSiparisMusteri.this, "Geçmiş siparişler bir sebepten getirilemedi: "  +e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}