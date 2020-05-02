package com.yazilimmuhendisligi.yemeksiparis.ui_musteri.SiparisVerActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yazilimmuhendisligi.yemeksiparis.R;
import com.yazilimmuhendisligi.yemeksiparis.ui_musteri.Sepet.Sepet;

import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class UrunListelemeActivity_Siparis extends AppCompatActivity {
    FirebaseFirestore db;
    String firmaUID,firmaTitle,firma_email;
    ArrayList<String> urun_isimleri;
    ArrayList<String> urun_fiyatlari;
    RecyclerView recyclerView;
    UrunListRecyclerAdapterM adapter;
    ArrayList<String> price;
    ArrayList<String> name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_listeleme__siparis);
        firmaUID = getIntent().getStringExtra("firmaUID");
        firma_email = getIntent().getStringExtra("firma_email");
        Log.d("Bos UID", "onCreate: " + firmaUID);
        firmaTitle = getIntent().getStringExtra("firma_isim");
        setTitle(firmaTitle);
        urun_isimleri = new ArrayList<>();
        urun_fiyatlari = new ArrayList<>();

        DBVeriAL();
        Log.d("Arraysize", "from onCreate: " + urun_isimleri.size());

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
             name = intent.getStringArrayListExtra("sepetteki_urunler");
             price = intent.getStringArrayListExtra("sepetteki_fiyatlar");
            //Toast.makeText(getApplicationContext(),"from siparisVer:" + name +" "+price ,Toast.LENGTH_SHORT).show();
        }
    };

    public void sepeteGit(View view)
    {
       // Log.d(TAG, "sepeteGit: ");
            Intent intentSepet = new Intent(getApplicationContext(), Sepet.class);
            intentSepet.putExtra("sepetteki_urunler",name);
            intentSepet.putExtra("sepetteki_fiyatlar",price);
            intentSepet.putExtra("firma_email",firma_email);
            startActivity(intentSepet);
    }

    public void DBVeriAL()
    {
        Log.d("array", "DBVeriAL: içerideyim ");
        CollectionReference collectionReference  = db.collection("kullanici_bilgileri/"+firmaUID+"/menu");
        Log.d("array", "Collection reference initialized. ");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null ) Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                urun_isimleri.clear();
                urun_fiyatlari.clear();
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments())
                {
                    urun_isimleri.add((String) document.get("urun_ismi"));
                    urun_fiyatlari.add((String) document.get("fiyat"));
                    System.out.println(urun_isimleri.get(0) + " " + urun_fiyatlari.get(0)+ " " + urun_fiyatlari.size());
                    Log.d("array", "for:" + urun_isimleri.size());
                }
                //RecyclerView
                adapter = new UrunListRecyclerAdapterM(urun_isimleri,urun_fiyatlari);
                recyclerView = findViewById(R.id.urunler_musteri_recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
            }
        });
        Log.d("array", "method sonu " + urun_isimleri.size());

    }

}
