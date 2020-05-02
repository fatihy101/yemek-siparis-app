package com.yazilimmuhendisligi.yemeksiparis.ui_admin.MusteriListeleAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yazilimmuhendisligi.yemeksiparis.R;
import com.yazilimmuhendisligi.yemeksiparis.ui_admin.MusteriKaraliste.MusteriKaraliste;

import java.util.HashMap;

public class MusteriBilgileriGoruntule extends AppCompatActivity {
    String musteri_uid;

    FirebaseFirestore datab;
    DocumentReference referans;
    TextView textview_mail, textview_no, textview_isim,textview_soyisim,textView_yetli_id;
    private Button Buttn;
    private Button Buttnn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_musteri_bilgileri_goruntule);
        musteri_uid = getIntent().getStringExtra("musteri_uid");
        String musteri_email_title = getIntent().getStringExtra("musteri_email");
        setTitle("Musteri: " + musteri_email_title);
        datab=FirebaseFirestore.getInstance();
        Buttn=  findViewById(R.id.buton12);
        Buttnn= findViewById(R.id.button13);


        textView_yetli_id= findViewById(R.id.textView66);
        textview_mail  =  findViewById(R.id.textview_no);
        textview_no  =  findViewById(R.id.textview_mail);
        textview_isim  =  findViewById(R.id.textview_soyisim);
        textview_soyisim   =  findViewById(R.id.textview_isim);
        referans = datab.document("kullanici_bilgileri/"+musteri_uid);

        Dbverigetir();
        Dbverigotur();


    }

    public void Dbverigetir(){

        referans.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            textview_mail.setText((String) documentSnapshot.get("email"));
                            textview_isim.setText((String)documentSnapshot.get("ad"));
                            textview_no.setText((String) documentSnapshot.get("tel_no"));
                            textview_soyisim.setText((String) documentSnapshot.get("soyad"));
                            textView_yetli_id.setText((String) documentSnapshot.get("yetki_id"));

                        }
                    }
                });


    }
    public  void Dbverigotur(){
        Buttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Banlama butonu

                HashMap<String,Object> hashMap= new HashMap<>();
                hashMap.put("tel_no", (String) textview_no.getText());
                hashMap.put("soyad", (String) textview_soyisim.getText());
                hashMap.put("ad", (String) textview_isim.getText());
                hashMap.put("email", (String) textview_mail.getText());
                hashMap.put("yetki_id", (String) textView_yetli_id.getText());
                hashMap.put("Blacklist",true);
                referans.set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MusteriBilgileriGoruntule.this, "Kişi başarıyla karalisteye eklendi.", Toast.LENGTH_SHORT).show();
                        Intent Intent = new Intent(MusteriBilgileriGoruntule.this,MusteriKaraliste.class);
                        startActivity(Intent);
                        Buttn.setEnabled(false);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MusteriBilgileriGoruntule.this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }

        });
            Buttnn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String,Object> hashMAP= new HashMap<>();
                    hashMAP.put("tel_no", (String) textview_no.getText());
                    hashMAP.put("soyad", (String) textview_soyisim.getText());
                    hashMAP.put("ad", (String) textview_isim.getText());
                    hashMAP.put("email", (String) textview_mail.getText());
                    hashMAP.put("yetki_id", (String) textView_yetli_id.getText());
                    hashMAP.put("Blacklist",false);
                    referans.set(hashMAP);
                    Toast.makeText(MusteriBilgileriGoruntule.this, "Kara Listeden Çıkarıldı!", Toast.LENGTH_LONG).show();
                    Buttnn.setEnabled(false);
                }
            });




    }

}