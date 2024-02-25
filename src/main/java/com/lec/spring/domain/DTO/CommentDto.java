package com.lec.spring.domain.DTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private String content;
    private Long userId;
    private Long storeReviewId;
    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDateTime updatedAt;
}
