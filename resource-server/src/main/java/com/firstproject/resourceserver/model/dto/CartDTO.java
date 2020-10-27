package com.firstproject.resourceserver.model.dto;

import lombok.Data;

@Data
public class CartDTO {

		private Long id;
		private Integer jumlahItem;
		private String idProduk;
		private String namaProduk;
		private Long hargaProduk;
		private String fotoProduk1;
}
