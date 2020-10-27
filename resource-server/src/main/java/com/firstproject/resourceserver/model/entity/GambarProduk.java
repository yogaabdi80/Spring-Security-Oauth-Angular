package com.firstproject.resourceserver.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "dat_gambar_produk")
public class GambarProduk {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nama_gambar")
	private String namaGambar;
	
	@Column(name = "id_produk")
	private String idProduk;
	
}
