package com.firstproject.resourceserver.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "s_roles")
public class Authorities {

	@Id
	@Column(name = "id_role")
	private String id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "label")
	private String label;
}
