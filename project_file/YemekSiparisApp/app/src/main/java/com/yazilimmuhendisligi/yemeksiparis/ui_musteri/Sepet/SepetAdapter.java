package com.yazilimmuhendisligi.yemeksiparis.ui_musteri.Sepet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yazilimmuhendisligi.yemeksiparis.R;

import java.util.ArrayList;

public class SepetAdapter extends RecyclerView.Adapter<SepetAdapter.sepetSatir> {
    private ArrayList<String>  urun_isimleri;
    private ArrayList<String> urun_fiyatlar;

    public SepetAdapter(ArrayList<String> urun_isimleri, ArrayList<String> urun_fiyatlar) {
        this.urun_isimleri = urun_isimleri;
        this.urun_fiyatlar = urun_fiyatlar;
    }

    @NonNull
    @Override
    public sepetSatir onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater  = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sepet_satir,parent,false);
        return new sepetSatir(view);
    }

    @Override
    public void onBindViewHolder(@NonNull sepetSatir holder, int position) {
    holder.urun_ismi.setText(urun_isimleri.get(position));
    holder.fiyat.setText(String.format("%s TL", urun_fiyatlar.get(position)));

    holder.onayla.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    });
    }

    @Override
    public int getItemCount() {
        return urun_fiyatlar.size();
    }

    public class sepetSatir extends RecyclerView.ViewHolder {
        Button onayla;
        TextView urun_ismi, fiyat;
        public sepetSatir(@NonNull View itemView) {
            super(itemView);
            onayla = itemView.findViewById(R.id.siparis_onayla_button);
            urun_ismi = itemView.findViewById(R.id.sepet_isim);
            fiyat  =itemView.findViewById(R.id.sepet_fiyat);

        }
    }
}
