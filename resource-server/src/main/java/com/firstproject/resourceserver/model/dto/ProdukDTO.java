package com.firstproject.resourceserver.model.dto;

import lombok.Data;

@Data
public class ProdukDTO {
	private String id;
	private String kategori;
	private String kdKategori;
	private String namaProduk;
	private Long hargaProduk;
	private Integer stokProduk;
	private String deskripsiProduk;
	private String fotoProduk1;
	private String fotoProduk2;
	private String fotoProduk3;
	private String fotoProduk4;
	private String fotoProduk5;
	private Integer jumlahItem;
}
