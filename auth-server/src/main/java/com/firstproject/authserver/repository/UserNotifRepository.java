package com.firstproject.authserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.firstproject.authserver.model.entity.UserNotif;

@Repository
public interface UserNotifRepository extends JpaRepository<UserNotif, Long> {
	UserNotif findByIdUserAndAction(Long idUser,String action);
}
