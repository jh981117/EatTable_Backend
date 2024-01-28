package com.lec.spring.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Data
@Entity
public class Waiting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ColumnDefault(value = "0")
    private int people;

    @Enumerated(value = EnumType.STRING)
    private TrueFalse waitingState;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime waitingRegDate;


    private String Time;


    //user 에 name  이랑  phonenumber   user 에서 꺼내기

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @ManyToOne
    @JoinColumn(name="partnerId")
    private Partner partner;


    @PrePersist
    public void prePersist (){
        this.waitingRegDate = LocalDateTime.now();
    }


}
