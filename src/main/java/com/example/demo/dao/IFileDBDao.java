package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.ImageFile;

public interface IFileDBDao extends JpaRepository<ImageFile, Long> {

}
