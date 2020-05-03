package com.yazilimmuhendisligi.yemeksiparis.ui_admin.FirmaListeleAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.yazilimmuhendisligi.yemeksiparis.R;

import java.util.HashMap;

public class FirmaBilgileriGoruntule extends AppCompatActivity {

    FirebaseFirestore firmabase;
    DocumentReference docKullaniciRef;
    HashMap hashMap;
    int local_urun_sayisi = -1;
    String firma_uid;
    TextView firma_adi, firma_sahip_ad_soyad,firma_telno, firma_yetki_id,firma_mail,firma_onay,menu_sayisi;
    Button karaliste_ekle_button, karaliste_cikar_button, firma_onay_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       firma_uid = getIntent().getStringExtra("firma_uid");
        firmabase=FirebaseFirestore.getInstance();
        docKullaniciRef = firmabase.document("kullanici_bilgileri/"+firma_uid);
        //region UI Tanımları
        setContentView(R.layout.activity_firma_bilgileri_goruntule);
        firma_adi =findViewById(R.id.Firma_adı);
        firma_mail =findViewById(R.id.Firma_mail);
        firma_sahip_ad_soyad = findViewById(R.id.Firma_sahip_adsoyad);
        firma_telno=findViewById(R.id.Firma_Telno);
        firma_yetki_id=findViewById(R.id.Firma_yetki_id);
        firma_onay=findViewById(R.id.Firma_onayı);
        karaliste_ekle_button=findViewById(R.id.karaliste_al_button);
        karaliste_cikar_button =findViewById(R.id.karaliste_cikar_button);
        firma_onay_button = findViewById(R.id.firmayi_onayla);
        menu_sayisi  =findViewById(R.id.menu_urun_sayisi);
        //endregion

        Dbverial();


    }
    public void Dbverial(){
        urunSayisiniAl();
        docKullaniciRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            firma_adi.setText((String) documentSnapshot.get("firma_adi"));
                            firma_mail.setText((String) documentSnapshot.get("email"));
                            firma_sahip_ad_soyad.setText((String)documentSnapshot.get("firma_sahibi_ad_soyad"));
                            firma_telno.setText((String) documentSnapshot.get("tel_no"));
                            firma_yetki_id.setText((String) documentSnapshot.get("yetki_id"));
                            firma_onay.setText(String.valueOf(documentSnapshot.get("admin_onayi")));
                            Log.d("snapshot control", "onSuccess: " + firma_adi.getText());
                        }
                        else Log.d("snapshot control", "onSuccess: Fail");
                        //Dbveriver();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("snapshot control", "onFailure: hey");
            }
        });
    }

    public void urunSayisiniAl()
    {

        CollectionReference referenceUrunler = firmabase.collection("kullanici_bilgileri/"+firma_uid+"/menu");
        referenceUrunler.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.isEmpty()) local_urun_sayisi = 0;
                 local_urun_sayisi =  queryDocumentSnapshots.size();
                Log.d("urun_sayisi", "urunSayisiniAlCheck: "+queryDocumentSnapshots.size());
                menu_sayisi.setText("Ürün sayısı: " + local_urun_sayisi);
            }
        });
    }

    public void  Dbveriver(){
        karaliste_ekle_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> hashMap= new HashMap<>();
                hashMap.put("tel_no", (String) firma_telno.getText());
                hashMap.put("firma_sahibi_ad_soyad", (String) firma_sahip_ad_soyad.getText());
                hashMap.put("firma_adi", (String) firma_adi.getText());
                hashMap.put("email", (String) firma_mail.getText());
                hashMap.put("yetki_id", (String) firma_yetki_id.getText());
                //hashMap.put("admin_onayi", Boolean.valueOf((String) firma_onay.getText()) );
                hashMap.put("Blacklist",true);
                docKullaniciRef.set(hashMap);
                Toast.makeText(getApplicationContext(), "Kara Listeye Alındı!", Toast.LENGTH_LONG).show();
                karaliste_ekle_button.setEnabled(false);

            }
        });

        karaliste_cikar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> HASHMAP = new HashMap<>();
                HASHMAP.put("tel_no",(String) firma_telno.getText());
                HASHMAP.put("firma_sahibi_ad_soyad",(String) firma_sahip_ad_soyad.getText());
                HASHMAP.put("firma_adi",(String) firma_adi.getText());
                HASHMAP.put("email",(String) firma_mail.getText());
                HASHMAP.put("yetki_id",(String) firma_yetki_id.getText());
               // HASHMAP.put("admin_onayi", Boolean.valueOf((String) firma_onay.getText()));
                HASHMAP.put("Blacklist",false);
                docKullaniciRef.set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FirmaBilgileriGoruntule.this, "Başarıyla eklendi ", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Fail", "onFailure: " + e.getLocalizedMessage());
                        Toast.makeText(FirmaBilgileriGoruntule.this, "Hata: "+ e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });





    }



}
