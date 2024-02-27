package com.lec.spring.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Waiting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ColumnDefault(value = "0")
    private int people;

    @Enumerated(value = EnumType.STRING)
    private TrueFalse waitingState;

    @JsonFormat(pattern = "yyyy년 MM월 dd일 ")
    private String waitingRegDate;


    private String Time;


    //user 에 name  이랑  phonenumber   user 에서 꺼내기

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne
    @JoinColumn(name="partnerId")
    private Partner partner;





}
