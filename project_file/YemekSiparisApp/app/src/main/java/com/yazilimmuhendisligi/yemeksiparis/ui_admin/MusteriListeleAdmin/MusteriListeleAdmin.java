package com.yazilimmuhendisligi.yemeksiparis.ui_admin.MusteriListeleAdmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
    ArrayList<String> müsteri_isimleri;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db= FirebaseFirestore .getInstance();
        DBVeriAL();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_musteri_listele_admin);
        ListView=(listView)findViewById(R.id.listview);
        müsteri_isimleri = new ArrayList<>();
        müsteri_isimleri.add("mertanarat");
        müsteri_isimleri.add("atıl kurt");
        müsteri_isimleri.add("müzeyyen senar");
        müsteri_isimleri.add("muray tatay");
        müsteri_isimleri.add("metehan dallı");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,müsteri_isimleri);
        ListView.setAdapter(arrayAdapter);

    }
    public void DBVeriAL()
    {
        Log.d("müsteri_isimleri", "DBVeriAL: içerideyim ");
        CollectionReference collectionReference  = db.collection("kullanici_bilgileri/");
        Log.d("müsteri_isimleri", "Collection reference initialized. ");
        collectionReference.whereEqualTo("yetki_ıd","1").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null ) Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
                {
                    müsteri_isimleri.add((String) document.get("email"));

                }

            }
        });

    }

}


