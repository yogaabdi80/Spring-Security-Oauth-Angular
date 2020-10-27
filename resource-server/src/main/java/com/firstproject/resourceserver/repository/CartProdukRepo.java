package com.firstproject.resourceserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firstproject.resourceserver.model.entity.CartProduk;

@Repository
public interface CartProdukRepo extends JpaRepository<CartProduk, Long> {

	
	Optional<CartProduk> findByIdCartAndIdProduk(Long idCart,String idProduk);
	
	List<CartProduk> findByIdCart(Long idCart);
	
}
