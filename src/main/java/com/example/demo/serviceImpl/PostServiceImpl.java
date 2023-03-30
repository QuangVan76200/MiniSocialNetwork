package com.example.demo.serviceImpl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.IFileDBDao;
import com.example.demo.dao.IPostDao;
import com.example.demo.dao.IUserDao;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.exception.EmptyCommentException;
import com.example.demo.exception.InvalidOperationException;
import com.example.demo.service.IPostService;
import com.example.demo.service.IUserService;
import com.github.javafaker.Faker;
import com.microsoft.sqlserver.jdbc.StringUtils;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements IPostService {

    @Autowired
    IUserService iUserService;

    @Autowired
    CommentServiceImpl commentServiceImpl;

    @Autowired
    IPostDao blogDao;

    @Autowired
    IUserDao userDao;

    @Autowired
    HttpServletRequest request;

    @Autowired
    IFileDBDao iFileDBDao;

    @Autowired
    StoreFile storeFile;

    final Faker faker = new Faker();
    final Random random = new Random();

    @Override
    public Post save(Post blog) {
        return blogDao.save(blog);
    }

    @Override
    public Post createNewPost(String title, String content, MultipartFile imageUrl) throws IOException {
        String user = request.getUserPrincipal().getName();
        Optional<User> authUser = iUserService.getAuthenticatedUser(user);

        Post newPost = new Post();
        newPost.setTitle(title);
        newPost.setContent(content);
        newPost.setLikeCount(0);
        newPost.setCommentCount(0);
        newPost.setCreatedAt(new Date());
        newPost.setDateLastModified(new Date());
        newPost.setUser(authUser.get());

        if (imageUrl != null && imageUrl.getSize() > 0) {
            String imgUrl = storeFile.uploadFile(imageUrl).toString();
            newPost.setImageUrl(imgUrl);
        }
        return blogDao.save(newPost);
    }

    @Override
    public Optional<Post> updatePost(Long postId, String title, String content, MultipartFile imageUrl)
            throws IOException, ResponseMessage {
        String user = request.getUserPrincipal().getName();
        Optional<Post> targetPost = blogDao.findById(postId);

        if (targetPost.isEmpty()) {
            throw new ResponseMessage("Id Post does not exist");
        } else {
            if (targetPost.get().getUser().getUserName().equals(user)) {
                targetPost.get().setTitle(title);
                targetPost.get().setContent(content);
                if (imageUrl != null && imageUrl.getSize() > 0) {
                    String imgUrl = storeFile.uploadFile(imageUrl).toString();
                    targetPost.get().setImageUrl(imgUrl);
                } else {
                    throw new ResponseMessage("imageUrl is empty");
                }
                targetPost.get().setDateLastModified(new Date());
                return blogDao.save(targetPost);
            } else {
                new ResponseMessage("Not Author");
            }
        }
        return targetPost;
    }

    @Override
    public void deletePost(Long postId) {
        String user = request.getUserPrincipal().getName();
        Optional<Post> targetPost = blogDao.findById(postId);

        if (targetPost.isEmpty()) {
            new ResponseMessage("Id Post does not exist");
        } else {
            if (targetPost.get().getUser().getUserName().equals(user)) {
                blogDao.deleteById(postId);
            } else {
                throw new InvalidOperationException("Not Author");
            }
        }

    }

    @Override
    public List<Post> findAll() {

        return blogDao.findAll();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return blogDao.findById(id);
    }

    @Override
    public void remove(Long id) {
        blogDao.deleteById(id);
    }

    @Override
    public Iterable<Post> findAllByContentContaining(String content) {
        return blogDao.findAllByContentContaining(content);
    }

    @Override
    public Iterable<Post> findAllByTitleContaining(String title) {
        return blogDao.findAllByTitleContaining(title);
    }

    @Override
    public List<Post> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        System.out.println("startDate" + startDate);
        System.out.println("endDate" + endDate);
        return blogDao.findByCreatedAtBetween(startDate, endDate);
    }

    @Override
    public List<Post> findAllByCreatedAt(LocalDateTime createDate) {
        return blogDao.findAllByCreatedAt(createDate);
    }

    @Override
    public List<Post> findAllWithByCreatedAtBefore(LocalDateTime createDate) {
        return blogDao.findAllWithByCreatedAtBefore(createDate);
    }

    @Override
    public List<Post> findAllByUserId(Long userId) {
        return blogDao.findAllByUserId(userId);
    }

    @Override
    public int getCountOfnumbOfPost(Long id) {
        return blogDao.getCountOfnumbOfPost(id);
    }

    @Override
    public List<Post> findPostByAuthorIdIn(List<Long> followingUserIds) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void likePost(Long postId) {
        String user = request.getUserPrincipal().getName();
        Optional<User> authUser = iUserService.getAuthenticatedUser(user);
        Optional<Post> targetPost = findById(postId);
        if (!targetPost.get().getLikePost().contains(authUser.get())) {
            targetPost.get().setLikeCount(targetPost.get().getLikeCount() + 1);
            targetPost.get().getLikePost().add(authUser.get());
            blogDao.save(targetPost.get());
        } else {
            unlikePost(postId);
        }

    }

    @Override
    public void unlikePost(Long postId) {
        String user = request.getUserPrincipal().getName();
        Optional<User> authUser = iUserService.getAuthenticatedUser(user);
        Optional<Post> targetPost = findById(postId);
        if (targetPost.get().getLikePost().contains(authUser.get())) {
            targetPost.get().setLikeCount(targetPost.get().getLikeCount() - 1);
            targetPost.get().getLikePost().remove(authUser.get());
            blogDao.save(targetPost.get());
        } else {
            throw new InvalidOperationException("targetPost does not exists");
        }

    }

    @Override
    public Comment createPostComment(Long postId, String text, MultipartFile imageUrl) throws IOException {
        if (StringUtils.isEmpty(text))
            throw new EmptyCommentException("Comment is Empty");

//		String user = request.getUserPrincipal().getName();
//		Optional<User> authUser = iUserService.getAuthenticatedUser(user);
        Optional<Post> targetPost = findById(postId);
        Comment savedComment = commentServiceImpl.createNewComment(text, targetPost.get(), imageUrl);
        targetPost.get().setCommentCount(targetPost.get().getCommentCount() + 1);
        blogDao.save(targetPost.get());

        return savedComment;
    }

    @Override
    public Comment updatePostComment(Long commentId, Long postId, String text, MultipartFile imageUrl)
            throws IOException {
        if (StringUtils.isEmpty(text))
            throw new EmptyCommentException("Comment is Empty");
        return commentServiceImpl.updateComment(commentId, text, imageUrl);
    }

    @Override
    public void deletePostComment(Long commentId, Long postId) {
        Optional<Post> targetPost = findById(postId);
        if (targetPost.isPresent()) {
            commentServiceImpl.deleteComment(commentId);
            targetPost.get().setCommentCount(targetPost.get().getCommentCount() - 1);
            blogDao.save(targetPost.get());
        } else {
            throw new InvalidOperationException("targetPost does not exists");
        }
    }

}
