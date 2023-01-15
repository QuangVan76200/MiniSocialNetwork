//package com.example.demo.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.example.demo.service.AmazonClient;
//
//@RestController
//@RequestMapping("/api/storage/")
//public class TestController {
//
//	@Autowired
//	private AmazonClient amazonClient;
//
////    @Autowired
////    TestController(AmazonClient amazonClient) {
////        this.amazonClient = amazonClient;
////    }
//
//    @PostMapping("/uploadFile")
//    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
//        return this.amazonClient.uploadFile(file);
//    }
//}
