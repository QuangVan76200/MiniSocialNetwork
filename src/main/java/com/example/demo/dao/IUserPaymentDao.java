package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserPayment;

@Repository
public interface IUserPaymentDao extends JpaRepository<UserPayment, Long> {

}
