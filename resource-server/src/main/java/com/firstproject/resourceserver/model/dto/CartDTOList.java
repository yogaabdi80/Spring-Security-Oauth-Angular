package com.firstproject.resourceserver.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class CartDTOList {
	private Long id;
	private Long idUser;
	private List<CartDTO> cartDTO;
}
