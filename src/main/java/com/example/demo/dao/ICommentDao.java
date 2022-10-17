package com.example.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Comment;

@Repository
public interface ICommentDao extends JpaRepository<Comment, Long> {

	Comment save(Optional<Comment> targetComment);

}
