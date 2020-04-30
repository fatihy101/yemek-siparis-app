package com.yazilimmuhendisligi.yemeksiparis.ui_musteri.SiparisVerActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yazilimmuhendisligi.yemeksiparis.R;
import com.yazilimmuhendisligi.yemeksiparis.ui_musteri.musteriActivity;

import java.util.ArrayList;

public class RestoranListeRecylerAdapter_Musteri extends RecyclerView.Adapter<RestoranListeRecylerAdapter_Musteri.RestoranSatir> {
    private ArrayList<String> firmalar_isim;
    private ArrayList<String> firmalar_uid;

//Constructor
    public RestoranListeRecylerAdapter_Musteri(ArrayList<String> firmalar_isim, ArrayList<String> firmalar_uid) {
        this.firmalar_isim = firmalar_isim;
        this.firmalar_uid = firmalar_uid;
    }

    @NonNull
    @Override
    public RestoranSatir onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Viewholder oluşturulunca
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.satir_restoran_listele,parent,false);
        return new RestoranSatir(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestoranSatir holder, final int position) {
//Viewholder'a bağlanınca
        holder.button.setText(firmalar_isim.get(position));

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UrunListelemeActivity_Siparis.class);
                intent.putExtra("firmaUID",firmalar_uid.get(position));
                 v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return firmalar_isim.size();
    }

    public class RestoranSatir extends RecyclerView.ViewHolder {
        Button button;
        public RestoranSatir(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.restoran_ismi_item);
        }
    }
}
