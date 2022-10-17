package com.example.demo.service;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.ImageFile;

public interface IFileDBService {
	ImageFile store(MultipartFile file) throws IOException;

    Stream<ImageFile> getAllFiles();

}
