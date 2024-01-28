package com.lec.spring.service;

import com.lec.spring.domain.*;
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


    // 해당 리뷰에 댓글
    public QryCommentList list(Long id){
        QryCommentList list = new QryCommentList();
        List<Comment> comments = commentRepository.findByStoreReviewId(id);

        list.setCount(comments.size()); //댓글 갯수
        list.setList(comments);
        list.setStatus("OK");

        return list;

    }



    public QryResult write(Long storeReviewId, Long userId, String content){

        User user = userRepository.findById(userId).orElse(null);
        StoreReview storeReview = storeReviewRepository.findById(storeReviewId).orElse(null);

        Comment comment= Comment.builder()
                .user(user)
                .content(content)
                .storeReview(storeReview)
                .build();
        commentRepository.save(comment);

        QryResult result = QryResult.builder()
                .count(1)
                .status("OK")
                .build();

        return result;
    }


    public QryResult delte(Long id){
        int count = commentRepository.deleteByIdint(id);
        String status = "FAIL";

        if(count > 0) status = "OK";

        QryResult result = QryResult.builder()
                .count(count)
                .status(status)
                .build();

        return result;
    }




}
