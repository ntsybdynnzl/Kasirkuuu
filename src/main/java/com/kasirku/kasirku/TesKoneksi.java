package com.kasirku.kasirku;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class TesKoneksi {

    public static void main(String[] args) {
        try (Connection conn = Koneksi.getKoneksi()) {
            JOptionPane.showMessageDialog(
                null,
                "Koneksi database berhasil!"
            );

            System.out.println(
                "Berhasil terhubung ke database kasirku_db."
            );

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                null,
                "Koneksi database gagal!\n" + e.getMessage()
            );

            e.printStackTrace();
        }
    }
}