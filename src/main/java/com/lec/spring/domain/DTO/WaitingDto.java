package com.lec.spring.domain.DTO;

import com.lec.spring.domain.Partner;
import com.lec.spring.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaitingDto {

    private int people;

    private String waitingState;

    private String waitingRegDate;

    private String Time;

    private Long userId;


    private Long partnerId;
}
