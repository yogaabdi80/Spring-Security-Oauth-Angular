package com.firstproject.resourceserver.service;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import com.firstproject.resourceserver.model.dto.UploadFile;

@Service
public class StorageService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
//	private final Path url=Paths.get("src/main/resources/upload-dir");
	private final Path rootLocation = Paths.get("upload-dir");
	
	public void store(List<UploadFile> uploadFiles,String pointer) {
		try {
			Path rootId = rootLocation.resolve(pointer);
			Resource resource = new UrlResource(rootId.toUri());
			if (!resource.exists()){
				Files.createDirectory(rootId);
			}
			for (UploadFile uploadFile : uploadFiles) {
				Files.copy(uploadFile.getFile().getInputStream(), rootId.resolve(uploadFile.getFilename()),StandardCopyOption.REPLACE_EXISTING);				
			}			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("FAIL!");
		}
	}
	
	public File loadFile(String filename, String pointer) {
//		System.out.println(rootLocation);
		try {			
			File file;
			file = rootLocation.resolve(pointer+"/"+filename).toFile();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
		
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}
}
