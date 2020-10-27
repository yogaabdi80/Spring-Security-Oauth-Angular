package com.firstproject.resourceserver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firstproject.resourceserver.model.entity.KategoriProduk;
import com.firstproject.resourceserver.model.entity.Produk;

@Repository
public interface ProdukRepository extends JpaRepository<Produk, String> {
	
	Page<Produk> findByKategoriProduk(KategoriProduk kategoriProduk,Pageable pageable);
	
	Page<Produk> findByNamaProdukContainingIgnoreCase(String namaProduk,Pageable pageable);
	
	Page<Produk> findByKategoriProdukAndNamaProdukContainingIgnoreCase(KategoriProduk kategoriProduk,String namaProduk,Pageable pageable);
	
}
