package com.lec.spring.domain;


import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class ReviewLikeId implements Serializable {
    private Long userId;
    private Long storeReviewId;
}
