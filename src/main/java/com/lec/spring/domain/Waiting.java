package com.lec.spring.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
public class Waiting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String people;
    private LocalDateTime waitingRegDate;
    private String waitingTime;


    //user 에 name  이랑  phonenumber   user 에서 꺼내기

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name="partnerInfo_id")
    private PartnerInfo partnerInfo;


}
