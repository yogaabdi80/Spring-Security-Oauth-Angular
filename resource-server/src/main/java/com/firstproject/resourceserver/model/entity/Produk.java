package com.firstproject.resourceserver.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "dat_produk")
public class Produk {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "kategori_produk")
	private KategoriProduk kategoriProduk;

	@Column(name = "nama_produk")
	private String namaProduk;

	@Column(name = "harga_produk")
	private Long hargaProduk;

	@Column(name = "stok_produk")
	private Integer stokProduk;

	@Column(name = "deskripsi_produk")
	private String deskripsiProduk;

	@Column(name = "foto_produk1")
	private String fotoProduk1;

	@Column(name = "foto_produk2")
	private String fotoProduk2;

	@Column(name = "foto_produk3")
	private String fotoProduk3;

	@Column(name = "foto_produk4")
	private String fotoProduk4;

	@Column(name = "foto_produk5")
	private String fotoProduk5;

//	@OneToOne(mappedBy = "produk")
//	private Cart cart;
	
//	@OneToMany(mappedBy = "idProduk", cascade = { CascadeType.ALL })
//	private List<GambarProduk> gambar;

}
