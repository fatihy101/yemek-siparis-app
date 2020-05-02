package com.yazilimmuhendisligi.yemeksiparis.ui_firma.UrunMenu;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.LogDescriptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.yazilimmuhendisligi.yemeksiparis.R;

import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;

class UrunMenuAdapter extends RecyclerView.Adapter<UrunMenuAdapter.urunRow> {
    private ArrayList<String> urun_isim, urun_fiyat,urun_doc_id;
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    DocumentReference documentReference;

    public UrunMenuAdapter(ArrayList<String> urun_isim, ArrayList<String> urun_fiyat, ArrayList<String> urun_doc_id) {
        this.urun_isim = urun_isim;
        this.urun_fiyat = urun_fiyat;
        this.urun_doc_id = urun_doc_id;
    }

    @NonNull
    @Override
    public urunRow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        View view = layoutInflater.inflate(R.layout.satir_urun_menu,parent,false);
        return new urunRow(view);
    }

    @Override
    public void onBindViewHolder(@NonNull urunRow holder, final int position) {
    holder.isim.setText(urun_isim.get(position));
    holder.fiyat.setText(urun_fiyat.get(position) + " TL");
    holder.sil.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            documentReference = firestore.collection("kullanici_bilgileri/"+ mAuth.getCurrentUser().getUid()+"/menu")
                    .document(urun_doc_id.get(position));
            documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("Sil", "onSuccess: Ürün başarıyla silindi!");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Sil", "onFailure: " +e.getLocalizedMessage());
                }
            });

            urun_isim.remove(position);
            urun_fiyat.remove(position);
            urun_doc_id.remove(position);
            notifyDataSetChanged();
        }
    });
    }

    @Override
    public int getItemCount() {
        return urun_fiyat.size();
    }

    public class urunRow extends RecyclerView.ViewHolder {
        TextView isim, fiyat;
        Button sil;
        public urunRow(@NonNull View itemView) {
            super(itemView);
            isim = itemView.findViewById(R.id.urun_ismi_firma_guncelle);
            fiyat = itemView.findViewById(R.id.urun_fiyat_firma_guncelle);
            sil = itemView.findViewById(R.id.urun_sil_firma);
        }
    }


}
