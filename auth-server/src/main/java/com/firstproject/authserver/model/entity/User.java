package com.firstproject.authserver.model.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.firstproject.authserver.model.dto.UserDto;

import lombok.Data;

@Data
@Entity
@Table(name = "s_users")
public class User {
	
	public User(String username, String password, Boolean enabled, String email, String fullname) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.email = email;
		this.fullname=fullname;
	}
	
	public User() {
		this.username = null;
		this.password = null;
		this.enabled = null;
		this.email = null;
		this.fullname=null;
	}
	
	public User(UserDto dto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		this.username = dto.getUsername();
		this.password = encoder.encode(dto.getPassword());
		this.email = dto.getEmail();
		this.fullname=dto.getFullname();
		this.enabled=false;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id_user")
	private Long id;
	private String username;
	private String password;
	@Column(name = "active")
	private Boolean enabled;
	private String email;
	private String fullname;
	@OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "s_user_roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
	private List<Authorities> authorities;
}
