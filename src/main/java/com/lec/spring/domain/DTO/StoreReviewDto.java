package com.lec.spring.domain.DTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreReviewDto {
    private Long storeReviewId;
    private String content;
    private Long avg;

    private Long partnerId;
    private String storeName;

    private Long userId;
    private String nickname;
    private String profileImageUrl;
    private Long temperature;

    private List<String> partnerReviewAttachments;



    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDateTime updatedAt;

}
