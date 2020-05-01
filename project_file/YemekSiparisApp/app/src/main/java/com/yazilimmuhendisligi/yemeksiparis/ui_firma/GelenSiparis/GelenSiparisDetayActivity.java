
/**********************************
 * Öğrenci Numarası: 2170656810   *
 * Öğrenci Ad Soyad: Hakan SÖZGEN *
 **********************************/

package com.yazilimmuhendisligi.yemeksiparis.ui_firma.GelenSiparis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yazilimmuhendisligi.yemeksiparis.R;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class GelenSiparisDetayActivity extends AppCompatActivity {

    TextView textViewSiparisDurumu, textViewFirmaEmail, textViewMusteriAdSoyad, textViewMusteriAdres, textViewMusteriSehir, textViewMusteriTelefon,
            textViewSiparisZamani, textViewSiparisEdilenUrunler, textViewToplamSiparisTutari, textViewDetayPrepare, textViewDetayDeliver, textViewDetayDelivered;

    ImageView imageViewSiparisDurumDetay;

    ImageButton imageButtonSiparisDetayPrepare, imageButtonSiparisDetayDelivery, imageButtonSiparisDetayDelivered;

    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gelen_siparis_detay);

        //Kontrolleri tanımla
        textViewSiparisDurumu = findViewById(R.id.textViewSiparisDetaySiparisDurumu);
        textViewFirmaEmail= findViewById(R.id.textViewSiparisDetayFirmaEmail);
        textViewMusteriAdSoyad = findViewById(R.id.textViewSiparisDetayMusteriAdSoyad);
        textViewMusteriAdres = findViewById(R.id.textViewSiparisDetayMusteriAdres);
        textViewMusteriSehir= findViewById(R.id.textViewSiparisDetayMusteriSehir);
        textViewMusteriTelefon = findViewById(R.id.textViewSiparisDetayMusteriTel);
        textViewSiparisZamani = findViewById(R.id.textViewSiparisDetayMusteriTarihSaat);
        textViewSiparisEdilenUrunler = findViewById(R.id.textViewSiparisDetaySiparisUrunler);
        textViewToplamSiparisTutari = findViewById(R.id.textViewSiparisDetayToplamFiyat);
        textViewDetayPrepare = findViewById(R.id.textViewDetayPrepare);
        textViewDetayDeliver = findViewById(R.id.textViewDetayDeliver);
        textViewDetayDelivered = findViewById(R.id.textViewDetayDelivered);
        imageViewSiparisDurumDetay = findViewById(R.id.imageViewSiparisDetayDurumu);
        imageButtonSiparisDetayPrepare = findViewById(R.id.imageButtonSiparisDetayPrepare);
        imageButtonSiparisDetayDelivery = findViewById(R.id.imageButtonSiparisDetayDelivery);
        imageButtonSiparisDetayDelivered = findViewById(R.id.imageButtonSiparisDetayDelivered);

        //Kontrollere değerleri getItem'dan ata
        textViewSiparisDurumu.setText(" Sipariş Durumu: "+ getIntent().getStringExtra("SiparisDurumu"));
        textViewFirmaEmail.setText(" Firma Email: "+getIntent().getStringExtra("FirmaEmail"));
        textViewMusteriAdSoyad.setText(" Müşteri Ad Soyad: "+getIntent().getStringExtra("MusteriAdSoyad"));
        textViewMusteriAdres.setText(" Müşteri Adres: "+getIntent().getStringExtra("MusteriAdres"));
        textViewMusteriSehir.setText(" Şehir: "+getIntent().getStringExtra("MusteriSehir"));
        textViewMusteriTelefon.setText(" Müşteri Telefon: "+ getIntent().getStringExtra("MusteriTel"));
        textViewSiparisZamani.setText(" Sipariz Zamanı: "+ getIntent().getStringExtra("SiparisTarihSaat"));
        textViewSiparisEdilenUrunler.setText(" Sipariş Edilen Ürünler: "+getIntent().getStringExtra("SiparisUrunler"));
        textViewToplamSiparisTutari.setText(" Toplam Sipariş Tutarı: "+getIntent().getIntExtra("ToplamFiyat",0)+"-TL");

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        //getIdem dan gelen sipariş durumuna göre sipariş durum image ini setle
        CharSequence siparisDurumuText = getIntent().getStringExtra("SiparisDurumu");

        switch ( siparisDurumuText.toString()){
            case "Yeni Sipariş":
                imageViewSiparisDurumDetay.setImageResource(R.drawable.new_order);
                imageButtonSiparisDetayDelivery.setEnabled(false); imageButtonSiparisDetayDelivery.setAlpha((float)0.2);
                imageButtonSiparisDetayDelivered.setEnabled(false); imageButtonSiparisDetayDelivered.setAlpha((float)0.2);
                textViewDetayDeliver.setAlpha((float)0.2); textViewDetayDelivered.setAlpha((float)0.2);
                break;
            case "Hazırlanıyor":
                imageViewSiparisDurumDetay.setImageResource(R.drawable.preparing);
                imageButtonSiparisDetayPrepare.setEnabled(false); imageButtonSiparisDetayPrepare.setAlpha((float)0.2);
                imageButtonSiparisDetayDelivered.setEnabled(false); imageButtonSiparisDetayDelivered.setAlpha((float)0.2);
                textViewDetayPrepare.setAlpha((float)0.2);
                textViewDetayDelivered.setAlpha((float) 0.2);
                break;
            case "Yolda":
                imageViewSiparisDurumDetay.setImageResource(R.drawable.delivery);
                imageButtonSiparisDetayPrepare.setEnabled(false); imageButtonSiparisDetayPrepare.setAlpha((float)0.2);
                imageButtonSiparisDetayDelivery.setEnabled(false); imageButtonSiparisDetayDelivery.setAlpha((float)0.2);
                textViewDetayPrepare.setAlpha((float)0.2);
                textViewDetayDeliver.setAlpha((float)0.2);
                break;
            case "Teslim Edildi":
                imageViewSiparisDurumDetay.setImageResource(R.drawable.delivered);
                imageButtonSiparisDetayPrepare.setEnabled(false); imageButtonSiparisDetayPrepare.setAlpha((float)0.2);
                imageButtonSiparisDetayDelivery.setEnabled(false); imageButtonSiparisDetayDelivery.setAlpha((float)0.2);
                imageButtonSiparisDetayDelivered.setEnabled(false); imageButtonSiparisDetayDelivered.setAlpha((float)0.2);
                textViewDetayPrepare.setAlpha((float)0.2);
                textViewDetayDeliver.setAlpha((float)0.2);
                textViewDetayDelivered.setAlpha((float) 0.2);
                break;
            default:
        }

    }

    public void durumSetPrepare(View view){

        System.out.println(getIntent().getStringExtra("SiparisId"));

        //Timestamp tarihSaat = new Timestamp(new Date(getIntent().getStringExtra("SiparisTarihSaat")));

        Map<String, Object> durumBilgisiMap = new HashMap<>();
        durumBilgisiMap.put("firma_email",getIntent().getStringExtra("FirmaEmail"));
        durumBilgisiMap.put("musteri_ad_soyad", getIntent().getStringExtra("MusteriAdSoyad"));
        durumBilgisiMap.put("musteri_sehir", getIntent().getStringExtra("MusteriSehir"));
        durumBilgisiMap.put("musteri_adres", getIntent().getStringExtra("MusteriAdres"));
        durumBilgisiMap.put("musteri_tel_no", getIntent().getStringExtra("MusteriTel"));
        durumBilgisiMap.put("toplam_fiyat", getIntent().getIntExtra("ToplamFiyat",0));
        durumBilgisiMap.put("urunler", getIntent().getStringExtra("SiparisUrunler"));
        durumBilgisiMap.put("siparis_tarih_saat", FieldValue.serverTimestamp());

        durumBilgisiMap.put("siparis_durum", "Hazırlanıyor");

        DocumentReference durumRef = db.document("siparis/"+ getIntent().getStringExtra("SiparisId"));

            durumRef.set(durumBilgisiMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(GelenSiparisDetayActivity.this, "Durum başarıyla güncellendi.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),GelenSiparisActivty.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// Açık olan tüm activity leri kapat
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(GelenSiparisDetayActivity.this, "Error: " +e.getLocalizedMessage() , Toast.LENGTH_SHORT).show();
                }
            });




    }
}
