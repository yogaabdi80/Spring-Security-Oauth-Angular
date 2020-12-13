package com.firstproject.resourceserver.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "s_users_details")
public class UserDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "no_hp")
	private String noHp;
	
	@Column(name = "foto_profil")
	private String fotoProfil;
	
	private String alamat;
	
	@JsonBackReference
	@OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "id_user",referencedColumnName = "id_user")
	private User user;
}
