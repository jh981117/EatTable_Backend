package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lec.spring.domain.StoreReview;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @ToString.Exclude
    private StoreReview storeReview;







}
