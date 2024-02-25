package com.lec.spring.controller;

import com.lec.spring.domain.Comment;
import com.lec.spring.domain.DTO.CommentDto;
import com.lec.spring.domain.StoreReview;
import com.lec.spring.domain.User;
import com.lec.spring.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@CrossOrigin
public class CommentController {



    private final CommentService commentService;





    @PostMapping("/write")
    public ResponseEntity<Comment> addComment(@RequestBody CommentDto commentDto) {
        System.out.println(commentDto + "231123123123123123123");
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        // Set userId and storeReviewId from DTO
        User user = new User();
        user.setId(commentDto.getUserId());
        comment.setUser(user);

        StoreReview storeReview = new StoreReview();
        storeReview.setId(commentDto.getStoreReviewId());
        comment.setStoreReview(storeReview);

        Comment addedComment = commentService.addComment(comment);
        return new ResponseEntity<>(addedComment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) {
        Comment comment = commentService.getCommentById(commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable Long commentId, @RequestBody Comment updatedComment) {
        commentService.updateComment(commentId, updatedComment);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
