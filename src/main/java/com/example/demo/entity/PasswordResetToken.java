//package com.example.demo.entity;
//
//import java.util.Date;
//
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//
//@Entity(name = "passwordRest")
//public class PasswordResetToken {
//    private static final int EXPIRATION = 60 * 24;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
//
//    private String token;
//
//    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false, name = "userId")
//    private User user;
//
//    private Date expiryDate;
//
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Date getExpiryDate() {
//        return expiryDate;
//    }
//
//    public void setExpiryDate(Date expiryDate) {
//        this.expiryDate = expiryDate;
//    }
//
//    public PasswordResetToken(String token, User user) {
//        this.token = token;
//        this.user = user;
//    }
//}
