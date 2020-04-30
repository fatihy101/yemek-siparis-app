package com.yazilimmuhendisligi.yemeksiparis;

import android.widget.TextView;

import com.yazilimmuhendisligi.yemeksiparis.ui_musteri.SiparisVerActivity.UrunListRecyclerAdapterM;

import java.util.ArrayList;

public class Siparis {
     ArrayList<String> sepetteki_urunler;
     ArrayList<String> sepetteki_fiyatlar;
     ArrayList<UrunListRecyclerAdapterM.urunSatir > sayacTemp;

    public ArrayList<UrunListRecyclerAdapterM.urunSatir> getSayacTemp() {
        return sayacTemp;
    }

    public void setSayacTemp(UrunListRecyclerAdapterM.urunSatir  sayacTemp) {
        this.sayacTemp.add(sayacTemp);
    }

    public int urunSay(String theUrun)
    {
        int sayacUrun = 0;
        if(sepetteki_urunler.contains(theUrun))
        {
            for(int i = 0; i<sepetteki_urunler.size();i++)
            {
              if(sepetteki_urunler.get(i).equals(theUrun))  sayacUrun++;
            }
            return sayacUrun;
        }
        else return 0;
    }

    public void sepeteUrunEkle(String urun, String fiyat)
    {
        sepetteki_urunler.add(urun);
        sepetteki_fiyatlar.add(fiyat);
    }

    public void sepettenUrunCikar(String urun,String fiyat)
    {
        sepetteki_urunler.remove(urun);
        sepetteki_fiyatlar.remove(fiyat);
    }

}
