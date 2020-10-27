package com.firstproject.resourceserver.model.dto;

import com.firstproject.resourceserver.model.entity.Produk;

import lombok.Data;

@Data
public class ProdukHome {
	private String id;
	private String namaProduk;
	private Long hargaProduk;
	private Integer stokProduk;
	private String fotoProduk1;
	
}
