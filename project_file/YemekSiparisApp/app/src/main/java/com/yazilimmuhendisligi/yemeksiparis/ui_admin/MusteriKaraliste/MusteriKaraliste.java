package com.yazilimmuhendisligi.yemeksiparis.ui_admin.MusteriKaraliste;

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
import com.yazilimmuhendisligi.yemeksiparis.ui_admin.MusteriListeleAdmin.MusteriBilgileriGoruntule;

import java.util.ArrayList;

public class MusteriKaraliste extends AppCompatActivity {
    ListView listView;
    ArrayList<String> musteri_email;
    ArrayList<String> musteri_uid;
    FirebaseFirestore databse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteri_karaliste);
        setTitle("Müşteri Karalistesi");
        musteri_email = new ArrayList<>();
        musteri_uid = new ArrayList<>();
        databse= FirebaseFirestore.getInstance();
        listView=findViewById(R.id.listview22);

        Dbvericek();


    }

        public void Dbvericek(){
        CollectionReference collectionReference = databse.collection("kullanici_bilgileri");
        collectionReference.whereEqualTo("Blacklist",true).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
                {
                    musteri_email.add((String) document.get("email"));
                    musteri_uid.add(document.getId());

                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, musteri_email);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent Intent = new Intent(MusteriKaraliste.this,MusteriBilgileriGoruntule.class);
                        Intent.putExtra("musteri_uid",musteri_uid.get(position));
                        startActivity(Intent);
                    }
                });


            }
        });



        }




    }

