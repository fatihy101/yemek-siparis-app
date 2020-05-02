package com.yazilimmuhendisligi.yemeksiparis.ui_musteri.Sepet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;
import com.yazilimmuhendisligi.yemeksiparis.R;
import com.yazilimmuhendisligi.yemeksiparis.ui_musteri.SiparisVerActivity.SiparisVerActivity;
import com.yazilimmuhendisligi.yemeksiparis.ui_musteri.SiparisVerActivity.UrunListRecyclerAdapterM;
import com.yazilimmuhendisligi.yemeksiparis.ui_musteri.musteriActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import io.grpc.Server;

public class Sepet extends AppCompatActivity {
    ArrayList<String> sepet_urunler;
    ArrayList<String> sepet_fiyatlar;
    RecyclerView recyclerView;
    Button onaylaButton;
    SepetAdapter adapter;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String firma_email, ad, soyad, musteri_email, tel_no,adres, sehir;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sepet);
        db = FirebaseFirestore.getInstance();
        setTitle("Sepet");
        builder = new AlertDialog.Builder(this);
        onaylaButton = findViewById(R.id.sepet_onayla_button);
        firma_email = getIntent().getStringExtra("firma_email");
        sepet_urunler  = getIntent().getStringArrayListExtra("sepetteki_urunler");
        sepet_fiyatlar = getIntent().getStringArrayListExtra("sepetteki_fiyatlar");
        // region Exception Control
        if(sepet_urunler == null) {
            onaylaButton.setText("Sipariş Ver'e Git");
            onaylaButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SiparisVerActivity.class);
                    startActivity(intent);
                }
            });
            Toast.makeText(this, "Sepetiniz boş", Toast.LENGTH_LONG).show();
        } //endregion
        else
            {
            mAuth = FirebaseAuth.getInstance();
        adapter = new SepetAdapter(sepet_urunler,sepet_fiyatlar);
        recyclerView = findViewById(R.id.siparis_musteri_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        onaylaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Siparişinizin toplam tutarı " + topla()+" TL") .setTitle("Toplam Tutar")
                .setCancelable(false)
                .setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AdresAl();
                        onaylaButton.setEnabled(false);
                    }
                }).setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Sepet.this, "İptal Edildi", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("TUTAR");
                alert.show();

            }
        });
        }
    }

    public void VeriYolla()
    {
        if(sehir == null) Toast.makeText(this, "Lütfen önce adres ekleyiniz.", Toast.LENGTH_SHORT).show();
        else
        {
            Map<String, Object> data_set  = new HashMap<>();
            data_set.put("firma_email",firma_email);
            data_set.put("musteri_ad_soyad", ad + " " + soyad);
            data_set.put("musteri_adres", adres);
            data_set.put("musteri_sehir",sehir);
            data_set.put("musteri_tel_no",tel_no);
            data_set.put("siparis_durum","Yeni Sipariş");
            data_set.put("toplam_fiyat", topla()); //int
            data_set.put("urunler",urunlerFormat());
            data_set.put("siparis_tarih_saat", FieldValue.serverTimestamp());

            CollectionReference reference  = db.collection("siparis");
            reference.add(data_set)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(Sepet.this, "Siparişiniz başarıyla verildi!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), musteriActivity.class);
                            startActivity(intent);
                        }
                    });
        }
    }

    public void AdresAl()
    {
        String uid = mAuth.getCurrentUser().getUid();
        Log.d("sepet_check", "AdresAl---> uid:" + uid);
        DocumentReference musteriRef = db.collection("kullanici_bilgileri/"+uid+"/adres").document("birincil_adres");
        musteriRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

              sehir = (String) documentSnapshot.get("sehir");
              adres = documentSnapshot.get("acik_adres") + ", " + documentSnapshot.get("sokak")
                      + ", " + documentSnapshot.get("semt") + "\nYol Tarifi" + documentSnapshot.get("yol_tarifi");
                MusteriBilgiAl();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Error DB", "onFailure: " +e.getLocalizedMessage());
            }
        });
        Log.d("sepet_check", "AdresAl: Başarılı. var sehir:" + sehir);
    }

    public void MusteriBilgiAl()
    {
        DocumentReference musteriRef = db.collection("kullanici_bilgileri").document(mAuth.getCurrentUser().getUid());
        musteriRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ad = (String) documentSnapshot.get("ad");
                soyad = (String) documentSnapshot.get("soyad");
                musteri_email = (String) documentSnapshot.get("email");
                tel_no = (String) documentSnapshot.get("tel_no");
                Log.d("sepet_check", "MusteriBilgiAl: Başarılı. var ad:" + ad);
                VeriYolla();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Sepet.this, "Müşteri bilgisini almada hata: " +  e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public int topla()
    {
        int tutar = 0;
        for(int i=0; i < sepet_fiyatlar.size();i++) tutar += Integer.parseInt(sepet_fiyatlar.get(i));
        return tutar;
    }

    public String urunlerFormat()
    {
      /*  ArrayList<String> tempUrunler = new ArrayList<>();
        int counter= 0;
        for(int i=0; i < sepet_urunler.size();i++){
           counter =  Collections.frequency(sepet_urunler,sepet_urunler.get(i));
        } //Urun sayısını hesapla.
*/
        StringBuilder urunlerTemp = new StringBuilder();
        for(int i=0; i < sepet_urunler.size();i++)  urunlerTemp.append(sepet_urunler.get(i)).append(", ");
        Log.d("sepet kontrol", "urunlerFormat: Başarılı. Output:" + urunlerTemp.toString());
        return urunlerTemp.toString();
    }
}
