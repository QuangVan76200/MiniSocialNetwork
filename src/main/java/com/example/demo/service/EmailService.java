//package com.example.demo.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//
//	private JavaMailSender javaMailSender;
//
//	@Autowired
//	public EmailService(JavaMailSender javaMailSender) {
//		this.javaMailSender = javaMailSender;
//	}
//
//	@Async
//	public void sendMail(String to, String subject, String text) {
//		SimpleMailMessage message = new SimpleMailMessage();
//		message.setTo(to);
//		message.setSubject(subject);
//		message.setText(text);
//		javaMailSender.send(message);
//
//	}
//
//}
