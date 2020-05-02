
/**********************************
 * Öğrenci Numarası: 2170656810   *
 * Öğrenci Ad Soyad: Hakan SÖZGEN *
 **********************************/

package com.yazilimmuhendisligi.yemeksiparis.ui_firma.GelenSiparis;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.yazilimmuhendisligi.yemeksiparis.GelenSiparis;
import com.yazilimmuhendisligi.yemeksiparis.R;

import java.util.ArrayList;

/*Recyclerview da gözükecek her bir satıra bağlanacak veriler bu sınıfta oluşturulacak. Sınıf RecyclerView.Adapter sınıfından extend edilir
ve bu sınıfın tipinden bir ViewHolder parametresi alır*/
public class GelenSiparisAdapter extends RecyclerView.Adapter<GelenSiparisAdapter.PostHolder> {

    private ArrayList<GelenSiparis> gelenSiparisArrayList; // Constructor dan gelen parametre buraya aktarılır.


    public GelenSiparisAdapter(ArrayList<GelenSiparis> gelenSiparisArrayList){ // Constructor

        this.gelenSiparisArrayList = gelenSiparisArrayList;

    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Satır ilk oluştuğunda xml olarak tasarladığımız satır görünümümünü inflate ediyor yani bağlıyoruz.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_adapter_gelen_siparis, parent,false);

        return new PostHolder(view);
    }

    //Veriler liste satırlarına bağlandığında yapılacak iş
    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, final int position) {

        //Post Holder sınıfında atanan kontrollere gelensipariş arraylistinden position değerine göre verileri aktar
        holder.siparisDurumu.setText(gelenSiparisArrayList.get(position).getSiparisDurumu());
        holder.siparisEdilenUrunler.setText(gelenSiparisArrayList.get(position).getSiparisUrunler());
        holder.musteriAdSoyad.setText(gelenSiparisArrayList.get(position).getMusteriAdSoyad());
        holder.siparisTarih.setText(gelenSiparisArrayList.get(position).getSiparisTarihSaat());

        // Parent layout yani satırın herhangi bir yerine tıklandığında intente taşıyıcı verileri ekle ve çağıran view in context inin aktivitesine git
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),GelenSiparisDetayActivity.class);
                intent.putExtra("SiparisId",gelenSiparisArrayList.get(position).getSiparisId());
                intent.putExtra("FirmaEmail",gelenSiparisArrayList.get(position).getFirmaEmail());
                intent.putExtra("MusteriAdSoyad",gelenSiparisArrayList.get(position).getMusteriAdSoyad());
                intent.putExtra("MusteriSehir",gelenSiparisArrayList.get(position).getMusteriSehir());
                intent.putExtra("MusteriAdres",gelenSiparisArrayList.get(position).getMusteriAdres());
                intent.putExtra("MusteriTel",gelenSiparisArrayList.get(position).getMusteriTel());
                intent.putExtra("SiparisDurumu",gelenSiparisArrayList.get(position).getSiparisDurumu());
                intent.putExtra("SiparisTarihSaat", gelenSiparisArrayList.get(position).getSiparisTarihSaat());
                intent.putExtra("ToplamFiyat",gelenSiparisArrayList.get(position).getToplamFiyat());
                intent.putExtra("SiparisUrunler",gelenSiparisArrayList.get(position).getSiparisUrunler());
                v.getContext().startActivity(intent);
            }
        });


        //İlgli pozisyondaki holder yani satırda bulunan ikonu siparişin durumuna göre setle
        CharSequence siparisDurumuText = holder.siparisDurumu.getText();

        switch ( siparisDurumuText.toString()){
            case "Hazırlanıyor":
                holder.imageViewGelenSiparis.setImageResource(R.drawable.preparing);
                break;
            case "Yolda":
                holder.imageViewGelenSiparis.setImageResource(R.drawable.delivery);
                break;
            case "Teslim Edildi":
                holder.imageViewGelenSiparis.setImageResource(R.drawable.delivered);
                break;
            default:holder.imageViewGelenSiparis.setImageResource(R.drawable.new_order);
        }

    }

    @Override
    public int getItemCount() {

        return gelenSiparisArrayList.size();
    }

    //Kontrollerin satırlarda tanımlandığı tutucu sınıf
    class PostHolder extends RecyclerView.ViewHolder {

        TextView siparisDurumu, siparisEdilenUrunler, musteriAdSoyad, siparisTarih;
        ImageView imageViewGelenSiparis;
        CardView parentLayout; /* Bu parent layoutu satırın herhangi bir yerine tıklandığında çağrılıcak onClickListener için.
         Satır tasarımı CardView ve Constraint Layout bulundurduğu için ikisinden birini belirlemek yeterli. */

        public PostHolder(@NonNull View itemView) {
            super(itemView);

            siparisDurumu= itemView.findViewById(R.id.textViewSiparisDurum);
            siparisEdilenUrunler = itemView.findViewById(R.id.textViewSiparisUrunler);
            musteriAdSoyad = itemView.findViewById(R.id.textViewSiparisDetayMusteriAdSoyad);
            siparisTarih=  itemView.findViewById(R.id.textViewSiparisTarihi);
            imageViewGelenSiparis = itemView.findViewById(R.id.imageViewGelenSiparis);
            parentLayout = itemView.findViewById(R.id.card_view_gelen_siparis);
        }

    }
}
