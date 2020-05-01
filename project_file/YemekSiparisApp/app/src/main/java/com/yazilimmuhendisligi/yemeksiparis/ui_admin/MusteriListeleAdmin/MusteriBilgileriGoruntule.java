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
    String musteri_uid ;
    FirebaseFirestore datab=FirebaseFirestore.getInstance();
    DocumentReference referans =datab.document("kullanici_bilgileri/"+musteri_uid)
    TextView textView6 ;
    TextView textview_mail;
    TextView textview_no;
    TextView textview_isim;
    TextView textview_soyisim;
    Dbverigetir();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        musteri_uid =getIntent().getStringExtra();
        super.onCreate(savedInstanceState);
        TextView =new TextView(findViewById(textview_no));
        TextView =new TextView(findViewById(textview_mail));
        TextView =new TextView(findViewById(textview_soyisim));
        TextView =new TextView(findViewById(textview_isim));
        setContentView(R.layout.activity_musteri_bilgileri_goruntule);


        public void Dbverigetir(){
            referans.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()){
                                textview_mail=documentSnapshot.get("mail");
                                textview_isim=documentSnapshot.get("ad");
                                textview_no=documentSnapshot.;
                                textview_soyisim=documentSnapshot.get("soyad");



                            }

                        }
                    })


        }


        String musteri_uid = getIntent().getStringExtra("musteri_uid");
        System.out.println(musteri_uid);

    }
}