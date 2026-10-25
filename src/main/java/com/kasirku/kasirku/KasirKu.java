package com.kasirku.kasirku;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class KasirKu extends JFrame {

    private JTable tabelKeranjang;
    private DefaultTableModel model;
    private JLabel labelTotal;

    private int totalBelanja = 0;

    public KasirKu() {
        setTitle("KasirKu - Aplikasi Kasir Minimarket");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel judul = new JLabel(
                "KASIRKU - MINIMARKET",
                SwingConstants.CENTER
        );
        judul.setFont(new Font("Arial", Font.BOLD, 26));
        judul.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        add(judul, BorderLayout.NORTH);

        JPanel panelProduk = new JPanel(new GridLayout(0, 2, 10, 10));
        panelProduk.setBorder(
                BorderFactory.createTitledBorder("Pilih Produk")
        );

        
try {
    ProdukDAO produkDAO = new ProdukDAO();

    for (Produk produk : produkDAO.getSemuaProduk()) {
        JButton tombol = new JButton(
                "<html><center>"
                + produk.getNamaProduk()
                + "<br>Rp"
                + String.format("%,d", produk.getHarga())
                + "<br>Stok: "
                + produk.getStok()
                + "</center></html>"
        );

        tombol.setFont(new Font("Arial", Font.BOLD, 14));

        tombol.addActionListener(e -> {
            if (produk.getStok() <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Stok produk habis!"
                );
                return;
            }

            tambahKeKeranjang(
                    produk.getNamaProduk(),
                    produk.getHarga()
            );
        });

        panelProduk.add(tombol);
    }

} catch (java.sql.SQLException e) {
    e.printStackTrace();

    JOptionPane.showMessageDialog(
            this,
            "Gagal mengambil produk dari database!\n"
            + e.getMessage(),
            "Kesalahan Database",
            JOptionPane.ERROR_MESSAGE
    );
}

        add(panelProduk, BorderLayout.WEST);

        model = new DefaultTableModel(
                new Object[]{"Produk", "Harga", "Jumlah", "Subtotal"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelKeranjang = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(tabelKeranjang);
        scrollPane.setBorder(
                BorderFactory.createTitledBorder("Keranjang Belanja")
        );

        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBawah = new JPanel(new BorderLayout(10, 10));
        panelBawah.setBorder(
                BorderFactory.createEmptyBorder(10, 15, 15, 15)
        );

        labelTotal = new JLabel("Total: Rp0");
        labelTotal.setFont(new Font("Arial", Font.BOLD, 22));

        JButton tombolHapus = new JButton("Hapus Semua");
        JButton tombolBayar = new JButton("Bayar");

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelTombol.add(tombolHapus);
        panelTombol.add(tombolBayar);

        panelBawah.add(labelTotal, BorderLayout.WEST);
        panelBawah.add(panelTombol, BorderLayout.EAST);

        add(panelBawah, BorderLayout.SOUTH);

        tombolHapus.addActionListener(e -> {
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(
                        this, "Keranjang masih kosong!"
                );
                return;
            }

            int pilihan = JOptionPane.showConfirmDialog(
                    this,
                    "Yakin ingin menghapus semua produk?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION
            );

            if (pilihan == JOptionPane.YES_OPTION) {
                model.setRowCount(0);
                totalBelanja = 0;
                perbaruiTotal();
            }
        });

        tombolBayar.addActionListener(e -> prosesPembayaran());
    }

    private void tambahTombolProduk(
            JPanel panel, String nama, int harga) {

        JButton tombol = new JButton(
                "<html><center>"
                        + nama
                        + "<br>Rp"
                        + String.format("%,d", harga)
                        + "</center></html>"
        );

        tombol.setFont(new Font("Arial", Font.BOLD, 14));

        tombol.addActionListener(e -> tambahKeKeranjang(nama, harga));

        panel.add(tombol);
    }

    private void tambahKeKeranjang(String nama, int harga) {

        for (int i = 0; i < model.getRowCount(); i++) {

            String namaProduk = model.getValueAt(i, 0).toString();

            if (namaProduk.equals(nama)) {

                int jumlah = Integer.parseInt(
                        model.getValueAt(i, 2).toString()
                );

                jumlah++;

                int subtotal = jumlah * harga;

                model.setValueAt(jumlah, i, 2);
                model.setValueAt(subtotal, i, 3);

                totalBelanja += harga;
                perbaruiTotal();

                return;
            }
        }

        model.addRow(new Object[]{
                nama, harga, 1, harga
        });

        totalBelanja += harga;
        perbaruiTotal();
    }

    private void perbaruiTotal() {
        labelTotal.setText(
                "Total: Rp" + String.format("%,d", totalBelanja)
        );
    }

    private void prosesPembayaran() {

        if (totalBelanja == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Pilih produk terlebih dahulu!"
            );
            return;
        }

        String input = JOptionPane.showInputDialog(
                this,
                "Total belanja: Rp"
                        + String.format("%,d", totalBelanja)
                        + "\nMasukkan uang pembayaran:"
        );

        if (input == null) {
            return;
        }

        int uangBayar;

        try {
            uangBayar = Integer.parseInt(
                    input.trim().replace(".", "").replace(",", "")
            );

            if (uangBayar < 0) {
                JOptionPane.showMessageDialog(
                        this, "Pembayaran tidak boleh negatif!"
                );
                return;
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Masukkan nominal berupa angka yang valid!"
            );
            return;
        }

        if (uangBayar < totalBelanja) {
            JOptionPane.showMessageDialog(
                    this,
                    "Uang pembayaran kurang!\n"
                            + "Total: Rp"
                            + String.format("%,d", totalBelanja)
                            + "\nKekurangan: Rp"
                            + String.format(
                                    "%,d", totalBelanja - uangBayar
                            ),
                    "Pembayaran Gagal",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

int kembalian = uangBayar - totalBelanja;

boolean berhasil = TransaksiDAO.simpanTransaksi(
        totalBelanja,
        uangBayar,
        kembalian,
        model
);

if (!berhasil) {
    return;
}

tampilkanStruk(uangBayar, kembalian);
    }

    private void tampilkanStruk(int uangBayar, int kembalian) {

        StringBuilder struk = new StringBuilder();

        String tanggal = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
        );

        struk.append("       KASIRKU MINIMARKET\n");
        struk.append("================================\n");
        struk.append("Tanggal: ").append(tanggal).append("\n");
        struk.append("================================\n");
        struk.append(String.format(
                "%-15s %4s %12s\n", "Produk", "Qty", "Subtotal"
        ));
        struk.append("--------------------------------\n");

        for (int i = 0; i < model.getRowCount(); i++) {

            String nama = model.getValueAt(i, 0).toString();
            int jumlah = Integer.parseInt(
                    model.getValueAt(i, 2).toString()
            );
            int subtotal = Integer.parseInt(
                    model.getValueAt(i, 3).toString()
            );

            struk.append(String.format(
                    "%-15s %4d %12s\n",
                    nama,
                    jumlah,
                    String.format("%,d", subtotal)
            ));
        }

        struk.append("--------------------------------\n");
        struk.append(String.format(
                "%-18s Rp%10s\n",
                "TOTAL",
                String.format("%,d", totalBelanja)
        ));
        struk.append(String.format(
                "%-18s Rp%10s\n",
                "BAYAR",
                String.format("%,d", uangBayar)
        ));
        struk.append(String.format(
                "%-18s Rp%10s\n",
                "KEMBALIAN",
                String.format("%,d", kembalian)
        ));
        struk.append("================================\n");
        struk.append("       TERIMA KASIH!\n");
        struk.append("    SELAMAT BERBELANJA\n");

        JTextArea areaStruk = new JTextArea(struk.toString());
        areaStruk.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaStruk.setEditable(false);
        areaStruk.setCaretPosition(0);

        JScrollPane scrollStruk = new JScrollPane(areaStruk);
        scrollStruk.setPreferredSize(new Dimension(480, 350));

        Object[] pilihan = {"Cetak Struk", "Tutup"};

        int hasil = JOptionPane.showOptionDialog(
                this,
                scrollStruk,
                "Struk Pembayaran",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                pilihan,
                pilihan[0]
        );

        if (hasil == 0) {
            try {
                areaStruk.print();
            } catch (java.awt.print.PrinterException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Struk gagal dicetak: " + ex.getMessage()
                );
            }
        }

        int transaksiBaru = JOptionPane.showConfirmDialog(
                this,
                "Apakah ingin memulai transaksi baru?",
                "Transaksi Selesai",
                JOptionPane.YES_NO_OPTION
        );

        if (transaksiBaru == JOptionPane.YES_OPTION) {
            model.setRowCount(0);
            totalBelanja = 0;
            perbaruiTotal();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new KasirKu().setVisible(true);
        });
    }
}