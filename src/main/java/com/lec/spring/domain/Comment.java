package com.lec.spring.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String content;


    @ManyToOne()
    @JoinColumn(name = "storeReviewId") // 외래 키를 명시합니다
    private StoreReview storeReview;

    @ManyToOne
    @JoinColumn(name = "userId")
    @ToString.Exclude
    private User user;



}
