package com.example.demo.serviceImpl;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.example.demo.dao.ICommentDao;
import com.example.demo.dto.respone.CommentDTO;
import com.example.demo.dto.respone.PostDTO;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.exception.CommentNotFoundException;
import com.example.demo.exception.InvalidOperationException;
import com.example.demo.service.ICommentService;
import com.example.demo.service.IUserService;

@Service
public class CommentServiceImpl implements ICommentService {

	@Autowired
	ICommentDao commentDao;

	@Autowired
	HttpServletRequest request;

	@Autowired
	IUserService iUserService;

	@Autowired
	StoreFile storeFile;

	@Override
	public Optional<Comment> getCommentById(Long commentId) {
		return commentDao.findById(commentId);
	}

	@Override
	public CommentDTO createNewComment(String content, Post post, MultipartFile imageUrl) throws IOException {

		String user = request.getUserPrincipal().getName();
		User authUser = iUserService.getAuthenticatedUser(user)
				.orElseThrow(() -> new UserNotFoundException("User not found"));

		Comment newComment = new Comment();
		newComment.setText(content);
		newComment.setCreated(new Date());
		newComment.setModifiedDate(new Date());
		newComment.setUser(authUser);
		newComment.setLikeCount(0);
		newComment.setPost(post);
		if (imageUrl != null && imageUrl.getSize() > 0) {
			String imgUrl = storeFile.uploadFile(imageUrl).toString();
			newComment.setImageUrl(imgUrl);
		}

		Comment saveComment = commentDao.save(newComment);
		ModelMapper modelMapper = new ModelMapper();

		CommentDTO commnetDTO = modelMapper.map(saveComment, CommentDTO.class);
		return commnetDTO;
	}

	@Override
	public CommentDTO updateComment(Long commentId, String content, MultipartFile imageUrl) throws IOException {
		String user = request.getUserPrincipal().getName();
		Optional<User> authUser = iUserService.getAuthenticatedUser(user);

		Optional<Comment> targetComment = getCommentById(commentId);
		Comment comment = targetComment.orElseThrow(() -> new CommentNotFoundException("Comment not exists"));

		if (!comment.getUser().getUserName().equals(user)) {
			throw new InvalidOperationException("Not Author");
		}

		comment.setText(content);
		comment.setModifiedDate(new Date());

		if (imageUrl != null && imageUrl.getSize() > 0) {
			String imgUrl = storeFile.uploadFile(imageUrl).toString();
			comment.setImageUrl(imgUrl);
		}

		Comment updatedComment = commentDao.save(comment);
		ModelMapper modelMapper = new ModelMapper();
		CommentDTO commentDTO = modelMapper.map(updatedComment, CommentDTO.class);
		return commentDTO;
	}

	@Override
	public void deleteComment(Long commentId) {
		String user = request.getUserPrincipal().getName();
		Optional<User> authUser = iUserService.getAuthenticatedUser(user);

		Optional<Comment> targetComment = getCommentById(commentId);
		Comment comment = targetComment.orElseThrow(() -> new CommentNotFoundException("Comment not exists"));

		if (!comment.getUser().getUserName().equals(user)) {
			throw new InvalidOperationException("Not Author");
		}

		commentDao.deleteById(commentId);

	}

	@Override
	public CommentDTO likeComment(Long commentId) {
		Optional<Comment> targetComment = getCommentById(commentId);
		Comment comment = targetComment.orElseThrow(() -> new CommentNotFoundException("Comment not exists"));
		String user = request.getUserPrincipal().getName();
		User authUser = iUserService.getAuthenticatedUser(user).orElseThrow();

		boolean isLiked = comment.getLikeList().contains(authUser);

		if (!isLiked) {
			comment.getLikeList().add(authUser);
			comment.setLikeCount(comment.getLikeCount() + 1);
			comment = commentDao.save(comment);
		}
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(targetComment, CommentDTO.class);
	}

	@Override
	public CommentDTO unlikeComment(Long commentId) {
		Optional<Comment> targetComment = getCommentById(commentId);
		Comment comment = targetComment.orElseThrow(() -> new CommentNotFoundException("Comment not exists"));

		String user = request.getUserPrincipal().getName();
		User authUser = iUserService.getAuthenticatedUser(user).orElseThrow();

		boolean isLiked = comment.getLikeList().contains(authUser);
		if (isLiked) {
			comment.getLikeList().remove(authUser);
			comment.setLikeCount(comment.getLikeCount() - 1);
			comment = commentDao.save(comment);
		}

		ModelMapper modelMapper = new ModelMapper();
		return modelMapper.map(targetComment, CommentDTO.class);
	}

}
