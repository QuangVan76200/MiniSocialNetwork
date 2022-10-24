package com.example.demo.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.IChatMessageDao;
import com.example.demo.dao.IUserDao;
import com.example.demo.dto.request.MessageDTO;
import com.example.demo.dto.request.UnreadMessagesDTO;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.Message;
import com.example.demo.entity.MessageStatus;
import com.example.demo.entity.User;
import com.example.demo.exception.InvalidOperationException;
import com.example.demo.service.IChatMessageService;
import com.example.demo.service.IUserService;

@Service
@Transactional
public class ChatMessageServiceImpl implements IChatMessageService {

	@Autowired
	IChatMessageDao messageDao;

	@Autowired
	HttpServletRequest request;

	@Autowired
	IUserService iUserService;

	@Autowired
	StoreFile storeFile;

	@Autowired
	IUserDao userDao;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<Message> findAll() {
		return messageDao.findAll();
	}

	@Override
	public Message findById(Long messageId) throws ResponseMessage {
		return messageDao.findById(messageId).map(t -> {
			t.setStatusChat(MessageStatus.MESSAGE);
			return messageDao.save(t);
		}).orElseThrow(() -> new ResponseMessage("Can't not find message with id " + messageId));
	}

	@Override
	public Message newMessage(MessageDTO message) throws IOException {
		String user = request.getUserPrincipal().getName();
		Optional<User> authUser = iUserService.getAuthenticatedUser(user);

		Message newMessage = new Message();
		newMessage.setMessage(message.getMessage());
		newMessage.setFromUser(authUser.get());
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
		if (targetMessage.getFromUser().getUserName().equals(user)) {
			messageDao.delete(targetMessage);
		} else {
			throw new InvalidOperationException("Not Author");
		}
	}

	@Override
	public Message updateMessage(MessageDTO message) throws IOException, ResponseMessage {
		String user = request.getUserPrincipal().getName();
		Message targetMessage = findById(message.getMessageId());
		if (targetMessage.getFromUser().getUserName().equals(user)) {
			Message newMessage = new Message();
			newMessage.setMessage(message.getMessage());
			if (message.getImageUrl() != null && message.getImageUrl().getSize() > 0) {
				String imgUrl = storeFile.uploadFile(message.getImageUrl()).toString();
				newMessage.setImageUrl(imgUrl);
			} else {
				new ResponseMessage("imageUrl is empty");
			}
			return messageDao.save(newMessage);
		} else {
			throw new ResponseMessage("Not Author");
		}
	}

	@Override
	public List<MessageDTO> getAllMessagesBetweenTwoUsers(Long chatUserId) throws ResponseMessage {
		String user = request.getUserPrincipal().getName();
		Optional<User> authUser = iUserService.getAuthenticatedUser(user);
		if (authUser.get().getId() == null) {
			throw new ResponseMessage("Unauthorized");
		}
		User userActor = userDao.findById(chatUserId).orElseThrow(() -> new ResponseMessage("Unauthorized"));

		List<Message> allMessagesBetweenTwoUsers = messageDao.findAllMessagesBetweenTwoUsers(chatUserId,
				userActor.getId());

		return allMessagesBetweenTwoUsers.stream().map(message -> modelMapper.map(message, MessageDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<MessageDTO> getAllMessageDTO() throws ResponseMessage {
		String user = request.getUserPrincipal().getName();
		Optional<User> authUser = iUserService.getAuthenticatedUser(user);
		if (authUser.get().getId() != null) {
			throw new ResponseMessage("Unauthorized");
		}
		List<Message> allUnreadMessages = messageDao.getAllMessages(authUser.get().getId());

		List<MessageDTO> messageUnread = allUnreadMessages.stream()
				.map(message -> modelMapper.map(message, MessageDTO.class)).collect(Collectors.toList());
		return messageUnread;
	}

    @Override
    public MessageDTO updateCompletionStatus(Long secondId) throws ResponseMessage  {
        String user = request.getUserPrincipal().getName();
        Optional<User> authUser = iUserService.getAuthenticatedUser(user);
        if (authUser.get().getId() != null) {
            throw new ResponseMessage("Unauthorized");
        }
        
        Optional<Message> updateStatus = messageDao.updateCompletionStatus(authUser.get().getId(), secondId);
        
        ModelMapper mapper = new ModelMapper();
        
        return mapper.map(updateStatus, MessageDTO.class);
    }

    @Override
    public int countUnreadMessage(Long toUserId)  throws ResponseMessage {
        String user = request.getUserPrincipal().getName();
        Optional<User> authUser = iUserService.getAuthenticatedUser(user);
        if (authUser.get().getId() != null) {
            throw new ResponseMessage("Unauthorized");
        }
        return messageDao.countUnreadMessage(authUser.get().getId(), toUserId);
    }

}
