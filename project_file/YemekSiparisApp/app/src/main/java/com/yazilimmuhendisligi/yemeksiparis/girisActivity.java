package com.yazilimmuhendisligi.yemeksiparis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.AutoText;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class girisActivity extends AppCompatActivity {

    EditText email, parola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        email = findViewById(R.id.giris_email_text);
        parola = findViewById(R.id.giris_parola_text);
    }

    public void girisYapButton(View view)
    {
        if(email.getText().toString().equals("") || parola.getText().toString().equals(""))
        {
            Toast.makeText(this, "Lütfen e-posta ve parola alanını doldurduğunuza emin olunuz", Toast.LENGTH_LONG).show();
        }
        else
        {
            //Firebase'den giriş kontrolü
        }
    }

    public void kullaniciKayitButton(View view)
    {
        Intent intent = new Intent(getApplicationContext(),musteriKayit.class);
        startActivity(intent);
    }

    public void restoranKayitButton(View view)
    {
        Intent intent = new Intent(getApplicationContext(),musteriKayit.class);
        startActivity(intent);
    }
    public void parolaUnuttumButton (View view)
    {
        Intent intent = new Intent(getApplicationContext(),parolaUnuttum.class);
        startActivity(intent);
    }

}
