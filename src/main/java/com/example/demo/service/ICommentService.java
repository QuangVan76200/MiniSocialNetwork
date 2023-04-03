package com.example.demo.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.respone.CommentDTO;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;

public interface ICommentService {

	Optional<Comment> getCommentById(Long commentId);
	
    CommentDTO createNewComment(String content, Post post, MultipartFile imageUrl)throws IOException;
    
    CommentDTO updateComment(Long commentId, String content, MultipartFile imageUrl)throws IOException;
    
    CommentDTO likeComment(Long commentId);
    
    CommentDTO unlikeComment(Long commentId);
    
    void deleteComment(Long commentId);
}
