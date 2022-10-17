package com.example.demo.serviceImpl;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.ICommentDao;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.exception.InvalidOperationException;
import com.example.demo.service.ICommentService;
import com.example.demo.service.IFileDBService;
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
	public Comment createNewComment(String content, Post post, MultipartFile imageUrl) throws IOException {

		String user = request.getUserPrincipal().getName();
		Optional<User> authUser = iUserService.getAuthenticatedUser(user);
		Comment newComment = new Comment();
		newComment.setText(content);
		newComment.setCreated(new Date());
		newComment.setModifiedDate(new Date());
		newComment.setUser(authUser.get());
		newComment.setLikeCount(0);
		newComment.setPost(post);
		if (imageUrl != null && imageUrl.getSize() > 0) {
			String imgUrl = storeFile.uploadFile(imageUrl).toString();
			newComment.setImageUrl(imgUrl);
		} else {
			new ResponseMessage("imageUrl is empty");
		}
		return commentDao.save(newComment);
	}

	@Override
	public Comment updateComment(Long commentId, String content, MultipartFile imageUrl) throws IOException {
		String user = request.getUserPrincipal().getName();
		Optional<User> authUser = iUserService.getAuthenticatedUser(user);

		Optional<Comment> targetComment = getCommentById(commentId);

		if (targetComment.get().getUser().getUserName().equals(user)) {
			targetComment.get().setText(content);
			targetComment.get().setModifiedDate(new Date());
			String imgUrl = storeFile.uploadFile(imageUrl).toString();
			targetComment.get().setImageUrl(imgUrl);
			return commentDao.save(targetComment);
		} else {
			throw new InvalidOperationException("Not Author");
		}
	}

	@Override
	public void deleteComment(Long commentId) {
		String user = request.getUserPrincipal().getName();
		Optional<User> authUser = iUserService.getAuthenticatedUser(user);

		Optional<Comment> targetComment = getCommentById(commentId);

		if (targetComment.get().getUser().getUserName().equals(user)) {
			commentDao.deleteById(commentId);
		} else {
			throw new InvalidOperationException("Not Author");
		}

	}

	@Override
	public Comment likeComment(Long commentId) {
		Optional<Comment> targetComment = getCommentById(commentId);
		String user = request.getUserPrincipal().getName();
		Optional<User> authUser = iUserService.getAuthenticatedUser(user);
		if (targetComment.isPresent()) {
			if (!targetComment.get().getLikeList().contains(authUser.get())) {
				targetComment.get().getLikeList().add(authUser.get());
				targetComment.get().setLikeCount(targetComment.get().getLikeCount() + 1);
				commentDao.save(targetComment.get());

			} else {
				unlikeComment(commentId);
			}
		} else {
			throw new InvalidOperationException("Comment does not exists");
		}
		return targetComment.get();
	}

	@Override
	public Comment unlikeComment(Long commentId) {
		Optional<Comment> targetComment = getCommentById(commentId);
		String user = request.getUserPrincipal().getName();
		Optional<User> authUser = iUserService.getAuthenticatedUser(user);
		if (targetComment.get().getLikeList().contains(authUser.get())) {
			targetComment.get().getLikeList().remove(authUser.get());
			targetComment.get().setLikeCount(targetComment.get().getLikeCount() - 1);
			commentDao.save(targetComment.get());
		}

		return targetComment.get();
	}

}
