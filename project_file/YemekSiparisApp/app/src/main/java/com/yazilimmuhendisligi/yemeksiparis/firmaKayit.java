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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class firmaKayit extends AppCompatActivity {

    EditText email, firmaAdi, firmaSahibiAdSoyad,telefonNo, parola,parolaRe, bos;
    Button onayButton;
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firma_kayit);
        email = findViewById(R.id.kayit_firma_email);
        firmaAdi = findViewById(R.id.kayit_firma_adi);
        firmaSahibiAdSoyad = findViewById(R.id.kayit_firma_sahibi_adsoyad);
        telefonNo = findViewById(R.id.kayit_tel_firma);
        parola = findViewById(R.id.kayit_parola_firma);
        parolaRe = findViewById(R.id.kayit_firma_parola_re);

        onayButton = findViewById(R.id.firma_kayit_ol_button);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        email.addTextChangedListener(firmaKayitTextWatcher);
        firmaAdi.addTextChangedListener(firmaKayitTextWatcher);
        firmaSahibiAdSoyad.addTextChangedListener(firmaKayitTextWatcher);
        telefonNo.addTextChangedListener(firmaKayitTextWatcher);
        parola.addTextChangedListener(firmaKayitTextWatcher);
        parolaRe.addTextChangedListener(firmaKayitTextWatcher);

    }

    public void firmaKayitOLButton(View view)
    {
        if(parolaUyumKontrolu()) {
            mAuth.createUserWithEmailAndPassword(email.getText().toString(),parola.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    String uid = authResult.getUser().getUid(); //Döküman adı için gerekli
                    Map<String, Object> kullaniciBilgileri = new HashMap<>();
                    kullaniciBilgileri.put("firma_adi",firmaAdi.getText().toString());
                    kullaniciBilgileri.put("firma_sahibi_ad_soyad",firmaSahibiAdSoyad.getText().toString());
                    kullaniciBilgileri.put("tel_no",telefonNo.getText().toString());
                    kullaniciBilgileri.put("email",email.getText().toString());
                    kullaniciBilgileri.put("yetki_id","2"); //Yetki id'si.
                    kullaniciBilgileri.put("admin_onayi",false);


                    db.collection("kullanici_bilgileri").document(uid)
                            .set(kullaniciBilgileri).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(firmaKayit.this, "Kayıt başarılı", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), girisActivity.class);
                            startActivity(intent);

                        }
                    }).addOnFailureListener(new OnFailureListener() { //Kötü durum senaryosu
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(firmaKayit.this, "Hata: "+e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
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

    private TextWatcher firmaKayitTextWatcher = new TextWatcher() { //Eğer tüm alanlar doldurulmadıysa butonun deaktive olması için metot.
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            ArrayList<EditText> input = new ArrayList<>();
            input.add(firmaSahibiAdSoyad); input.add(firmaAdi); input.add(telefonNo); input.add(parola); input.add(parolaRe);
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
