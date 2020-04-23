package com.yazilimmuhendisligi.yemeksiparis.ui_musteri;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.yazilimmuhendisligi.yemeksiparis.R;
import com.yazilimmuhendisligi.yemeksiparis.girisActivity;
import com.yazilimmuhendisligi.yemeksiparis.ui_musteri.GecmisSiparisMusteri.GecmisSiparisMusteri;
import com.yazilimmuhendisligi.yemeksiparis.ui_musteri.MusteriBilgileriGuncelle.MusteriBilgileriGuncelle;
import com.yazilimmuhendisligi.yemeksiparis.ui_musteri.Sepet.Sepet;
import com.yazilimmuhendisligi.yemeksiparis.ui_musteri.SiparisVerActivity.SiparisVerActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class musteriActivity extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteri);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        //Buradan sonra yazmaya başlayın.
        auth = FirebaseAuth.getInstance();
    }
    //region Buttonlar için intent Methodları
    public void musteriBilgileriGuncelleGit(View view)
    {
        Intent intent = new Intent(getApplicationContext(), MusteriBilgileriGuncelle.class);
        startActivity(intent);
    }

    public void sepetActivityGit(View view)
    {
        Intent intent = new Intent(getApplicationContext(), Sepet.class);
        startActivity(intent);
    }

    public void gecmisSiparisActivityGit(View view)
    {
        Intent intent = new Intent(getApplicationContext(), GecmisSiparisMusteri.class);
        startActivity(intent);
    }

    public void siparisVerActivityGit(View view)
    {
        Intent intent = new Intent(getApplicationContext(), SiparisVerActivity.class);
        startActivity(intent);
    }
//endregion

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

}
