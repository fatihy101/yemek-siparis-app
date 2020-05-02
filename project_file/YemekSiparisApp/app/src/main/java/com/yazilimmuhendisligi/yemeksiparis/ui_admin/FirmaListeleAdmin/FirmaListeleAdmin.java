package com.yazilimmuhendisligi.yemeksiparis.ui_admin.FirmaListeleAdmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.yazilimmuhendisligi.yemeksiparis.R;

import java.util.ArrayList;

public class FirmaListeleAdmin extends AppCompatActivity {

    ListView listView;
    ArrayList<String> firma_ad;
    ArrayList<String> firma_uid;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firma_listele_admin);
        setTitle("Firmaları Listele");
        firma_ad = new ArrayList<>();
        firma_uid = new ArrayList<>();
        db= FirebaseFirestore .getInstance();
        listView = findViewById(R.id.firma_listview);
        DBVeriAL();




    }

    public void DBVeriAL()
    {
        Log.d("firma_isimleri", "DBVeriAL: içerideyim ");
        CollectionReference collectionReference  = db.collection("kullanici_bilgileri");
        Log.d("firma_isimleri", "Collection reference initialized. ");
        collectionReference.whereEqualTo("yetki_id","2").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null ) Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                firma_ad.clear();
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
                {
                    firma_ad.add((String) document.get("firma_adi"));
                    firma_uid.add(document.getId());
                    Log.d("firma_isimleri: ", firma_ad.get(0) + " " + firma_ad.size());
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, firma_ad);
               // listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent Intent = new Intent(FirmaListeleAdmin.this,FirmaBilgileriGoruntule.class);
                        Intent.putExtra("firma_uid",firma_uid.get(position));
                        startActivity(Intent);
                    }
                });

            }
        });

    }
}
