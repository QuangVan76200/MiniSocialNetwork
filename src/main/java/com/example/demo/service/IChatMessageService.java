package com.example.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.request.MessageDTO;
import com.example.demo.dto.request.UnreadMessagesDTO;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.Message;

public interface IChatMessageService {

	List<Message> findAll();

	Message findById(Long messageId) throws ResponseMessage;

	Message newMessage(MessageDTO message) throws IOException;

	void deleteMessage(Long messageId)throws ResponseMessage;

	MessageDTO updateCompletionStatus(Long secondId) throws ResponseMessage ;

	Message updateMessage(MessageDTO message) throws IOException, ResponseMessage;
	
	List<MessageDTO> getAllMessagesBetweenTwoUsers(Long chatUserId)throws ResponseMessage;
	
	List<MessageDTO> getAllMessageDTO() throws ResponseMessage;
	
	int countUnreadMessage(Long toUserId) throws ResponseMessage;
	
	

}
