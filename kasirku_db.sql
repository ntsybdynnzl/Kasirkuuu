-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 25 Okt 2026 pada 07.47
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kasirku_db`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_transaksi`
--

CREATE TABLE `detail_transaksi` (
  `id_detail` int(11) NOT NULL,
  `id_transaksi` int(11) NOT NULL,
  `nama_produk` varchar(100) NOT NULL,
  `harga` int(11) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `subtotal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `detail_transaksi`
--

INSERT INTO `detail_transaksi` (`id_detail`, `id_transaksi`, `nama_produk`, `harga`, `jumlah`, `subtotal`) VALUES
(1, 8, 'Susu BubleGum', 8000, 1, 8000),
(2, 8, 'Susu Coklat', 8000, 1, 8000),
(3, 8, 'Susu Matcha', 9000, 1, 9000),
(4, 9, 'Susu Literan', 15000, 1, 15000),
(5, 9, 'Susu Coklat', 8000, 1, 8000),
(6, 10, 'Susu Literan', 15000, 3, 45000),
(7, 11, 'Susu RedVelvet', 8000, 2, 16000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `produk`
--

CREATE TABLE `produk` (
  `id_produk` int(11) NOT NULL,
  `nama_produk` varchar(100) NOT NULL,
  `harga` int(11) NOT NULL,
  `stok` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `produk`
--

INSERT INTO `produk` (`id_produk`, `nama_produk`, `harga`, `stok`) VALUES
(1, 'Susu Literan', 15000, 50),
(2, 'Susu Coklat', 8000, 25),
(3, 'Susu Taro', 8000, 25),
(4, 'Susu Oreo', 8000, 25),
(5, 'Susu Rempah-Rempah', 8000, 25),
(6, 'Susu Vanila', 8000, 25),
(7, 'Susu RedVelvet', 8000, 25),
(8, 'Susu Melon', 8000, 25),
(9, 'Susu Cappucino', 8000, 25),
(10, 'Susu Milo', 8000, 25),
(11, 'Susu Strawberry', 8000, 25),
(12, 'Susu Pisang', 8000, 25),
(13, 'Susu Matcha', 9000, 25),
(14, 'Susu BubleGum', 8000, 25);

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` int(11) NOT NULL,
  `tanggal` datetime DEFAULT current_timestamp(),
  `total` int(11) NOT NULL,
  `bayar` int(11) NOT NULL,
  `kembalian` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `transaksi`
--

INSERT INTO `transaksi` (`id_transaksi`, `tanggal`, `total`, `bayar`, `kembalian`) VALUES
(1, '2026-10-24 23:41:58', 16000, 20000, 4000),
(2, '2026-10-24 23:58:24', 16000, 29000, 13000),
(3, '2026-10-25 00:35:32', 15000, 20000, 5000),
(4, '2026-10-25 10:27:35', 23000, 40000, 17000),
(5, '2026-10-25 10:34:09', 16000, 20000, 4000),
(6, '2026-10-25 10:52:39', 16000, 20000, 4000),
(7, '2026-10-25 11:18:55', 24000, 40000, 16000),
(8, '2026-10-25 12:06:08', 25000, 40000, 15000),
(9, '2026-10-25 12:08:03', 23000, 40000, 17000),
(10, '2026-10-25 12:15:30', 45000, 50000, 5000),
(11, '2026-10-25 12:20:48', 16000, 20000, 4000);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD PRIMARY KEY (`id_detail`),
  ADD KEY `id_transaksi` (`id_transaksi`);

--
-- Indeks untuk tabel `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`id_produk`);

--
-- Indeks untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  MODIFY `id_detail` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT untuk tabel `produk`
--
ALTER TABLE `produk`
  MODIFY `id_produk` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD CONSTRAINT `detail_transaksi_ibfk_1` FOREIGN KEY (`id_transaksi`) REFERENCES `transaksi` (`id_transaksi`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
