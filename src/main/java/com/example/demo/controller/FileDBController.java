package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.dao.IFileDBDao;
import com.example.demo.dto.respone.FileDBResponse;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.service.IFileDBService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FileDBController {
	
	@Autowired
	IFileDBDao iFileDBDao;
	
	@Autowired
	IFileDBService iFileDBService;
	
	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file){
		String message ="";
		try {
			iFileDBService.store(file);
			
			 message = ""+"Uploaded the file successfully: " + file.getOriginalFilename();
			 return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Could not upload the file: "+"!"+  file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}
	
	@GetMapping("/files")
	public ResponseEntity<List<FileDBResponse>> getListFiles(){
		List<FileDBResponse> files = iFileDBService.getAllFiles().map(dbFile->{
			String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/auth/files/")
                    .path(dbFile.getPostImageId().toString())
                    .toUriString();
			
			return new FileDBResponse(dbFile.getTitle(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getFiles().length);
        }).collect(Collectors.toList());
		
		return ResponseEntity.status(HttpStatus.OK).body(files);
	}

}
