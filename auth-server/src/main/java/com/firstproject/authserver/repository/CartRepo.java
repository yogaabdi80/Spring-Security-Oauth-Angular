package com.firstproject.authserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firstproject.authserver.model.entity.Cart;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

}
