package com.lec.spring.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class PartnerInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String phone;
    private String area;
    private String tableCnt;
    private String openTime;
    private String info;
    private String parking;




    @OneToMany(mappedBy = "partnerInfo")
    private List<PartnerReview> partnerReviews = new ArrayList<>();


    @OneToMany(mappedBy = "partnerInfo")
    private List<Waiting> waitingList = new ArrayList<>();


    @OneToMany(mappedBy = "partnerInfo")
    private List<PartnerAttachment> partnerAttachments = new ArrayList<>();

    @OneToMany(mappedBy = "partnerInfo")
    private List<PartnerMenu> partnerMenus = new ArrayList<>();



    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "userid")
    private User user;






}
