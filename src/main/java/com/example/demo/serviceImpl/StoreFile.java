package com.example.demo.serviceImpl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.ImageFile;
import com.example.demo.service.IFileDBService;

@Component
public class StoreFile {
	
	@Autowired
	IFileDBService iFileDBService;
	
	public ImageFile uploadFile(MultipartFile file) throws IOException {

		ImageFile iFileStrorage = iFileDBService.store(file);

		return iFileStrorage;

	}

}
