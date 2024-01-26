package com.lec.spring.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;


    private String name;
    private String birthdate;

    private String email;

    private String temperature;

    private String profileImageUrl;
    private String bio;


    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Waiting> waitingList = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<PartnerReview> partnerReviews = new ArrayList<>();




    @OneToOne(mappedBy = "user")
    private UserAttachment userAttachment;


    @OneToMany(mappedBy = "follower")
    private List<Follow> following = new ArrayList<>();

    @OneToMany(mappedBy = "followee")
    private List<Follow> followee = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<PartnerInfo> partnerInfos = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<PartnerReceipt> partnerReceipts = new ArrayList<>();

}
