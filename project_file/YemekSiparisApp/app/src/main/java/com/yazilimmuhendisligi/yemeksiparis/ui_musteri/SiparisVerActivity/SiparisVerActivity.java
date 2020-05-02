package com.yazilimmuhendisligi.yemeksiparis.ui_musteri.SiparisVerActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yazilimmuhendisligi.yemeksiparis.R;

import java.util.ArrayList;

public class SiparisVerActivity extends AppCompatActivity  {

    FirebaseFirestore db;
    RecyclerView recyclerView;
    ArrayList<String> firmalar_isim;
    ArrayList<String> firmalar_uid;
    ArrayList<String> firmalar_email;
    RestoranListeRecylerAdapter_Musteri restoranlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siparis_ver);
        db = FirebaseFirestore.getInstance();
        firmalar_isim = new ArrayList<>();
        firmalar_uid = new ArrayList<>();
        firmalar_email = new ArrayList<>();
        //RecylerView
        DBVeriAl();
        restoranlistAdapter = new RestoranListeRecylerAdapter_Musteri(firmalar_isim,firmalar_uid,firmalar_email);
        recyclerView = findViewById(R.id.recyclerView_restoran_listele_musteri);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(restoranlistAdapter);


    }

    public void DBVeriAl()
    {
CollectionReference collectionReference  = db.collection("kullanici_bilgileri");
collectionReference.whereEqualTo("yetki_id","2").addSnapshotListener(new EventListener<QuerySnapshot>() {
    @Override
    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
           if(e != null ) Toast.makeText(SiparisVerActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

           for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
           {
               firmalar_isim.add((String) document.get("firma_adi"));
               firmalar_uid.add(document.getId());
               firmalar_email.add((String) document.get("email"));


               restoranlistAdapter.notifyDataSetChanged(); //Adapter'a bilgilerin değiştiğini bildirmek

           }
    }
});
// kullanici_bilgieri/firmaUID/menu
    }


}
