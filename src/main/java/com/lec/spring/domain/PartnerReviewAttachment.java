package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lec.spring.domain.StoreReview;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PartnerReviewAttachment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;   // 저장된 파일명 (rename 된 파일명)
    private String imageUrl;


    private boolean isImage;   // 이미지

    @ManyToOne
    @JoinColumn(name = "storeReviewId")
    @JsonIgnore
    private StoreReview storeReview;


    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;


}
