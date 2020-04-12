package com.yazilimmuhendisligi.yemeksiparis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.AutoText;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class girisActivity extends AppCompatActivity {
/* Authentication ID'leri
* 0 = Kullanıcı
* 1 = Firma
* 2 = Admin*/

    EditText email, parola;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.signOut(); //test amaçlı
        FirebaseUser currentUser = mAuth.getCurrentUser();
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
                    Object testObject = YetkiveriAlFirebase(email.getText().toString());
                    if (testObject == null) System.out.println("Test object null"); // Test amaçlı
                    YetkiyeGoreYonlendirme(testObject);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(girisActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }



    public void girisYapmisMi(FirebaseUser user) //Kontrolü "onStart"ta yapılır.
    {
        if(user != null)
        {
            Toast.makeText(this, "Zaten giriş yapmışsınız.", Toast.LENGTH_LONG).show(); //Geçici olarak bir Toast mesajı bıraktım.
            //Gerekli ekrana yönlendirsin. TODO: Kullanıcılar oluştur ve kullanıcıların yetki seviyelerine göre yönlendirmeyi direkt yap.
        }
    }

    public void YetkiyeGoreYonlendirme(Object idNo)
    {Intent intent;
        System.out.println("YetkiyeGoreYonlendirme.class'a ulaşıldı... " + idNo);
        String idTemp = "0"; // FIXME
        switch (idTemp)
        {
            case "1":
                intent = new Intent(girisActivity.this,musteri.class);
                startActivity(intent);
                break;
            case "2":
                 intent = new Intent(girisActivity.this,firmaActivity.class);
                startActivity(intent);
                break;
            case "3":
                 intent = new Intent(girisActivity.this,adminMain.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

    }

    public Object YetkiveriAlFirebase(String email) //Kullanıcının yetki seviyesini DB'den çekmek için.
    {
        System.out.println("yetkiveriAlFirebase'e ulaşıldı.");
        final Object[] yetkiIDObj = new Object[1];
        CollectionReference kullaniciRef = firebaseFirestore.collection("kullanici_bilgileri");
        final Query query = kullaniciRef.whereEqualTo("email",email);
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        System.out.println("Devam"); // Uygulama buraya hiç gelmiyor FIXME
                    if(task.isSuccessful())
                    {

                              for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult()))
                              {
                                Map<String,Object> kullaniciData = document.getData();
                               yetkiIDObj[0] = kullaniciData.get("yetki_id");
                                  System.out.println("ID: "+ yetkiIDObj[0]);
                              }
                    }
                    else
                    {
                        Toast.makeText(girisActivity.this, "Veritabanında email'a rastlanmadı.", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                });
    return yetkiIDObj;
    }

    //On Click methodları
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
