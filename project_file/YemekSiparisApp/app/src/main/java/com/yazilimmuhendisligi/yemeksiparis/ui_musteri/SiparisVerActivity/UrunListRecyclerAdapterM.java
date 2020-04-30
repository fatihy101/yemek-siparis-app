package com.yazilimmuhendisligi.yemeksiparis.ui_musteri.SiparisVerActivity;

import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yazilimmuhendisligi.yemeksiparis.R;
import com.yazilimmuhendisligi.yemeksiparis.Siparis;

import java.util.ArrayList;

public class UrunListRecyclerAdapterM extends RecyclerView.Adapter<UrunListRecyclerAdapterM.urunSatir> {
    private ArrayList<String>  urun_isimleri;
    private ArrayList<String> urun_fiyatlar;
    Siparis siparis;

//Constructor
    public UrunListRecyclerAdapterM(ArrayList<String> urun_isimleri, ArrayList<String> urun_fiyatlar) {
        this.urun_isimleri = urun_isimleri;
        this.urun_fiyatlar = urun_fiyatlar;
        Log.d("Log", "Constructor: Called. Size of arraylist:" + urun_isimleri.size());
    }


    @NonNull
    @Override
    public urunSatir onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("AdapterMsg", "onCreateViewHolder: called ");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.satir_urun_liste,parent,false);
        return new urunSatir(view);
    }

    @Override
    public void onBindViewHolder(@NonNull urunSatir holder, final int position) {
        Log.d("Log", "onBindViewHolder: called");
        siparis = new Siparis();
        holder.sayac.setText("0");
        siparis.setSayacTemp(holder.sayac);
        holder.urun_ismi.setText(urun_isimleri.get(position));
        holder.fiyat.setText(urun_fiyatlar.get(position));
        //holder.sayac.setText(siparis.urunSay(urun_isimleri.get(position)));
        holder.azalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        //siparis.sepettenUrunCikar(urun_isimleri.get(position),urun_fiyatlar.get(position));
            siparis.getSayacTemp().setText(Integer.parseInt((String) siparis.getSayacTemp().getText())-1);
            }
        });

        holder.arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         //siparis.sepeteUrunEkle(urun_isimleri.get(position),urun_fiyatlar.get(position));
                siparis.getSayacTemp().setText(Integer.parseInt((String) siparis.getSayacTemp().getText())+1);

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
