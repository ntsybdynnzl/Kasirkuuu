package com.kasirku.kasirku;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

public class TesProduk {

    public static void main(String[] args) {
        try {
            ProdukDAO dao = new ProdukDAO();
            List<Produk> daftarProduk = dao.getSemuaProduk();

            StringBuilder hasil = new StringBuilder();

            for (Produk produk : daftarProduk) {
                hasil.append(produk.getNamaProduk())
                     .append(" | Rp")
                     .append(String.format("%,d", produk.getHarga()))
                     .append(" | Stok: ")
                     .append(produk.getStok())
                     .append("\n");
            }

            if (daftarProduk.isEmpty()) {
                hasil.append("Belum ada produk di database.");
            }

            JOptionPane.showMessageDialog(
                null,
                hasil.toString(),
                "Daftar Produk dari Database",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                null,
                "Gagal membaca produk:\n" + e.getMessage(),
                "Kesalahan Database",
                JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }
}