package com.yazilimmuhendisligi.yemeksiparis.ui_admin.MusteriListeleAdmin;

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

public class MusteriListeleAdmin extends AppCompatActivity {
    ListView listView;
    ArrayList<String> musteri_email;
    ArrayList<String> musteri_uid;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteri_listele_admin);
        setTitle("Müşterileri Listele");
        musteri_email = new ArrayList<>();
        musteri_uid = new ArrayList<>();
        db= FirebaseFirestore .getInstance();
        listView = findViewById(R.id.listview);
        DBVeriAL();




    }

    public void DBVeriAL()
    {
        Log.d("müsteri_isimleri", "DBVeriAL: içerideyim ");
        CollectionReference collectionReference  = db.collection("kullanici_bilgileri");
        Log.d("müsteri_isimleri", "Collection reference initialized. ");
        collectionReference.whereEqualTo("yetki_id","1").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null ) Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                musteri_email.clear();
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
                {
                    musteri_email.add((String) document.get("email"));
                    musteri_uid.add(document.getId());
                    Log.d("musteri_isimleri: ", musteri_email.get(0) + " " + musteri_email.size());
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, musteri_email);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Intent Intent = new Intent(MusteriListeleAdmin.this,MusteriBilgileriGoruntule.class);
                        Intent.putExtra("musteri_uid",musteri_uid.get(position));
                        Intent.putExtra("musteri_email",musteri_email.get(position));
                        startActivity(Intent);
                    }
                });

            }
        });

    }

}