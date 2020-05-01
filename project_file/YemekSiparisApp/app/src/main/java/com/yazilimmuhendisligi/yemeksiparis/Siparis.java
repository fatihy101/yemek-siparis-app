package com.yazilimmuhendisligi.yemeksiparis;

import android.widget.TextView;

import com.yazilimmuhendisligi.yemeksiparis.ui_musteri.SiparisVerActivity.UrunListRecyclerAdapterM;

import java.util.ArrayList;

public class Siparis {
     ArrayList<String> sepetteki_urunler;
     ArrayList<String> sepetteki_fiyatlar;

    public Siparis(ArrayList<String> sepetteki_urunler, ArrayList<String> sepetteki_fiyatlar) {
        this.sepetteki_urunler = sepetteki_urunler;
        this.sepetteki_fiyatlar = sepetteki_fiyatlar;
    }

    public ArrayList<String> getSepetteki_urunler() {
        return sepetteki_urunler;
    }

    public ArrayList<String> getSepetteki_fiyatlar() {
        return sepetteki_fiyatlar;
    }

    public void sepeteUrunEkle(String urun, String fiyat)
    {
        sepetteki_urunler.add(urun);
        sepetteki_fiyatlar.add(fiyat);
    }

    public void list()
    {
     for (int i = 0; i < sepetteki_urunler.size(); i++) System.out.println(sepetteki_urunler.get(i) + "  " + getSepetteki_fiyatlar().get(i));
    }

    public void sepettenUrunCikar(String urun,String fiyat)
    {
        sepetteki_urunler.remove(urun);
        sepetteki_fiyatlar.remove(fiyat);
    }

}
