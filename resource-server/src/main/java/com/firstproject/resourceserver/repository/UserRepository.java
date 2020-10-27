package com.firstproject.resourceserver.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.firstproject.resourceserver.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	static final String SQL_PERMISSION = "select r.name as authority from s_user_roles ur inner join s_users u on ur.id_user = u.id_user " + 
			"inner join s_roles r on r.id_role = ur.id_role where u.username = :username";
	
	User findByUsername(String username);

	User findByEmail(String email);

	@Query(value = SQL_PERMISSION, nativeQuery = true)
	List<String> getAuthorities(String username);
	
	@Transactional
	@Modifying
	@Query(value = "update s_users set active=:enabled where id_user=:id",nativeQuery = true)
	Integer setEnabled(Long id,boolean enabled);
}
