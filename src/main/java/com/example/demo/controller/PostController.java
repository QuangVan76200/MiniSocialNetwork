package com.example.demo.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.IPostDao;
import com.example.demo.dao.IUserDao;
import com.example.demo.dto.respone.CommentResponse;
import com.example.demo.dto.respone.ResponseMessage;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.entity.Response;
import com.example.demo.entity.User;
import com.example.demo.serviceImpl.CommentServiceImpl;
import com.example.demo.serviceImpl.PostServiceImpl;
import com.example.demo.serviceImpl.StoreFile;

@RestController

@RequestMapping("/blogs")
public class PostController {
	@Autowired
	PostServiceImpl blogServiceImpl;

	@Autowired
	CommentServiceImpl commentServiceImpl;

	@Autowired
	IPostDao postDao;

	@Autowired
	IUserDao userDao;

	@Autowired
	StoreFile storeFile;

	@GetMapping("/findall")
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(blogServiceImpl.findAll(), HttpStatus.OK);
	}

	@PostMapping("/addPost")
	public ResponseEntity<?> createPost(@RequestParam String title, @RequestParam String content,
			@RequestParam MultipartFile imageUrl) throws IOException {
		Post newPost = blogServiceImpl.createNewPost(title, content, imageUrl);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("OK", "Insert newpost sucessfully", newPost));
	}

	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			storeFile.uploadFile(file);
			message = "" + "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + "!" + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> findbyId(@PathVariable Long id) {
		Optional<Post> findPost = blogServiceImpl.findById(id);
		return findPost.isPresent()
				? ResponseEntity.status(HttpStatus.OK).body(new Response("Ok", "sucessfully", findPost))
				: ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new Response("Failed", "cannot found Post with", id));
	}

	@PostMapping("/post/update/{postId}")
	public ResponseEntity<Response> findbyId(@PathVariable("postId") Long postId, String title, String content,
			MultipartFile imageUrl) throws IOException {
		try {
			Optional<Post> savedPost = blogServiceImpl.updatePost(postId, title, content, imageUrl);
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Ok", "updated successfully", savedPost));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Failed", e.getMessage(), null));
		}
		

	}

	@PostMapping("/posts/{postId}/delete")
	public ResponseEntity<Response> delete(@PathVariable Long postId) {
		blogServiceImpl.deletePost(postId);

		return ResponseEntity.status(HttpStatus.OK).body(new Response("Successfully", "Delete successfully with id Post", postId));
	}

	@GetMapping("/search-by-content")
	public ResponseEntity<?> findAllByContentContaining(@RequestParam String content) {
		return new ResponseEntity<>(blogServiceImpl.findAllByContentContaining(content), HttpStatus.OK);
	}

	@GetMapping("/search-by-title")
	public ResponseEntity<?> findAllByTitleContaining(@RequestParam String title) {
		return new ResponseEntity<>(blogServiceImpl.findAllByTitleContaining(title), HttpStatus.OK);
	}

	@GetMapping("/search-by-createDate-between")
	public ResponseEntity<?> findAllByCreateDay(@RequestParam LocalDateTime startDate,
			@RequestParam LocalDateTime endDate) {
		System.out.println("startDate" + startDate);
		System.out.println("endDate" + endDate);
		if (endDate.getDayOfYear() <= startDate.getDayOfYear()) {
			return new ResponseEntity<>(new ResponseMessage("Date entered is wrong format, please re-enter"),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(blogServiceImpl.findByCreatedAtBetween(startDate, endDate), HttpStatus.OK);
	}

	@GetMapping("/search-by-createDate-before")
	public ResponseEntity<Response> findAllByCreateDayBefore(LocalDateTime createDate) {
		LocalDateTime dateTime = LocalDateTime.now();
		if (createDate.getDayOfYear() > dateTime.getDayOfYear()) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
					new Response("Faild", "Date must be less than or equals current date", dateTime.getDayOfYear()));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Response("successfull", "Search suucessfully",
				blogServiceImpl.findAllWithByCreatedAtBefore(createDate)));
	}

	@GetMapping("/search-by-createDate")
	public ResponseEntity<Response> findAllByCreatedAt(LocalDateTime createDate) {
		if (createDate.getDayOfYear() > Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(new Response("Faild",
					"Date must be less than or equals current date", Calendar.getInstance().get(Calendar.DAY_OF_YEAR)));
		}
		return ResponseEntity.status(HttpStatus.OK).body(
				new Response("successfull", "Search suucessfully", blogServiceImpl.findAllByCreatedAt(createDate)));
	}

	@GetMapping("/find-post-by-userid")
	public ResponseEntity<Response> findAllByUserId(@RequestParam Long id) {
		Optional<User> findUser = userDao.findById(id);
		System.out.println(findUser.toString());
		if (findUser.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new Response("Empty", "No have post with userID", id));
		}
		return ResponseEntity.status(HttpStatus.OK).body(
				new Response("Successfully", "Serach by UserId successfully", blogServiceImpl.findAllByUserId(id)));
	}

	@PostMapping("/posts/{postId}/like")
	public ResponseEntity<ResponseMessage> likePost(@PathVariable("postId") Long postId) {
		blogServiceImpl.likePost(postId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("posts/{postId}/unlike")
	public ResponseEntity<ResponseMessage> unlikePost(@PathVariable("postId") Long postId) {
		blogServiceImpl.unlikePost(postId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/posts/{postId}/comments/create")
	public ResponseEntity<Response> createPostComment(@PathVariable Long postId, @RequestParam String text,
			@RequestParam MultipartFile imageUrl) throws IOException {
		Comment savedComment = blogServiceImpl.createPostComment(postId, text, imageUrl);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("create comment succesfull", "", savedComment));
	}

	@PostMapping("/posts/{postId}/comments/{commentId}/update")
	public ResponseEntity<Response> updatePost(@PathVariable("commentId") Long commentId,
			@PathVariable("postId") Long postId, @RequestParam String text, MultipartFile imageUrl) throws IOException {
		Comment savedComment = blogServiceImpl.updatePostComment(commentId, postId, text, imageUrl);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("upadate comment successfull", "", savedComment));
	}

	@PostMapping("/posts/{postId}/comments/{commentId}/delete")
	public ResponseEntity<Response> deleteComment(@PathVariable("commentId") Long commentId,
			@PathVariable("postId") Long postId) {
		blogServiceImpl.deletePostComment(commentId, postId);

		return ResponseEntity.status(HttpStatus.OK).body(new Response("delete successfully", "Id comment", commentId));
	}

	@PostMapping("/posts/comments/{commentId}/like")
	public ResponseEntity<Response> likeComment(@PathVariable("commentId") Long commentId) {
		commentServiceImpl.likeComment(commentId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
