
package com.example.demo.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.respone.CommentDTO;
import com.example.demo.dto.respone.PostDTO;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.Post;

public interface IPostService extends IService<Post> {

	PostDTO updatePost(Long postId, String title, String content, MultipartFile postPhoto)
			throws IOException, ResponseMessage;

	void deletePost(Long postId);

	Iterable<Post> findAllByContentContaining(String content);

	Iterable<Post> findAllByTitleContaining(String title);

	List<Post> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

	List<Post> findAllByCreatedAt(LocalDateTime createDate);

	@Modifying
	@Query(value = "select a from Blog a where a.createdAt <= :createDate")
	public List<Post> findAllWithByCreatedAtBefore(@Param("createDate") LocalDateTime createDate);

	List<Post> findPostByAuthorIdIn(List<Long> followingUserIds);

	List<Post> findAllByUserId(Long id);

	public int getCountOfnumbOfPost(@Param("id") Long id);

	void likePost(Long postId);

	void unlikePost(Long postId);

	CommentDTO createPostComment(Long postId, String content, MultipartFile imageUrl) throws IOException;

	CommentDTO updatePostComment(Long commentId, Long postId, String content, MultipartFile imageUrl) throws IOException;

	void deletePostComment(Long commentId, Long postId);

	PostDTO createNewPost(String title, String content, MultipartFile image) throws IOException ;

	List<Post> findAll();

}
