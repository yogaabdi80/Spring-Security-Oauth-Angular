package com.firstproject.resourceserver.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.firstproject.resourceserver.model.dto.ApiResponse;
import com.firstproject.resourceserver.model.dto.UploadFile;
import com.firstproject.resourceserver.model.dto.UserDto;
import com.firstproject.resourceserver.model.entity.User;
import com.firstproject.resourceserver.model.entity.UserDetail;
import com.firstproject.resourceserver.repository.UserDetailRepo;
import com.firstproject.resourceserver.repository.UserRepository;
import com.firstproject.resourceserver.service.StorageService;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class ApiUser {

	@Autowired
	private UserDetailRepo detailRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private StorageService storage;

	@GetMapping("/info/{idUser}")
	private UserDto get(@PathVariable String idUser) {
		UserDto userDto = new UserDto();
		UserDetail detail = new UserDetail();
		User user = new User();
		user = userRepo.findById(Long.parseLong(idUser)).get();
		detail = user.getUserDetail();
		mapper.map(user, userDto);
		mapper.map(detail, userDto);
		userDto.setIdUser(user.getId());
		userDto.setIdDetail(detail.getId());
		return userDto;
	}

	@PostMapping("/saveUser")
	private ApiResponse<?> saveUser(@RequestPart UserDto userDto, @RequestParam(required = false) MultipartFile foto) {
		List<UploadFile> uploadFiles = new ArrayList<UploadFile>();
		UploadFile uploadFile = new UploadFile();
		if (foto != null) {
			uploadFile = new UploadFile();
			uploadFile.setFile(foto);
			uploadFile.setFilename(
					"Foto_" + userDto.getIdUser() + "." + FilenameUtils.getExtension(foto.getOriginalFilename()));
			userDto.setFotoProfil(uploadFile.getFilename());
			uploadFiles.add(uploadFile);
		}
		storage.store(uploadFiles, "User");
		UserDetail detail = new UserDetail();
		detail.setId(userDto.getIdDetail());
		mapper.map(userDto, detail);
		User user = new User();
		user.setId(userDto.getIdUser());
		mapper.map(userDto,user);
		detail.setUser(user);
		detailRepo.save(detail);
		return new ApiResponse<>("Berhasil Disimpan", 200, null);
	}

	@GetMapping("/getProfil/{id}")
	private void getProfil(@PathVariable String id, HttpServletResponse response) {
		File file;
		if(userRepo.findById(Long.parseLong(id)).get().getUserDetail().getFotoProfil()!=null) {			
			file = storage.loadFile(userRepo.findById(Long.parseLong(id)).get().getUserDetail().getFotoProfil(), "User");
			InputStream inputStream = null;
			try {
				inputStream = new BufferedInputStream(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				response.flushBuffer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@GetMapping("/getGambarProfil/{filename}")
	private void getGambarProfil(@PathVariable String filename, HttpServletResponse response) {
		File file = storage.loadFile(filename, "User");
		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
