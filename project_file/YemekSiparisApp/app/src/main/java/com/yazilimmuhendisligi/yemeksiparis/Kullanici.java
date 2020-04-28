package com.yazilimmuhendisligi.yemeksiparis;

import com.yazilimmuhendisligi.yemeksiparis.ui_musteri.SiparisVerActivity.UrunListelemeActivity_Siparis;

public class Kullanici  {
    private String email, UID, yetkiID;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getYetkiID() {
        return yetkiID;
    }

    public void setYetkiID(String yetkiID) {
        this.yetkiID = yetkiID;
    }

    //Constructor
    public Kullanici(String email, String UID) {
        this.email = email;
        this.UID = UID;
    }

    public  void yetkiIDGosterConsole()
    {
        System.out.println("Kullanıcının yetki ID'si: " + yetkiID);
    }
}
