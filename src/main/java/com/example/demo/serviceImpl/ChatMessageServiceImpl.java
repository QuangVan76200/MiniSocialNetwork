package com.example.demo.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.IChatMessageDao;
import com.example.demo.dto.request.MessageDTO;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.Message;
import com.example.demo.entity.MessageStatus;
import com.example.demo.entity.User;
import com.example.demo.exception.InvalidOperationException;
import com.example.demo.service.IChatMessageService;
import com.example.demo.service.IUserService;

@Service
public class ChatMessageServiceImpl implements IChatMessageService {

	@Autowired
	IChatMessageDao messageDao;
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	IUserService iUserService;
	
	@Autowired
	StoreFile storeFile;

	@Override
	public List<Message> findAll() {
		return messageDao.findAll();
	}

	@Override
	public Message findById(Long messageId) throws ResponseMessage {
		return messageDao.findById(messageId).map(t -> {
			t.setStatus(MessageStatus.MESSAGE);
			return messageDao.save(t);
		}).orElseThrow(() -> new ResponseMessage("Can't not find message with id " + messageId));
	}

	@Override
	public Message newMessage(MessageDTO message) throws IOException {
		String user = request.getUserPrincipal().getName();
		Optional<User> authUser = iUserService.getAuthenticatedUser(user);
		
		Message newMessage = new Message();
		newMessage.setMessage(message.getMessage());
		newMessage.setUser(authUser.get());
		if (message.getImageUrl() != null && message.getImageUrl().getSize() > 0) {
			String imgUrl = storeFile.uploadFile(message.getImageUrl()).toString();
			newMessage.setImageUrl(imgUrl);
		} else {
			new ResponseMessage("imageUrl is empty");
		}
		return messageDao.save(newMessage);
	}

	@Override
	public void deleteMessage(Long messageId) throws ResponseMessage {
		String user = request.getUserPrincipal().getName();
		Message targetMessage = findById(messageId);
		if(targetMessage.getUser().getUserName().equals(user)) {
			messageDao.delete(targetMessage);
		} else {
			throw new InvalidOperationException("Not Author");
		}
	}

	@Override
	public Optional<Message> updateCompletionStatus(Long id, boolean status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message updateMessage(MessageDTO message) throws IOException, ResponseMessage {
		String user = request.getUserPrincipal().getName();
		Optional<User> authUser = iUserService.getAuthenticatedUser(user);
		Message targetMessage = findById(message.getMessageId());
		if(targetMessage.getUser().getId()==authUser.get().getId()) {
			Message newMessage = new Message();
			newMessage.setMessage(message.getMessage());
			if (message.getImageUrl() != null && message.getImageUrl().getSize() > 0) {
				String imgUrl = storeFile.uploadFile(message.getImageUrl()).toString();
				newMessage.setImageUrl(imgUrl);
			} else {
				new ResponseMessage("imageUrl is empty");
			}
			return messageDao.save(newMessage);
		}
		else {
			throw new ResponseMessage("Not Author");
		}
	}

}
