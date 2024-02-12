package com.lec.spring.domain.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lec.spring.domain.Address;
import com.lec.spring.domain.PartnerAttachment;
import com.lec.spring.domain.TrueFalse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Data
@Builder
public class PartnerDto  {
    private Long id;
    private String storeName;

    private String partnerName;

    private Long userId;

    private String partnerPhone;

    private String storePhone;

    private Address address;

    private String tableCnt;

    private String openTime;

    private String storeInfo;

    private String reserveInfo;

    private String favorite;

    private String viewCnt;

    private TrueFalse parking;

    private TrueFalse corkCharge;

    private TrueFalse  dog;

    private TrueFalse partnerState;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH시 mm분")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 HH시 mm분")
    private LocalDateTime updatedAt;





    @Builder.Default
    private List<PartnerAttachment> fileList = new ArrayList<>();
}
