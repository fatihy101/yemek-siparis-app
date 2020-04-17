package com.yazilimmuhendisligi.yemeksiparis.ui_firma.UrunMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yazilimmuhendisligi.yemeksiparis.R;

import java.util.HashMap;
import java.util.Map;

public class UrunMenu extends AppCompatActivity {
EditText urunIsmi, fiyat;
FirebaseFirestore firebaseFirestore;
FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_menu);
    urunIsmi = findViewById(R.id.urun_ismi_ekle);
    fiyat = findViewById(R.id.urun_fiyat_ekle);
    mAuth = FirebaseAuth.getInstance();
    firebaseFirestore = FirebaseFirestore.getInstance();
    setTitle("Menü Güncelleme Ekranı");
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
            String UIDCurrent  = mAuth.getUid();
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


}

//firebaseFirestore.collection("kullanici_bilgileri/"+UIDCurrent+"/menu").get()
