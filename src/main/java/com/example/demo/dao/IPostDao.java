package com.example.demo.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.respone.PostDTO;
import com.example.demo.entity.Post;

@Repository
public interface IPostDao extends JpaRepository<Post, Long> {

	Iterable<Post> findAllByContentContaining(String content);

	Iterable<Post> findAllByTitleContaining(String title);

	List<Post> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

	List<Post> findAllByCreatedAt(LocalDateTime createDate);

	@Query(value = "select a from Post a where a.createdAt <= :createDate")
	public List<Post> findAllWithByCreatedAtBefore(@Param("createDate") LocalDateTime createDate);

	List<Post> findAllByUserId(Long id);

	List<Post> findPostByUserIdIn(List<Long> followingUserIds);

	@Query(value = "select count(*) As Total from Users U INNER JOIN Blog B On U.id = B.id where U.id=:id", nativeQuery = true)
	public int getCountOfnumbOfPost(@Param("id") Long id);

	List<Post> findAll();

}