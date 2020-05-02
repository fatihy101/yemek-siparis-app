package com.yazilimmuhendisligi.yemeksiparis.ui_admin;

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
import com.yazilimmuhendisligi.yemeksiparis.ui_admin.FirmaKaraliste.FirmaKaraliste;
import com.yazilimmuhendisligi.yemeksiparis.ui_admin.FirmaListeleAdmin.FirmaListeleAdmin;
import com.yazilimmuhendisligi.yemeksiparis.ui_admin.MusteriKaraliste.MusteriKaraliste;
import com.yazilimmuhendisligi.yemeksiparis.ui_admin.MusteriListeleAdmin.MusteriListeleAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class adminActivity extends AppCompatActivity {
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        //Buradan sonra yazmaya başlayın.
        auth = FirebaseAuth.getInstance();

    }
    public  void  karaliste_firmaButton(View view)
    {
        Intent intent = new Intent(getApplicationContext(), FirmaKaraliste.class);
        startActivity(intent);
    }
    public void karaliste_musteriButton(View view)
    {
        Intent intent = new Intent(getApplicationContext(), MusteriKaraliste.class);
        startActivity(intent);
    }
public void firmalariListeleButton(View view)
{
    Intent intent = new Intent(getApplicationContext(), FirmaListeleAdmin.class);
    startActivity(intent);
}


public void musteriListeleButton(View view)
{
    Intent intent = new Intent(getApplicationContext(), MusteriListeleAdmin.class);
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

}
