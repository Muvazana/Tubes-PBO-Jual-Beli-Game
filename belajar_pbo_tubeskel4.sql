-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 20 Jan 2022 pada 11.15
-- Versi server: 10.4.16-MariaDB
-- Versi PHP: 7.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `belajar_pbo_tubeskel4`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_products`
--

CREATE TABLE `tb_products` (
  `id` int(11) NOT NULL,
  `store_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `type` enum('wallet_code','game_item','game_code') NOT NULL DEFAULT 'wallet_code',
  `stock` int(11) NOT NULL DEFAULT 0,
  `price` int(11) NOT NULL DEFAULT 0,
  `rate` varchar(5) NOT NULL DEFAULT '',
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_products`
--

INSERT INTO `tb_products` (`id`, `store_id`, `name`, `type`, `stock`, `price`, `rate`, `description`) VALUES
(12, 57, 'Stem Wallet 50USD', 'wallet_code', 19, 600000, '4.0', ''),
(13, 57, 'Stem BattleField Game Code', 'game_code', 3, 800000, '0', 'ini game bagus'),
(14, 58, 'CSGO Stem Senjata Bagus', 'game_item', 4, 200000, '2.5', 'ini senjata bagus dong'),
(15, 58, 'Stem Wallet 20USD', 'wallet_code', 55, 210000, '0', 'ini wallet bruh'),
(19, 70, 'Steam Wallet 30USD', 'wallet_code', 14, 300000, '4.0', 'ini Steam Wallet 30USD');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_transaction`
--

CREATE TABLE `tb_transaction` (
  `id` int(11) NOT NULL,
  `user_member_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `total_price` int(11) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `redeem_code` varchar(255) DEFAULT NULL,
  `product_rate` int(5) DEFAULT 0,
  `status` enum('member_request','member_cancel','seller_approved','seller_rejected','done') NOT NULL DEFAULT 'member_request'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_transaction`
--

INSERT INTO `tb_transaction` (`id`, `user_member_id`, `product_id`, `total_price`, `note`, `redeem_code`, `product_rate`, `status`) VALUES
(15, 56, 14, 220000, 'Stem/Muvazana', NULL, 4, 'done'),
(16, 59, 14, 220000, 'Stem/Arga', NULL, 0, 'member_cancel'),
(17, 59, 14, 220000, 'Stem/Arga', NULL, 1, 'done'),
(19, 68, 19, 330000, NULL, NULL, 0, 'member_cancel'),
(20, 68, 19, 330000, NULL, 'SJKW23', 4, 'done');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_wallet_log`
--

CREATE TABLE `tb_wallet_log` (
  `id` int(11) NOT NULL,
  `user_req_id` int(11) NOT NULL,
  `wallet` int(11) NOT NULL DEFAULT 0,
  `status` enum('request','approved','rejected') NOT NULL DEFAULT 'request'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_wallet_log`
--

INSERT INTO `tb_wallet_log` (`id`, `user_req_id`, `wallet`, `status`) VALUES
(10, 56, 1000000, 'approved'),
(11, 59, 500000, 'approved'),
(13, 68, 1000000, 'approved');

-- --------------------------------------------------------

--
-- Struktur dari tabel `toko`
--

CREATE TABLE `toko` (
  `id` int(11) NOT NULL,
  `store_name` varchar(150) NOT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `toko`
--

INSERT INTO `toko` (`id`, `store_name`, `description`) VALUES
(57, 'Toko Bahagia 1', 'ini adalah toko yang sangat membuat kalian bahagia!!!'),
(58, 'Toko Bahagia 2', 'ini adalah toko yang sangat membuat kalian bahagia!!! 2'),
(70, 'Toko Arga', 'ini toko Arga');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(150) NOT NULL,
  `role` enum('admin','member','seller') NOT NULL DEFAULT 'member',
  `wallet` int(11) DEFAULT 0,
  `username` varchar(150) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id`, `name`, `role`, `wallet`, `username`, `password`) VALUES
(1, 'Muvazana', 'admin', 0, 'admin', 'admin'),
(56, 'Member1', 'member', 780000, 'member1', 'member1'),
(57, 'Seller1', 'seller', 600000, 'seller1', 'seller1'),
(58, 'Seller2', 'seller', 400000, 'seller2', 'seller2'),
(59, 'Member2', 'member', 280000, 'member2', 'member2'),
(60, 'Member3', 'member', 0, 'member3', 'member3'),
(68, 'Argaa', 'member', 670000, 'arga', 'arga'),
(70, 'Arga Seller', 'seller', 300000, 'argaseller', 'argaseller');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tb_products`
--
ALTER TABLE `tb_products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `STORE_ID` (`store_id`);

--
-- Indeks untuk tabel `tb_transaction`
--
ALTER TABLE `tb_transaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `USER_MEMBER_ID` (`user_member_id`),
  ADD KEY `PRODUCT_ID` (`product_id`);

--
-- Indeks untuk tabel `tb_wallet_log`
--
ALTER TABLE `tb_wallet_log`
  ADD PRIMARY KEY (`id`),
  ADD KEY `USER_REQ_ID` (`user_req_id`);

--
-- Indeks untuk tabel `toko`
--
ALTER TABLE `toko`
  ADD PRIMARY KEY (`id`),
  ADD KEY `toko_id` (`id`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tb_products`
--
ALTER TABLE `tb_products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT untuk tabel `tb_transaction`
--
ALTER TABLE `tb_transaction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT untuk tabel `tb_wallet_log`
--
ALTER TABLE `tb_wallet_log`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=71;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `tb_products`
--
ALTER TABLE `tb_products`
  ADD CONSTRAINT `tb_products_ibfk_1` FOREIGN KEY (`store_id`) REFERENCES `toko` (`id`);

--
-- Ketidakleluasaan untuk tabel `tb_transaction`
--
ALTER TABLE `tb_transaction`
  ADD CONSTRAINT `tb_transaction_ibfk_1` FOREIGN KEY (`user_member_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `tb_transaction_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `tb_products` (`id`);

--
-- Ketidakleluasaan untuk tabel `tb_wallet_log`
--
ALTER TABLE `tb_wallet_log`
  ADD CONSTRAINT `tb_wallet_log_ibfk_1` FOREIGN KEY (`user_req_id`) REFERENCES `user` (`id`);

--
-- Ketidakleluasaan untuk tabel `toko`
--
ALTER TABLE `toko`
  ADD CONSTRAINT `toko_ibfk_1` FOREIGN KEY (`id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
