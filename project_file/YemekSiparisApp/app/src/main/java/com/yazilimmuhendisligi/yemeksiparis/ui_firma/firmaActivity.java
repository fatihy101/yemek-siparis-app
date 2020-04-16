package com.yazilimmuhendisligi.yemeksiparis.ui_firma;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.yazilimmuhendisligi.yemeksiparis.R;
import com.yazilimmuhendisligi.yemeksiparis.girisActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class firmaActivity extends AppCompatActivity {
FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firma);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        //Buradan sonra yazmaya başlayın.
        auth = FirebaseAuth.getInstance();
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
