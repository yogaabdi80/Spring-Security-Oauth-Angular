package com.firstproject.resourceserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firstproject.resourceserver.model.entity.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long>{
	
//	Cart findByProduk(Produk produk);
	
	Optional<Cart> findByIdUser(Long idUser);
		
	Integer countByIdUser(Long idUser);
}
