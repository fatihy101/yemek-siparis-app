package com.yazilimmuhendisligi.yemeksiparis.ui_firma;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.yazilimmuhendisligi.yemeksiparis.R;
import com.yazilimmuhendisligi.yemeksiparis.girisActivity;
import com.yazilimmuhendisligi.yemeksiparis.ui_firma.FirmaBilgileriGuncelle.FirmaBilgileriGuncelle;
import com.yazilimmuhendisligi.yemeksiparis.ui_firma.GelenSiparis.GelenSiparisActivty;
import com.yazilimmuhendisligi.yemeksiparis.ui_firma.UrunMenu.UrunMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.w3c.dom.Document;

public class firmaActivity extends AppCompatActivity {
FirebaseAuth auth;
TextView onay_bilgi;
FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firma);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        onay_bilgi = findViewById(R.id.onay_bilgi_tv_firma);
        auth = FirebaseAuth.getInstance();
        firestore  = FirebaseFirestore.getInstance();
        getOnayData();
    }


    public void restoranMenusuneGit(View view)
    {
        Intent intent = new Intent(getApplicationContext(), UrunMenu.class);
        startActivity(intent);
    }

    public void firmaGuncelleGit(View view)
    {
        Intent intent = new Intent(getApplicationContext(), FirmaBilgileriGuncelle.class);
        startActivity(intent);
    }

    //region Sağ üstte açılan menü için metotlar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.ust_sag_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.cikis_yap:
                auth.signOut();
                Toast.makeText(this, "Başarıyla çıkış yapıldı.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), girisActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

    public void gelenSiparislerActivity (View view){
        Intent intent = new Intent(getApplicationContext(), GelenSiparisActivty.class);
        startActivity(intent);
    }

    public void getOnayData()
    {
        DocumentReference reference = firestore.collection("kullanici_bilgileri")
                .document(auth.getCurrentUser().getUid());
        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                onayKontrol(documentSnapshot.getBoolean("admin_onayi"));
            }
        });
    }

    public void onayKontrol(boolean isAdminConfirm)
    {
        System.out.println("onay val: " + isAdminConfirm);
        if(isAdminConfirm)
        {
            onay_bilgi.setText("Hesabınız admin tarafından onaylandı!");
            onay_bilgi.setBackgroundResource(R.color.onayColor);
        }
        else
        {
            onay_bilgi.setText("Hesabınız admin tarafından henüz onaylanmadı!");
            onay_bilgi.setBackgroundResource(R.color.alternativeColor);

        }
    }
}
