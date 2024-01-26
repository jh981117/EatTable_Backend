package com.lec.spring.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PartnerReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;


    @OneToMany(mappedBy = "partnerReview")
    private List<Comment> commentList = new ArrayList<>();

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name ="partnerinfo_id")
    private PartnerInfo partnerInfo;



    @OneToMany(mappedBy = "partnerReview")
    private List<PartnerReviewAttachment> partnerReviewAttachments = new ArrayList<>();




}
