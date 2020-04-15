package com.yazilimmuhendisligi.yemeksiparis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class girisActivity extends AppCompatActivity {
/* Authentication ID'leri
* 1 = Musteri
* 2 = Firma
* 3 = Admin*/

    EditText email, parola;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    Kullanici kullanici;
    FirebaseUser currentUser;

    @Override
    protected void onStart() {
        super.onStart();
        girisYapmisMi(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        email = findViewById(R.id.giris_email_text);
        parola = findViewById(R.id.giris_parola_text);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public void girisYapmisMi(FirebaseUser user) //Kontrolü "onStart"ta yapılır.
    {
        if(user != null)
        {
            Toast.makeText(this, "Zaten giriş yapmışsınız.", Toast.LENGTH_LONG).show(); //Geçici olarak bir Toast mesajı bıraktım.
            yetkiVerisiniAl();
        }
    }

    public void YetkiyeGoreYonlendirme(String idNo)
    {Intent intent;
        System.out.println("YetkiyeGoreYonlendirme.class'a ulaşıldı... " + idNo);
        switch (idNo)
        {
            case "1":
                intent = new Intent(girisActivity.this, musteriActivity.class);
                startActivity(intent);
                break;
            case "2":
                 intent = new Intent(girisActivity.this,firmaActivity.class);
                startActivity(intent);
                break;
            case "3":
                 intent = new Intent(girisActivity.this, adminActivity.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

    }


    public void yetkiVerisiniAl()
    {

        DocumentReference docRef = firebaseFirestore.collection("kullanici_bilgileri").document(mAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                 String yetkiID = documentSnapshot.getString("yetki_id");

                 YetkiyeGoreYonlendirme(yetkiID);
                }
                else
                {
                    System.out.println("ERROR: Kayıt bulunamadı");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    //region On-Click Metotları
    public void girisYapButton(View view)
    {

        if(email.getText().toString().equals("") || parola.getText().toString().equals(""))
        {
            Toast.makeText(this, "Lütfen e-posta ve parola alanını doldurduğunuza emin olunuz", Toast.LENGTH_LONG).show();
        }
        else
        {
            mAuth.signInWithEmailAndPassword(email.getText().toString(),parola.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(girisActivity.this, "Bilgiler doğru!", Toast.LENGTH_SHORT).show();
                    currentUser = mAuth.getCurrentUser();
                    kullanici = new Kullanici(currentUser.getEmail(), currentUser.getUid());
                    yetkiVerisiniAl();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(girisActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void kullaniciKayitButton(View view)
    {
        Intent intent = new Intent(getApplicationContext(),musteriKayit.class);
        startActivity(intent);
    }

    public void restoranKayitButton(View view)
    {
        Intent intent = new Intent(getApplicationContext(),firmaKayit.class);
        startActivity(intent);
    }
    public void parolaUnuttumButton (View view)
    {
        Intent intent = new Intent(getApplicationContext(),parolaUnuttum.class);
        startActivity(intent);
    }
    //endregion

}
