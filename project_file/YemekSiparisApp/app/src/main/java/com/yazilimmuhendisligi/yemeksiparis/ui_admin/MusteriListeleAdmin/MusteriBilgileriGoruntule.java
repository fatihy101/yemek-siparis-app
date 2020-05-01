package com.yazilimmuhendisligi.yemeksiparis.ui_admin.MusteriListeleAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yazilimmuhendisligi.yemeksiparis.R;

public class MusteriBilgileriGoruntule extends AppCompatActivity {
    String musteri_uid;
    FirebaseFirestore datab;
    DocumentReference referans;
    TextView textview_mail;
    TextView textview_no;
    TextView textview_isim;
    TextView textview_soyisim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteri_bilgileri_goruntule);
        musteri_uid = getIntent().getStringExtra("musteri_uid");
        String musteri_email_title = getIntent().getStringExtra("musteri_email");
        setTitle("Musteri: " + musteri_email_title);
        datab=FirebaseFirestore.getInstance();
        textview_mail  =  findViewById(R.id.textview_no);
        textview_no  =  findViewById(R.id.textview_mail);
        textview_isim  =  findViewById(R.id.textview_soyisim);
        textview_soyisim   =  findViewById(R.id.textview_isim);
        Dbverigetir();

    }


    public void Dbverigetir(){
        referans = datab.document("kullanici_bilgileri/"+musteri_uid);
        referans.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            textview_mail.setText((String) documentSnapshot.get("email"));
                            textview_isim.setText((String)documentSnapshot.get("ad"));
                            textview_no.setText((String) documentSnapshot.get("tel_no"));
                            textview_soyisim.setText((String) documentSnapshot.get("soyad"));
                        }

                    }
                });


    }
}