package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UserPayment;

public interface IUserPaymentDao extends JpaRepository<UserPayment, Long> {

}
