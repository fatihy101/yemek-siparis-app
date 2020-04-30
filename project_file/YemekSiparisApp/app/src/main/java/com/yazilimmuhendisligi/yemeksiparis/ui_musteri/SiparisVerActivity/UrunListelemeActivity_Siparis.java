package com.yazilimmuhendisligi.yemeksiparis.ui_musteri.SiparisVerActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class UrunListelemeActivity_Siparis extends AppCompatActivity {
    FirebaseFirestore db;
    String firmaUID;
    ArrayList<String> urun_isimleri;
    ArrayList<String> urun_fiyatlari;
    ListView urunlerListView;
    //RecyclerView recyclerView;
    //UrunListRecyclerAdapterM adapter;
    //Not: Bu class içerisinde recyclerview'a dair her şey deaktive edilmiştir.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_listeleme__siparis);
        firmaUID = getIntent().getStringExtra("firmaUID");
        urun_isimleri = new ArrayList<>();
        urun_fiyatlari = new ArrayList<>();
        urunlerListView = findViewById(R.id.listview_urunler);

        DBVeriAL();
        Log.d("Arraysize", "from onCreate: " + urun_isimleri.size());
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
               // ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1, urun_isimleri);
                CustomAdapter customAdapter = new CustomAdapter();
                urunlerListView.setAdapter(customAdapter);

                //RecyclerView
                /*adapter = new UrunListRecyclerAdapterM(urun_isimleri,urun_fiyatlari);
                recyclerView = findViewById(R.id.urunler_musteri_recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);*/
            }
        });
        Log.d("array", "method sonu " + urun_isimleri.size());

    }
   class CustomAdapter extends BaseAdapter
   {

       @Override
       public int getCount() {
           return urun_fiyatlari.size();
       }

       @Override
       public Object getItem(int position) {
           return null;
       }

       @Override
       public long getItemId(int position) {
           return 0;
       }

       @Override
       public View getView(int position, View convertView, ViewGroup parent) {
           convertView = getLayoutInflater().inflate(R.layout.layout_custom_urun,null);
           TextView urun_adi = findViewById(R.id.urun_adi_lv);
           TextView urun_fiyat = findViewById(R.id.urun_fiyat_lv);
           urun_adi.setText(urun_isimleri.get(position));
           urun_fiyat.setText((urun_fiyatlari.get(position)));
           return null;
       }
   }
}
