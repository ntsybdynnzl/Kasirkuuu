package com.kasirku.kasirku;

public class Produk {
    private int idProduk;
    private String namaProduk;
    private int harga;
    private int stok;

    public Produk(int idProduk, String namaProduk, int harga, int stok) {
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.stok = stok;
    }

    public int getIdProduk() {
        return idProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public int getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    @Override
    public String toString() {
        return namaProduk + " - Rp" + String.format("%,d", harga);
    }
}