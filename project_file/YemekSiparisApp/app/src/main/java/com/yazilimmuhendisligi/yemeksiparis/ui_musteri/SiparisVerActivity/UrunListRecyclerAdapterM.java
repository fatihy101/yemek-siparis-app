package com.yazilimmuhendisligi.yemeksiparis.ui_musteri.SiparisVerActivity;

import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yazilimmuhendisligi.yemeksiparis.R;
import com.yazilimmuhendisligi.yemeksiparis.Siparis;

import java.util.ArrayList;

public class UrunListRecyclerAdapterM extends RecyclerView.Adapter<UrunListRecyclerAdapterM.urunSatir> {
    private ArrayList<String>  urun_isimleri;
    private ArrayList<String> urun_fiyatlar;
    private ArrayList<String>  sepet_urun;
    private ArrayList<String> sepet_fiyat;
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
        sepet_fiyat = new ArrayList<>();
        sepet_urun = new ArrayList<>();
        siparis = new Siparis(sepet_urun,sepet_fiyat);
        return new urunSatir(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final urunSatir holder, final int position) {
        Log.d("Log", "onBindViewHolder: called");
        //siparis = new Siparis();

      // if(holder.sayac!=null)  siparis.setSayacTemp(holder);
        holder.urun_ismi.setText(urun_isimleri.get(position));
        holder.fiyat.setText(String.format("%s TL", urun_fiyatlar.get(position)));
        //holder.sayac.setText(siparis.urunSay(urun_isimleri.get(position)));


        holder.arttir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siparis.sepeteUrunEkle(urun_isimleri.get(position),urun_fiyatlar.get(position));
                Toast.makeText(v.getContext(), urun_isimleri.get(position) +" sepete eklendi!", Toast.LENGTH_SHORT).show();
            Intent intentData = new Intent("custom-message");
            intentData.putExtra("sepetteki_urunler",siparis.getSepetteki_urunler());
            intentData.putExtra("sepetteki_fiyatlar",siparis.getSepetteki_fiyatlar());
            LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intentData);
            siparis.list();
            }
        });

    }

    @Override
    public int getItemCount() {
        return urun_isimleri.size();
    }

    public class urunSatir extends RecyclerView.ViewHolder {
        Button arttir;
        TextView urun_ismi, fiyat;
        public urunSatir(@NonNull View itemView) {
            super(itemView);

            arttir = itemView.findViewById(R.id.urun_arttir);
            urun_ismi = itemView.findViewById(R.id.urun_ismi_liste);
            fiyat = itemView.findViewById(R.id.urun_fiyat_liste);

        }
    }
}
