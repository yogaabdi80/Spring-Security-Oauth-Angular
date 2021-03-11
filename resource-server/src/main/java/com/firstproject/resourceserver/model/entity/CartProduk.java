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
@Table(name = "dat_cart_produk")
public class CartProduk {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="jumlah_item")
	private Integer jumlahItem;
	
	@Column(name = "id_produk")
	private String idProduk;
	
	@Column(name = "id_cart")
	private Long idCart;
	
}
