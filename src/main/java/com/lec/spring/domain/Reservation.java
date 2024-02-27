package com.lec.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ColumnDefault(value = "0")
    private int people;

    @Enumerated(value = EnumType.STRING)
    private TrueFalse reservationState;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 ")
    private String reservationRegDate;

    private String Time;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "partnerId")
    private Partner partner;
}
