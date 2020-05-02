package com.yazilimmuhendisligi.yemeksiparis.ui_admin.FirmaKaraliste;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yazilimmuhendisligi.yemeksiparis.R;
import com.yazilimmuhendisligi.yemeksiparis.ui_admin.FirmaListeleAdmin.FirmaBilgileriGoruntule;
import com.yazilimmuhendisligi.yemeksiparis.ui_admin.MusteriKaraliste.MusteriKaraliste;
import com.yazilimmuhendisligi.yemeksiparis.ui_admin.MusteriListeleAdmin.MusteriBilgileriGoruntule;

import java.util.ArrayList;

public class FirmaKaraliste extends AppCompatActivity {
    ListView listView;
    ArrayList<String> firma_mail;
    ArrayList<String> firma_uid;
    FirebaseFirestore databaser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firma_karaliste);
        setTitle("Firma Karaliste");
        firma_mail= new ArrayList<>();
        firma_uid= new ArrayList<>();
        databaser=FirebaseFirestore.getInstance();
        listView=findViewById(R.id.liste23);
        dbverigetir();

    }
     public void dbverigetir(){
         CollectionReference collectionReference=databaser.collection("kullanici_bilgileri");
         collectionReference.whereEqualTo("Blacklist",true).addSnapshotListener(new EventListener<QuerySnapshot>() {
             @Override
             public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                 for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
                 {
                     firma_mail.add((String) document.get("email"));
                     firma_uid.add(document.getId());
                 }
                 ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, firma_mail);
                 listView.setAdapter(arrayAdapter);
                 listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                         Intent Intent = new Intent(FirmaKaraliste.this, FirmaBilgileriGoruntule.class);
                         Intent.putExtra("musteri_uid",firma_uid.get(position));
                         startActivity(Intent);
                     }
                 });



             }
         });





     };


}
