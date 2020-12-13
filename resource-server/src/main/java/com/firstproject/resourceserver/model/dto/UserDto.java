package com.firstproject.resourceserver.model.dto;

import lombok.Data;

@Data
public class UserDto {
	private Long idUser;
	private String username;
	private String email;
	private String fullname;
	private Long idDetail;
	private String noHp;
	private String fotoProfil;
	private String alamat;
}
