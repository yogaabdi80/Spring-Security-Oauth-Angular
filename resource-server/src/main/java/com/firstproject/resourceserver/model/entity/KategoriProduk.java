package com.firstproject.resourceserver.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "dat_kategori_produk")
public class KategoriProduk {

	@Id
	private String id;

	private String kategori;

}
