package com.lec.spring.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {

    private int people;
    private String reservationState;


    private String reservationRegDate;
    private String Time;
    private Long userId;
    private Long partnerId;

}
