package com.example.demo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.request.MessageDTO;
import com.example.demo.entity.Message;
import com.example.demo.service.IUserService;
import com.example.demo.serviceImpl.ChatMessageServiceImpl;

@Controller
public class TestChatController {
	

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @Autowired
    private ChatMessageServiceImpl messageService;
    
    @Autowired
	HttpServletRequest request;
	
	@Autowired
	IUserService iUserService;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public MessageDTO receiveMessage(@Payload MessageDTO message){
    	System.out.println(message);
        return message;
    }

    @MessageMapping("/private-message")
    public Message createMessage(@Payload MessageDTO message) throws IOException{
    	String user = request.getUserPrincipal().getName();
		
        simpMessagingTemplate.convertAndSendToUser(user,"/private	",message);
        
        return messageService.newMessage(message);
    }
}
