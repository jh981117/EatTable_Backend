package com.lec.spring.domain.DTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.lec.spring.domain.PartnerReqState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PartnerReqDto {
    private Long id;
    private Long userId;
    private String managerName;
    private String storeName;
    private String phone;
    private String memo;
    private PartnerReqState partnerReqState;

    @JsonFormat(pattern = "yyyy년 MM월 dd일")
    private LocalDateTime createdAt;



}
