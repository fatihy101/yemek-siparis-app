package com.yazilimmuhendisligi.yemeksiparis.ui_musteri.SiparisVerActivity;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yazilimmuhendisligi.yemeksiparis.R;

import java.util.ArrayList;

public class UrunListRecyclerAdapterM extends RecyclerView.Adapter<UrunListRecyclerAdapterM.urunSatir> {
    private ArrayList<String>  urun_isimleri;
    private ArrayList<String> urun_fiyatlar;


    public UrunListRecyclerAdapterM(ArrayList<String> urun_isimleri, ArrayList<String> urun_fiyatlar) {
        this.urun_isimleri = urun_isimleri;
        this.urun_fiyatlar = urun_fiyatlar;
    }


    @NonNull
    @Override
    public urunSatir onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.satir_urun_liste,parent,false);
        return new urunSatir(view);
    }

    @Override
    public void onBindViewHolder(@NonNull urunSatir holder, int position) {

        holder.urun_ismi.setText(urun_isimleri.get(position));
        holder.fiyat.setText(urun_fiyatlar.get(position));

        holder.azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return urun_isimleri.size();
    }

    public class urunSatir extends RecyclerView.ViewHolder {
        Button arttir, azalt;
        TextView urun_ismi, fiyat, sayac;
        public urunSatir(@NonNull View itemView) {
            super(itemView);
            arttir = itemView.findViewById(R.id.urun_arttir);
            azalt = itemView.findViewById(R.id.urun_azalt);
            urun_ismi = itemView.findViewById(R.id.urun_ismi_liste);
            fiyat = itemView.findViewById(R.id.urun_fiyat_liste);
            sayac = itemView.findViewById(R.id.urun_sayac);


        }
    }
}
