
/**********************************
 * Öğrenci Numarası: 2170656810   *
 * Öğrenci Ad Soyad: Hakan SÖZGEN *
 **********************************/

package com.yazilimmuhendisligi.yemeksiparis.ui_firma.GelenSiparis;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import com.yazilimmuhendisligi.yemeksiparis.GelenSiparis;
import com.yazilimmuhendisligi.yemeksiparis.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

public class GelenSiparisActivty extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    GelenSiparisAdapter gelenSiparisAdapter;
    RecyclerView recyclerViewGelenSiparis;
    ArrayList<GelenSiparis> gelenSiparisArrayList;
    SharedPreferences sharedPreferencesTimestamp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gelen_siparis);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        gelenSiparisArrayList = new ArrayList<>();

        getDataFromFirestore();

        //RecyclerView
        recyclerViewGelenSiparis = findViewById(R.id.recyclerViewGelenSiparisler);
        recyclerViewGelenSiparis.setLayoutManager(new LinearLayoutManager(this));
        gelenSiparisAdapter = new GelenSiparisAdapter(gelenSiparisArrayList);
        recyclerViewGelenSiparis.setAdapter(gelenSiparisAdapter);
    }

    public void getDataFromFirestore(){

        CollectionReference collectionReference = firebaseFirestore.collection("siparis");

        collectionReference.whereEqualTo("firma_email", firebaseAuth.getCurrentUser().getEmail()).orderBy("siparis_tarih_saat", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Toast.makeText(GelenSiparisActivty.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }

                gelenSiparisArrayList.clear(); // Veriler snapshot olarak anlık listelendiği için her veri geldiğinde tekrarlı listeleme olmasın

                if(queryDocumentSnapshots!=null){

                    for (DocumentSnapshot snapshot:queryDocumentSnapshots.getDocuments()){ //Veri sayısınca döngüye gir ve veri getir

                        Map<String,Object> data = snapshot.getData();

                        String siparisId = snapshot.getId();
                        String firmaEmail = data.get("firma_email").toString();
                        String musteriAdSoyad = data.get("musteri_ad_soyad").toString();
                        String musteriSehir = data.get("musteri_sehir").toString();
                        String musteriAdres = data.get("musteri_adres").toString();
                        String musteriTel = data.get("musteri_tel_no").toString();
                        String siparisDurum = data.get("siparis_durum").toString();
                        Number toplamFiyat =(Number) data.get("toplam_fiyat");
                        String siparisEdilenUrunler = data.get("urunler").toString();

                        Timestamp siparisTarih = (Timestamp) data.get("siparis_tarih_saat");
                        sharedPreferencesTimestamp = getParent().getSharedPreferences("com.yazilimmuhendisligi.yemeksiparis.ui_firma.GelenSiparis",Context.MODE_PRIVATE);
                        sharedPreferencesTimestamp.edit().put

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        String formatliSiparisTarihi= simpleDateFormat.format(siparisTarih.toDate());

                        GelenSiparis gelenSiparis = new GelenSiparis();

                        gelenSiparis.setSiparisId(siparisId);
                        gelenSiparis.setFirmaEmail(firmaEmail);
                        gelenSiparis.setMusteriAdSoyad(musteriAdSoyad);
                        gelenSiparis.setMusteriSehir(musteriSehir);
                        gelenSiparis.setMusteriAdres(musteriAdres);
                        gelenSiparis.setMusteriTel(musteriTel);
                        gelenSiparis.setSiparisDurumu(siparisDurum);
                        gelenSiparis.setToplamFiyat(toplamFiyat.intValue());
                        gelenSiparis.setSiparisUrunler(siparisEdilenUrunler);
                        gelenSiparis.setSiparisTarihSaat(formatliSiparisTarihi);

                        gelenSiparisArrayList.add(gelenSiparis);

                        gelenSiparisAdapter.notifyDataSetChanged();

                    }
                }
                if(queryDocumentSnapshots.size()==0){
                    Toast.makeText(GelenSiparisActivty.this,"Henüz siparişiniz yok",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
