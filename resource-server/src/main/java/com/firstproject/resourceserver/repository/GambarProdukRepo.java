package com.firstproject.resourceserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.firstproject.resourceserver.model.entity.GambarProduk;

@Repository
public interface GambarProdukRepo extends JpaRepository<GambarProduk, Long>{

	@Query(value = "select * from dat_gambar_produk where id_produk =:idProduk order by nama_gambar asc",nativeQuery = true)
	List<GambarProduk> findByIdProduk(String idProduk);
}
