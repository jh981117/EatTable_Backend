package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReviewLike {

    @EmbeddedId
    private ReviewLikeId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @ManyToOne
    @MapsId("storeReviewId")
    @JoinColumn(name = "reviewId")
    private StoreReview storeReview;


}