package com.yazilimmuhendisligi.yemeksiparis.ui_musteri.Sepet;

import android.util.Log;
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
    public void onBindViewHolder(@NonNull sepetSatir holder, final int position) {
    holder.urun_ismi.setText(urun_isimleri.get(position));
    holder.fiyat.setText(String.format("%s TL", urun_fiyatlar.get(position)));

    holder.cikar_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            urun_isimleri.remove(position);
            urun_fiyatlar.remove(position);
            System.out.println(urun_isimleri);
            notifyDataSetChanged();
        }
    });
    }

    @Override
    public int getItemCount() {

        return urun_isimleri.size();
    }

    public class sepetSatir extends RecyclerView.ViewHolder {
        Button cikar_button;
        TextView urun_ismi, fiyat;
        public sepetSatir(@NonNull View itemView) {
            super(itemView);
            cikar_button = itemView.findViewById(R.id.urun_cikar);
            urun_ismi = itemView.findViewById(R.id.urun_ismi_liste_2);
            fiyat  =itemView.findViewById(R.id.urun_fiyat_liste_2);

        }
    }
}
