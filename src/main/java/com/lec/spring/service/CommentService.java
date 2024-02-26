package com.lec.spring.service;

import com.lec.spring.domain.*;
import com.lec.spring.domain.DTO.CommentDto;
import com.lec.spring.repository.CommentRepository;
import com.lec.spring.repository.StoreReviewRepository;
import com.lec.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {


    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final StoreReviewRepository storeReviewRepository;





    public Comment addComment(CommentDto commentDto) {
        User user = userRepository.findById(commentDto.getUserId()).orElse(null);
        StoreReview storeReview = storeReviewRepository.findById(commentDto.getStoreReviewId()).orElse(null);
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .user(user)
                .storeReview(storeReview)
                .build();
        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElse(null);
    }

    public void updateComment(Long commentId, Comment updatedComment) {
        Comment comment = getCommentById(commentId);

        comment.setContent(updatedComment.getContent());

        commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<Comment> getListComment(Long reviewId) {
        return commentRepository.findByStoreReviewId(reviewId);
    }
}
