package com.example.demo.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;

public interface ICommentService {

	Optional<Comment> getCommentById(Long commentId);
	
    Comment createNewComment(String content, Post post, MultipartFile imageUrl)throws IOException;
    
    Comment updateComment(Long commentId, String content, MultipartFile imageUrl)throws IOException;
    
    Comment likeComment(Long commentId);
    
    Comment unlikeComment(Long commentId);
    
    void deleteComment(Long commentId);
}
