package com.example.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.request.MessageDTO;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.Message;

public interface IChatMessageService {

	List<Message> findAll();

	Message findById(Long messageId) throws ResponseMessage;

	Message newMessage(MessageDTO message) throws IOException;

	void deleteMessage(Long messageId)throws ResponseMessage;

	Optional<Message> updateCompletionStatus(Long id, boolean status);

	Message updateMessage(MessageDTO message) throws IOException, ResponseMessage ;

}
