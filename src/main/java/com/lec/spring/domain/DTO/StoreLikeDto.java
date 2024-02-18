package com.lec.spring.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreLikeDto {
    private Long userId;
    private Long partnerId;
}
