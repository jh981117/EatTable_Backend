package com.lec.spring.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class PartnerReq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String managerName;
    @Column(nullable = false)
    private String storeName;
    @Column(nullable = false)
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    @Enumerated(value = EnumType.STRING)
    private PartnerReqState partnerReqState;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "partnerId")
    private Partner partner;


    @PrePersist
    public void prePersist (){
        this.regDate = LocalDateTime.now();
    }
}
