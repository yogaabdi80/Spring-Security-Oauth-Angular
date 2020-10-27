package com.firstproject.resourceserver.model.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UploadFile {
	private String filename;
	private MultipartFile file;
}
