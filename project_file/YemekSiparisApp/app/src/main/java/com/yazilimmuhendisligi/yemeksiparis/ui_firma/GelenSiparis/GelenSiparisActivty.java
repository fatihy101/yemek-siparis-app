
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
import android.widget.TextView;
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
    TextView textViewGelenSiparisTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gelen_siparis);

        firebaseAuth = FirebaseAuth.getInstance(); // Veritabanından kullanıcı nesnesini örnekle
        firebaseFirestore = FirebaseFirestore.getInstance(); //Veritabanı nesnesini örnekle

        gelenSiparisArrayList = new ArrayList<>(); //GelenSiparis sınıfından bir Arraylist oluştur.

        getDataFromFirestore(); //Verileri veritabanından getiren metodu çağır

        //Verileri listeleyen RecyclerView elemanını tanımla
        recyclerViewGelenSiparis = findViewById(R.id.recyclerViewGelenSiparisler);
        recyclerViewGelenSiparis.setLayoutManager(new LinearLayoutManager(this));

        //GelenSiparisAdapter sınıfını initialize et ve parametre olarak gelensSiparisArrayList i ver
        gelenSiparisAdapter = new GelenSiparisAdapter(gelenSiparisArrayList);
        recyclerViewGelenSiparis.setAdapter(gelenSiparisAdapter); //Recyclerview a gelenSiparisAdapter i bağla

        //GELEN SİPARİŞLER ekranının başlığında kullanıcı emailini göster
        textViewGelenSiparisTitle = findViewById(R.id.textViewTitleGelenSiparisler);
        textViewGelenSiparisTitle.setText("   GELEN SİPARİŞLER ("+firebaseAuth.getCurrentUser().getEmail()+")");
    }

    //Listeyi doldurmak için veritabanından veri çeken metod
    public void getDataFromFirestore(){

        CollectionReference collectionReference = firebaseFirestore.collection("siparis");//Veritabanı tablosunu referans al

        //Referans sorgusunu oluştur. Snapshotlistener ile verileri anlık al
        collectionReference.whereEqualTo("firma_email", firebaseAuth.getCurrentUser()
                .getEmail()).orderBy("siparis_tarih_saat", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e!=null){
                            Toast.makeText(GelenSiparisActivty.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }
                        //Exceptionda hata yoksa önce tekrarlı verileri temizle
                        gelenSiparisArrayList.clear();

                        if(queryDocumentSnapshots!=null){ //Anlık veri varsa snapshopttan data map ine verileri column bazında döngü boyunca ata

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

                                Timestamp siparisTarih = (Timestamp) data.get("siparis_tarih_saat");//Firebasden düzensiz gelen tarihi ele al

                                if(siparisTarih!=null) { // Sipariş Detay ekranındaki Durum güncellemesi sonrası bu if bloğu gerekli. Yoksa listelenmez.

                                    //Tarihi insanın anlayabileceği hale getir
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                    String formatliSiparisTarihi = simpleDateFormat.format(siparisTarih.toDate());

                                    GelenSiparis gelenSiparis = new GelenSiparis(); // Veritabanından gelen verileri aktarmak için gelen sipariş objesi oluştur

                                    //Objenin elemanlarına verileri tek tek aktar
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

                                    gelenSiparisArrayList.add(gelenSiparis); // GelenSiparis sınıfından oluşturulan ArrayListe gelen sipariş nesnesini döngü boyunca aktar

                                    gelenSiparisAdapter.notifyDataSetChanged(); //Adapter a değeşiklik olduğunu bildir
                                }

                            }
                        }
                        if(queryDocumentSnapshots.size()==0){
                            Toast.makeText(GelenSiparisActivty.this,"Henüz siparişiniz yok",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
