package com.example.demo.serviceImpl;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.IFileDBDao;
import com.example.demo.entity.ImageFile;
import com.example.demo.service.IFileDBService;

@Service
public class FileDBServiceImpl implements IFileDBService {

	@Autowired
	IFileDBDao fileDBDao;

	@Override
	public ImageFile store(MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		ImageFile image = new ImageFile(fileName, file.getBytes(), file.getContentType());

		return fileDBDao.save(image);
	}


	@Override
	public Stream<ImageFile> getAllFiles() {
		return fileDBDao.findAll().stream();
	}

}
