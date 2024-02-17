package com.lec.spring.domain;

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
public class PartnerReviewAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;   // 저장된 파일명 (rename 된 파일명)
    private String imageUrl;
    private String description;
    private LocalDateTime regDate;

    private boolean isImage;   // 이미지

    @ManyToOne
    @JoinColumn(name = "storeReviewId")
    private StoreReview storeReview;


}
