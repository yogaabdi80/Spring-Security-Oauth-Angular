package com.firstproject.resourceserver.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProdukHomeDTO {
	private List<ProdukHome> produk;
	private int totalPage;
	private long length;
	
}
