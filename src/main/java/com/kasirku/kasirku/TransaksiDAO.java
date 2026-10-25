package com.kasirku.kasirku;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class TransaksiDAO {

    public static boolean simpanTransaksi(
            int total,
            int bayar,
            int kembalian,
            DefaultTableModel model) {

        String sqlTransaksi = "INSERT INTO transaksi "
                + "(total, bayar, kembalian) VALUES (?, ?, ?)";

        String sqlDetail = "INSERT INTO detail_transaksi "
                + "(id_transaksi, nama_produk, harga, jumlah, subtotal) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Koneksi.getKoneksi()) {

            conn.setAutoCommit(false);

            try {
                int idTransaksi;

                // Simpan transaksi utama
                try (PreparedStatement ps = conn.prepareStatement(
                        sqlTransaksi, Statement.RETURN_GENERATED_KEYS)) {

                    ps.setInt(1, total);
                    ps.setInt(2, bayar);
                    ps.setInt(3, kembalian);
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException(
                                    "ID transaksi tidak ditemukan.");
                        }

                        idTransaksi = rs.getInt(1);
                    }
                }

                // Simpan setiap produk yang dibeli
                try (PreparedStatement ps = conn.prepareStatement(sqlDetail)) {

                    for (int i = 0; i < model.getRowCount(); i++) {

                        String namaProduk =
                                model.getValueAt(i, 0).toString();

                        int harga = Integer.parseInt(
                                model.getValueAt(i, 1).toString());

                        int jumlah = Integer.parseInt(
                                model.getValueAt(i, 2).toString());

                        int subtotal = Integer.parseInt(
                                model.getValueAt(i, 3).toString());

                        ps.setInt(1, idTransaksi);
                        ps.setString(2, namaProduk);
                        ps.setInt(3, harga);
                        ps.setInt(4, jumlah);
                        ps.setInt(5, subtotal);

                        ps.addBatch();
                    }

                    ps.executeBatch();
                }

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                throw e;

            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}