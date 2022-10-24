package com.example.demo.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.demo.dto.request.MessageDTO;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.Message;
import com.example.demo.entity.Response;
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
    public MessageDTO receiveMessage(@Payload MessageDTO message) {
        System.out.println(message);
        return message;
    }

    @MessageMapping("/private-message")
    public Message createMessage(@Payload MessageDTO message) throws IOException {
        String user = request.getUserPrincipal().getName();

        simpMessagingTemplate.convertAndSendToUser(user, "/private	", message);

        return messageService.newMessage(message);
    }

    @MessageMapping("/private-update-message")
    public Message updateMessage(@Payload MessageDTO message) throws IOException, ResponseMessage {
        String user = request.getUserPrincipal().getName();

        simpMessagingTemplate.convertAndSendToUser(user, "/private   ", message);

        return messageService.updateMessage(message);
    }

    @MessageMapping("/private-delete-message")
    public ResponseEntity<ResponseMessage> deleteMessage(@Payload MessageDTO message) throws ResponseMessage {
        try {
            String user = request.getUserPrincipal().getName();

            simpMessagingTemplate.convertAndSendToUser(user, "/private   ", message);
            messageService.deleteMessage(message.getMessageId());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Delete successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(e.getMessage()));
        }

    }

    @MessageMapping("/private-getAllMessagesBetweenTwoUsers-message")
    public ResponseEntity<ResponseMessage> getAllMessagesBetweenTwoUsers(@Payload MessageDTO message)
            throws ResponseMessage {
        try {
            String user = request.getUserPrincipal().getName();

            simpMessagingTemplate.convertAndSendToUser(user, "/private   ", message);
            messageService.getAllMessagesBetweenTwoUsers(message.getMessageId());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Delete successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(e.getMessage()));
        }

    }

    @MessageMapping("/private-getall-message")
    public ResponseEntity<ResponseMessage> getAllMessage(@Payload MessageDTO message) throws ResponseMessage {
        try {
            String user = request.getUserPrincipal().getName();

            simpMessagingTemplate.convertAndSendToUser(user, "/private   ", message);
            messageService.getAllMessageDTO();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(e.getMessage()));
        }

    }

    @MessageMapping("/private-countunread-message")
    public ResponseEntity<?> countUnreadMessage(@Payload MessageDTO message) throws ResponseMessage {
        try {
            String user = request.getUserPrincipal().getName();

            simpMessagingTemplate.convertAndSendToUser(user, "/private   ", message);

            int countUnread = messageService.countUnreadMessage(message.getReceiver().getId());

            return ResponseEntity.status(HttpStatus.OK).body(new Response("successfully", "", countUnread));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(e.getMessage()));
        }

    }

    @MessageMapping("/private-updateCompletion-statusMessage")
    public ResponseEntity<Response> updateCompletionStatus(@Payload MessageDTO message) throws ResponseMessage {
        try {
            String user = request.getUserPrincipal().getName();

            simpMessagingTemplate.convertAndSendToUser(user, "/private   ", message);

            MessageDTO updateStatusChat = messageService.updateCompletionStatus(message.getReceiver().getId());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Response("OK", "update sucessfully", updateStatusChat));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Faild", e.getMessage(), null));
        }

    }

}
