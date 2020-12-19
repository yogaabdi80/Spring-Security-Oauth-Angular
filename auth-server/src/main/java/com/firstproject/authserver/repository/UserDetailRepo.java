package com.firstproject.authserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firstproject.authserver.model.entity.UserDetail;


@Repository
public interface UserDetailRepo extends JpaRepository<UserDetail, Long>{

}
