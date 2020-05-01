
/**********************************
 * Öğrenci Numarası: 2170656810   *
 * Öğrenci Ad Soyad: Hakan SÖZGEN *
 **********************************/

package com.yazilimmuhendisligi.yemeksiparis;

public class GelenSiparis {



    private String siparisId;
    private String firmaEmail;
    private String musteriAdSoyad;
    private String musteriSehir;
    private String musteriAdres;
    private String musteriTel;
    private String siparisDurumu;
    private String siparisTarihSaat;
    private int toplamFiyat;
    private String siparisUrunler;

    public GelenSiparis() {

    }

    public GelenSiparis(String firmaEmail, String musteriAdSoyad, String musteriSehir, String musteriAdres, String musteriTel, String siparisDurumu, String siparisTarihSaat, int toplamFiyat, String siparisUrunler) {
        this.firmaEmail = firmaEmail;
        this.musteriAdSoyad = musteriAdSoyad;
        this.musteriSehir = musteriSehir;
        this.musteriAdres = musteriAdres;
        this.musteriTel = musteriTel;
        this.siparisDurumu = siparisDurumu;
        this.siparisTarihSaat = siparisTarihSaat;
        this.toplamFiyat = toplamFiyat;
        this.siparisUrunler = siparisUrunler;
    }

    public String getSiparisId() {
        return siparisId;
    }

    public void setSiparisId(String siparisId) {
        this.siparisId = siparisId;
    }

    public String getFirmaEmail() {
        return firmaEmail;
    }

    public void setFirmaEmail(String firmaEmail) {
        this.firmaEmail = firmaEmail;
    }

    public String getMusteriAdSoyad() {
        return musteriAdSoyad;
    }

    public void setMusteriAdSoyad(String musteriAdSoyad) {
        this.musteriAdSoyad = musteriAdSoyad;
    }

    public String getMusteriSehir() {
        return musteriSehir;
    }

    public void setMusteriSehir(String musteriSehir) {
        this.musteriSehir = musteriSehir;
    }

    public String getMusteriAdres() {
        return musteriAdres;
    }

    public void setMusteriAdres(String musteriAdres) {
        this.musteriAdres = musteriAdres;
    }

    public String getMusteriTel() {
        return musteriTel;
    }

   public void setMusteriTel(String musteriTel) {
        this.musteriTel = musteriTel;
    }

    public String getSiparisDurumu() {
        return siparisDurumu;
    }

    public void setSiparisDurumu(String siparisDurumu) {
        this.siparisDurumu = siparisDurumu;
    }

    public String getSiparisTarihSaat() {
        return siparisTarihSaat;
    }

    public void setSiparisTarihSaat(String siparisTarihSaat) {
        this.siparisTarihSaat = siparisTarihSaat;
    }

   public int getToplamFiyat() {
        return toplamFiyat;
    }

   public void setToplamFiyat(int toplamFiyat) {
        this.toplamFiyat = toplamFiyat;
    }

    public String getSiparisUrunler() {
        return siparisUrunler;
    }

    public void setSiparisUrunler(String siparisUrunler) {
        this.siparisUrunler = siparisUrunler;
    }



}
