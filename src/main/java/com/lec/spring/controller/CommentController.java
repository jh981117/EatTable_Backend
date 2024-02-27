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

        return new ResponseEntity<>(commentService.addComment(commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/list/{reviewId}")
    public ResponseEntity<List<Comment>> getAllComments(@PathVariable Long reviewId) {
        List<Comment> comments = commentService.getListComment(reviewId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long commentId) {
        Comment comment = commentService.getCommentById(commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody Comment updatedComment) {
        Comment updated = commentService.updateComment(commentId, updatedComment);
        return ResponseEntity.ok(updated); // 수정된 댓글 객체 반환
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
