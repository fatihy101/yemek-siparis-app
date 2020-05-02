package com.yazilimmuhendisligi.yemeksiparis.ui_admin.FirmaListeleAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yazilimmuhendisligi.yemeksiparis.R;
import com.yazilimmuhendisligi.yemeksiparis.ui_admin.MusteriListeleAdmin.MusteriBilgileriGoruntule;

import java.util.HashMap;

public class FirmaBilgileriGoruntule extends AppCompatActivity {

    FirebaseFirestore firmabase;
    DocumentReference fireferans;
    HashMap hashMap;

    TextView firma_adı;
    TextView firma_Sahipadsoyad;
    TextView firma_telno;
    TextView firma_yetki_id;
    TextView firma_mail;
    TextView firma_onay;
    Button button1;
    Button button2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String firma_uid = getIntent().getStringExtra("firma_uid");
        firmabase=FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_firma_bilgileri_goruntule);
        firma_adı=findViewById(R.id.Firma_adı);
        firma_mail =findViewById(R.id.Firma_mail);
        firma_Sahipadsoyad= findViewById(R.id.Firma_sahip_adsoyad);
        firma_telno=findViewById(R.id.Firma_Telno);
        firma_yetki_id=findViewById(R.id.Firma_yetki_id);
        firma_onay=findViewById(R.id.Firma_onayı);
        fireferans=firmabase.document("kullanici_bilgileri/"+firma_uid);
        button1=findViewById(R.id.button_1);
        button2=findViewById(R.id.button_2);


        Dbveriver();
        Dbverial();


    }
    public void Dbverial(){

        fireferans.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            firma_adı.setText((String) documentSnapshot.get("firma_adi"));
                            firma_mail.setText((String) documentSnapshot.get("email"));
                            firma_Sahipadsoyad.setText((String)documentSnapshot.get("firma_sahibi_ad_soyad"));
                            firma_telno.setText((String) documentSnapshot.get("tel_no"));
                            firma_yetki_id.setText((String) documentSnapshot.get("yetki_id"));
                            firma_onay.setText((String)String.valueOf(documentSnapshot.get("admin_onayi")) );

                        }


                    }
                });


    }
    public void  Dbveriver(){
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> hashMap= new HashMap<>();
                hashMap.put("tel_no", (String) firma_telno.getText());
                hashMap.put("firma_sahibi_ad_soyad", (String) firma_Sahipadsoyad.getText());
                hashMap.put("firma_adi", (String) firma_adı.getText());
                hashMap.put("email", (String) firma_mail.getText());
                hashMap.put("yetki_id", (String) firma_yetki_id.getText());
                hashMap.put("admin_onayi", Boolean.valueOf((String) firma_onay.getText()) );
                hashMap.put("Blacklist",true);
                fireferans.set(hashMap);
                Toast.makeText(getApplicationContext(), "Kara Listeye Alındı!", Toast.LENGTH_LONG).show();
                button1.setEnabled(false);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> HASHMAP = new HashMap<>();
                HASHMAP.put("tel_no",(String) firma_telno.getText());
                HASHMAP.put("firma_sahibi_ad_soyad",(String) firma_Sahipadsoyad.getText());
                HASHMAP.put("firma_adi",(String) firma_adı.getText());
                HASHMAP.put("email",(String) firma_mail.getText());
                HASHMAP.put("yetki_id",(String) firma_yetki_id.getText());
                HASHMAP.put("admin_onayi", Boolean.valueOf((String) firma_onay.getText()));
                HASHMAP.put("Blacklist",true);
                fireferans.set(hashMap);
            }
        });





    }



}
