package com.lec.spring.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Builder
public class PartnerReq extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String managerName;
    @Column(nullable = false)
    private String storeName;
    @Column(nullable = false)
    private String phone;

    private String memo;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime regDate;

    @Enumerated(value = EnumType.STRING)
    private PartnerReqState partnerReqState;

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "userId")
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "partnerId")
//    private Partner partner;


//    @PrePersist
//    public void prePersist (){
//        this.regDate = LocalDateTime.now();
//    }
}
