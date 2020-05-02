package com.yazilimmuhendisligi.yemeksiparis.ui_musteri.GecmisSiparisMusteri;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yazilimmuhendisligi.yemeksiparis.R;

import java.util.ArrayList;

public class GecmisSiparisAdapter extends RecyclerView.Adapter<GecmisSiparisAdapter.siparisRow> {
    ArrayList<String> icerik;
    ArrayList<String> tutar;
    ArrayList<String> firma;
    ArrayList<String> durum;

    public GecmisSiparisAdapter(ArrayList<String> icerik, ArrayList<String> tutar, ArrayList<String> firma, ArrayList<String> durum) {
        this.icerik = icerik;
        this.tutar = tutar;
        this.firma = firma;
        this.durum = durum;
    }


    @NonNull
    @Override
    public siparisRow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.gecmis_siparis_satir,parent,false);
        return new siparisRow(view);
    }

    @Override
    public void onBindViewHolder(@NonNull siparisRow holder, int position) {
    holder.siparis_icerik.setText(icerik.get(position));
    holder.siparis_tutar.setText(tutar.get(position)+ " TL");
    holder.siparis_firma.setText(firma.get(position));
    holder.durum.setText(durum.get(position));
    }

    @Override
    public int getItemCount() {
        return icerik.size();
    }

    public class siparisRow extends RecyclerView.ViewHolder {
        TextView siparis_icerik, siparis_tutar, siparis_firma, durum;

        public siparisRow(@NonNull View itemView) {
            super(itemView);
            siparis_icerik = itemView.findViewById(R.id.icerik_gecmis_row);
            siparis_tutar  = itemView.findViewById(R.id.tutar_gecmis_row);
            siparis_firma = itemView.findViewById(R.id.email_firma_gecmis_row);
            durum = itemView.findViewById(R.id.durum_gecmis_row);
        }
    }
}
