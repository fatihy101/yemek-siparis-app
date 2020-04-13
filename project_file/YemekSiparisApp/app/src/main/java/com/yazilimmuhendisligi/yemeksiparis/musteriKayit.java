package com.yazilimmuhendisligi.yemeksiparis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class musteriKayit extends AppCompatActivity {
EditText ad, soyad, email, parola, parolaRe, telefonNo, bos;
Button onayButton;
FirebaseFirestore db;
FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musteri_kayit);
        ad = findViewById(R.id.musteri_kayit_ad);
        soyad = findViewById(R.id.musteri_kayit_soyad);
        email = findViewById(R.id.musteri_kayit_email);
        parola = findViewById(R.id.musteri_kayit_parola);
        parolaRe = findViewById(R.id.musteri_kayit_parola_re) ;
        telefonNo = findViewById(R.id.musteri_kayit_tel);
        onayButton = findViewById(R.id.musteri_kayit_ol_button);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        ad.addTextChangedListener(musteriKayitTextWatcher);
        soyad.addTextChangedListener(musteriKayitTextWatcher);
        email.addTextChangedListener(musteriKayitTextWatcher);
        parola.addTextChangedListener(musteriKayitTextWatcher);
        parolaRe.addTextChangedListener(musteriKayitTextWatcher);
        telefonNo.addTextChangedListener(musteriKayitTextWatcher);

    }

    public void kayitOlMusteriButton(View view)
    {
        if(parolaUyumKontrolu()) {
            mAuth.createUserWithEmailAndPassword(email.getText().toString(),parola.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String uid = authResult.getUser().getUid(); //Döküman adı için gerekli
                Map<String, Object> kullaniciBilgileri = new HashMap<>();
                kullaniciBilgileri.put("ad",ad.getText().toString());
                kullaniciBilgileri.put("soyad",soyad.getText().toString());
                kullaniciBilgileri.put("tel_no",telefonNo.getText().toString());
                kullaniciBilgileri.put("email",email.getText().toString());
                kullaniciBilgileri.put("yetki_id","1"); //Yetki id'si.


                    db.collection("kullanici_bilgileri").document(uid)
                            .set(kullaniciBilgileri).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Intent intent = new Intent(musteriKayit.this, girisActivity.class);
                            startActivity(intent);

                        }
                    }).addOnFailureListener(new OnFailureListener() { //Kötü durum senaryosu
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(musteriKayit.this, "Hata: "+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            //Hatalı oluşturulan kullanıcıyı silmek için bir metot
                            mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(!task.isComplete()) System.out.println("Kullanıcı oluşturulurken bir hata oluştu. ");
                                }
                            });
                        }
                    });
                }
        }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }

    }

private TextWatcher musteriKayitTextWatcher = new TextWatcher() { //Eğer tüm alanlar doldurulmadıysa butonun deaktive olması için metot.
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        ArrayList<EditText> input = new ArrayList<>();
        input.add(ad); input.add(soyad); input.add(telefonNo); input.add(parola); input.add(parolaRe);
        input.add(email);
        boolean[] booleanDizi = new boolean[input.size()];
        for (int i = 0; i < input.size(); i++)
        {
            bos = input.get(i);
             booleanDizi[i] = bos.getText().toString().trim().isEmpty();
        }
        onayButton.setEnabled(!booleanDizi[0] && !booleanDizi[1] && !booleanDizi[2] && !booleanDizi[3] && !booleanDizi[4] && !booleanDizi[5]);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
};

    public boolean parolaUyumKontrolu()
    {
        if(!parola.getText().toString().equals(parolaRe.getText().toString()))
        {
            Toast.makeText(this, "Girdiğiniz parola ve tekrarı uyuşmamaktadır.", Toast.LENGTH_LONG).show();
            return false;
        }
        else return true;
    }

}
