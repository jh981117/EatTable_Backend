package com.lec.spring.domain.DTO;

import com.lec.spring.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreReviewAttchmentDto extends BaseEntity {


    private Long storeId;
    private String filename;   // 저장된 파일명 (rename 된 파일명)
    private String imageUrl;


    private boolean isImage;   // 이미지
}
