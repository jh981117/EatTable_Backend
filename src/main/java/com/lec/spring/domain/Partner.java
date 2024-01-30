package com.lec.spring.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Partner {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String storeName;
    @Column(nullable = false)
    private String partnerName;
    @Column(nullable = false)
    private String partnerPhone;
    @Column(nullable = false)
    private String storePhone;

    @Embedded
    private Address address;

    @ColumnDefault(value = "0")
    private String tableCnt;

    private String openTime;

    private String info;

    private String reserveInfo;

    private String favorite;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    @Enumerated(value = EnumType.STRING)
    private TrueFalse parking;


    @Enumerated(value = EnumType.STRING)
    private TrueFalse corkCharge;


    @Enumerated(value = EnumType.STRING)
    private TrueFalse partnerState;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "partnerId")
    @ToString.Exclude
    private List<PartnerAttachment> fileList = new ArrayList<>();


    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "userId")
    private User user;



    @PrePersist
    public void prePersist() {
        regDate = LocalDateTime.now();
    }


}
