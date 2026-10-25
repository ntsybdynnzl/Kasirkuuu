package com.kasirku.kasirku;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdukDAO {

    public List<Produk> getSemuaProduk() throws SQLException {
        List<Produk> daftarProduk = new ArrayList<>();

        String sql = """
            SELECT id_produk, nama_produk, harga, stok
            FROM produk
            ORDER BY nama_produk
            """;

        try (
            Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Produk produk = new Produk(
                    rs.getInt("id_produk"),
                    rs.getString("nama_produk"),
                    rs.getInt("harga"),
                    rs.getInt("stok")
                );

                daftarProduk.add(produk);
            }
        }

        return daftarProduk;
    }
}