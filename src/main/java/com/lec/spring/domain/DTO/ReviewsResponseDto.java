package com.lec.spring.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewsResponseDto {
    private List<StoreReviewDto> reviews;
    private Double averageRating;
}
