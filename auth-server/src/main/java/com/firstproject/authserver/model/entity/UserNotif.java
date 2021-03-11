package com.firstproject.authserver.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "dat_notif_user")
public class UserNotif {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "id_user")
	private Long idUser;
	private String username;
	private String email;
	private String action;
	@Column(name = "expired_date")
	private Date expiredDate;
	
}
