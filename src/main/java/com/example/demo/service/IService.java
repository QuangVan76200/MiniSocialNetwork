package com.example.demo.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IService<T> {
	 	T save(T t);
	    Iterable<T> findAll();
	    Optional<T> findById(Long id);
	    void remove(Long id);

}
